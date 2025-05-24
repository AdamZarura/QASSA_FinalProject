package com.example.qassa_finalproject.UserFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.qassa_finalproject.BarberShop;
import com.example.qassa_finalproject.R;

public class BarberShop_View_Fragment extends Fragment {
    private Button buttonTakeTurn;
    private TextView price, sun, mon, tue, wed, thu, fri, sat, shopNameTextView;
    private static final String ARG_BARBERSHOP = "barber_shop";
    private BarberShop mBarberShop;

    public BarberShop_View_Fragment() {
        // Required empty public constructor
    }

    public static BarberShop_View_Fragment newInstance(BarberShop barberShop) {
        BarberShop_View_Fragment fragment = new BarberShop_View_Fragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BARBERSHOP, barberShop);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBarberShop = (BarberShop) getArguments().getSerializable(ARG_BARBERSHOP);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_barber_shop__view_, container, false);

        buttonTakeTurn = view.findViewById(R.id.btnTakeTurn);
        price = view.findViewById(R.id.tvPrice);
        sun = view.findViewById(R.id.tvSun);
        mon = view.findViewById(R.id.tvMon);
        tue = view.findViewById(R.id.tvtue);
        wed = view.findViewById(R.id.tvWed);
        thu = view.findViewById(R.id.tvThu);
        fri = view.findViewById(R.id.tvFri);
        sat = view.findViewById(R.id.tvSat);
        shopNameTextView = view.findViewById(R.id.tvShopName);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mBarberShop == null) {
            Toast.makeText(getContext(), "Error: BarberShop data could not be loaded.", Toast.LENGTH_LONG).show();
            return;
        }

        if (shopNameTextView != null) {
            shopNameTextView.setText(mBarberShop.getName());
        }
        if (price != null) {
            price.setText(String.format("%d", mBarberShop.getPrice()));
        }
        if (sun != null) sun.setText(mBarberShop.getSun());
        if (mon != null) mon.setText(mBarberShop.getMon());
        if (tue != null) tue.setText(mBarberShop.getTue());
        if (wed != null) wed.setText(mBarberShop.getWed());
        if (thu != null) thu.setText(mBarberShop.getThu());
        if (fri != null) fri.setText(mBarberShop.getFri());
        if (sat != null) sat.setText(mBarberShop.getSat());

        if (buttonTakeTurn != null) {
            buttonTakeTurn.setOnClickListener(v -> {
                Take_Queue_Fragment fragment = new Take_Queue_Fragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("barber_shop", mBarberShop);
                fragment.setArguments(bundle);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.main, fragment)
                        .addToBackStack(null)
                        .commit();
            });
        }
    }
}