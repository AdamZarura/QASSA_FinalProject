package com.example.qassa_finalproject.UserFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.qassa_finalproject.AddFragments.AddDetails_User_Fragment;
import com.example.qassa_finalproject.BarberShop;
import com.example.qassa_finalproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BarberShop_View_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BarberShop_View_Fragment extends Fragment {

    private Button buttont;
    private TextView price;
    private TextView sun, mon, tue, wed, thu, fri, sat;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BarberShop_View_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BarberShop_View_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BarberShop_View_Fragment newInstance(String param1, String param2) {
        BarberShop_View_Fragment fragment = new BarberShop_View_Fragment();
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
        return inflater.inflate(R.layout.fragment_barber_shop__view_, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();

        buttont = getActivity().findViewById(R.id.btnTake);
        price = getActivity().findViewById(R.id.textView16);
        sun = getActivity().findViewById(R.id.tvSun);
        mon = getActivity().findViewById(R.id.tvMon);
        tue = getActivity().findViewById(R.id.tvtue);
        wed = getActivity().findViewById(R.id.tvWed);
        thu = getActivity().findViewById(R.id.tvThu);
        fri = getActivity().findViewById(R.id.tvFri);
        sat = getActivity().findViewById(R.id.tvSat);

        //تسليك
        BarberShop Shop=(BarberShop) getArguments().getSerializable("Shop");

        buttont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main, new Take_Queue_Fragment());
                ft.commit();
            }
        });

        price.setText(Shop.getPrice());
        sun.setText(Shop.getSun());
        mon.setText(Shop.getMon());
        tue.setText(Shop.getTue());
        wed.setText(Shop.getWed());
        thu.setText(Shop.getThu());
        fri.setText(Shop.getFri());
        sat.setText(Shop.getSat());

    }
}