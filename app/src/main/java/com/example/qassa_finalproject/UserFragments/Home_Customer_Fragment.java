package com.example.qassa_finalproject.UserFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qassa_finalproject.AddFragments.AddDetails_User_Fragment;
import com.example.qassa_finalproject.BarberShop;
import com.example.qassa_finalproject.FirebaseServices;
import com.example.qassa_finalproject.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home_Customer_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home_Customer_Fragment extends Fragment {


    private FirebaseServices fbs;
    private FirebaseFirestore db;
    private FirebaseStorage fs;

    // RecyclerView
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home_Customer_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home_Customer_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Home_Customer_Fragment newInstance(String param1, String param2) {
        Home_Customer_Fragment fragment = new Home_Customer_Fragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home__customer_, container, false);

        recyclerView = view.findViewById(R.id.ShopsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fbs = FirebaseServices.getInstance(); // if you have singleton
        db = FirebaseFirestore.getInstance();

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        db.collection("BarberShops").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<BarberShop> barberList = new ArrayList<>();
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    BarberShop barber = doc.toObject(BarberShop.class);
                    barberList.add(barber);
                }

                Sh_recyclerViewAdapter adapter = new Sh_recyclerViewAdapter(getContext(), barberList);
                recyclerView.setAdapter(adapter);
            }
        });


    }


}
////////////////