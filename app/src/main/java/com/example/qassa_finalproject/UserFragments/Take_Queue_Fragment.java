package com.example.qassa_finalproject.UserFragments;

import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.qassa_finalproject.BarberShop;
import com.example.qassa_finalproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Take_Queue_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Take_Queue_Fragment extends Fragment {
    private BarberShop barberShop;
    private RecyclerView recyclerView;
    private QueueAdapter adapter; // Create this adapter class
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Take_Queue_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Take_Queue_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Take_Queue_Fragment newInstance(String param1, String param2) {
        Take_Queue_Fragment fragment = new Take_Queue_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
    private void loadQueueForDay(String dayKey) {

        try {
            String workingHours = ""; // e.g., "08:00-14:00"
            Calendar cal = Calendar.getInstance();
            switch (dayKey) {
                case "today":
                    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                    workingHours = getDayTime(dayOfWeek);
                    break;
                case "tomorrow":
                    cal.add(Calendar.DAY_OF_YEAR, 1);
                    break;
                case "afterTomorrow":
                    cal.add(Calendar.DAY_OF_YEAR, 2);
                    break;
            }

            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            workingHours = getDayTime(dayOfWeek);

            String[] slots = barberShop.GenerateQueue(workingHours, barberShop.getEstimatedQueue());
            adapter = new QueueAdapter(slots);
            recyclerView.setAdapter(adapter);
        }catch ( Exception e)
        {
            e.printStackTrace();
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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        try {


            view = inflater.inflate(R.layout.fragment_take__queue_, container, false);

            recyclerView = view.findViewById(R.id.turnsRecycleView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            Button btnToday = view.findViewById(R.id.btnToday);
            btnToday.setOnClickListener(v -> loadQueueForDay("today"));

            Button btnTomorrow = view.findViewById(R.id.btnTomorrow);
            Button btnAfterTomorrow = view.findViewById(R.id.btnAfterTomorrow);

            btnTomorrow.setOnClickListener(v -> loadQueueForDay("tomorrow"));
            btnAfterTomorrow.setOnClickListener(v -> loadQueueForDay("afterTomorrow"));


            loadQueueForDay("today"); // Default
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return view;

    }
}