package com.example.qassa_finalproject.UserFragments;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.qassa_finalproject.BarberShop;
import com.example.qassa_finalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

public class Take_Queue_Fragment extends Fragment {
    private BarberShop barberShop;
    private RecyclerView recyclerView;
    private QueueAdapter adapter;
    private Button btnTake;
    private Button closeButton;
    private String selectedTime;
    private String selectedDate;

    public Take_Queue_Fragment() {
        // Required empty public constructor
    }

    public static Take_Queue_Fragment newInstance(BarberShop barberShop) {
        Take_Queue_Fragment fragment = new Take_Queue_Fragment();
        Bundle args = new Bundle();
        args.putSerializable("barber_shop", barberShop);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            barberShop = (BarberShop) getArguments().getSerializable("barber_shop");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_take__queue_, container, false);

        recyclerView = view.findViewById(R.id.turnsRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnTake = view.findViewById(R.id.button2);
        Button btnToday = view.findViewById(R.id.btnToday);
        Button btnTomorrow = view.findViewById(R.id.btnTomorrow);
        Button btnAfterTomorrow = view.findViewById(R.id.btnAfterTomorrow);

        btnToday.setOnClickListener(v -> loadQueueForDay("today"));
        btnTomorrow.setOnClickListener(v -> loadQueueForDay("tomorrow"));
        btnAfterTomorrow.setOnClickListener(v -> loadQueueForDay("afterTomorrow"));

        btnTake.setOnClickListener(v -> bookAppointment());

        //ة برجع صفح
        closeButton = view.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        loadQueueForDay("today"); // Default
        return view;
    }

    private void loadQueueForDay(String dayKey) {
        try {
            Calendar cal = Calendar.getInstance();
            String workingHours;
            String date;

            switch (dayKey) {
                case "today":
                    workingHours = getDayTime(cal.get(Calendar.DAY_OF_WEEK));
                    date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(cal.getTime());
                    break;
                case "tomorrow":
                    cal.add(Calendar.DAY_OF_YEAR, 1);
                    workingHours = getDayTime(cal.get(Calendar.DAY_OF_WEEK));
                    date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(cal.getTime());
                    break;
                case "afterTomorrow":
                    cal.add(Calendar.DAY_OF_YEAR, 2);
                    workingHours = getDayTime(cal.get(Calendar.DAY_OF_WEEK));
                    date = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(cal.getTime());
                    break;
                default:
                    return;
            }

            selectedDate = date;
            String[] slots = barberShop.GenerateQueue(workingHours, barberShop.getEstimatedQueue(), date);
            List<String> availableSlots = new ArrayList<>();
            for (String slot : slots) {
                if (slot != null) {
                    availableSlots.add(slot);
                }
            }
            adapter = new QueueAdapter(availableSlots, time -> selectedTime = time);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error loading queue: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String getDayTime(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY: return barberShop.getSun();
            case Calendar.MONDAY: return barberShop.getMon();
            case Calendar.TUESDAY: return barberShop.getTue();
            case Calendar.WEDNESDAY: return barberShop.getWed();
            case Calendar.THURSDAY: return barberShop.getThu();
            case Calendar.FRIDAY: return barberShop.getFri();
            case Calendar.SATURDAY: return barberShop.getSat();
            default: return "";
        }
    }

    private void bookAppointment() {
        if (selectedTime == null || selectedDate == null) {
            Toast.makeText(getContext(), "Please select a time slot", Toast.LENGTH_SHORT).show();
            return;
        }
        if (barberShop.bookAppointment(selectedDate, selectedTime)) {
            Toast.makeText(getContext(), "Appointment booked for " + selectedTime + " on " + selectedDate, Toast.LENGTH_LONG).show();
            loadQueueForDay("today"); // Refresh the queue

// Email sending code

//////////

            getParentFragmentManager().popBackStack(); // Return to previous fragment
        } else {
            Toast.makeText(getContext(), "Time slot is already booked", Toast.LENGTH_SHORT).show();
        }
    }





}