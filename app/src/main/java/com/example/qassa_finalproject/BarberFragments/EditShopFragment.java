package com.example.qassa_finalproject.BarberFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qassa_finalproject.Barber;
import com.example.qassa_finalproject.BarberShop;
import com.example.qassa_finalproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditShopFragment extends Fragment {

    private EditText etPrice, etShopName;
    private EditText etSunday, etMonday, etTuesday, etWednesday, etThursday, etFriday, etSaturday;
    private EditText etEstimatedQueue;

    private Button btnSave;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditShopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditShopFragment newInstance(String param1, String param2) {
        EditShopFragment fragment = new EditShopFragment();
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
        return inflater.inflate(R.layout.fragment_edit_shop, container, false);
    }


    @Override
    public void onStart(){
        super.onStart();

        etPrice = getView().findViewById(R.id.et_price);
        etShopName = getView().findViewById(R.id.etShopNmae);

        etSunday = getView().findViewById(R.id.et_sunday);
        etMonday = getView().findViewById(R.id.et_monday);
        etTuesday = getView().findViewById(R.id.et_tuesday);
        etWednesday = getView().findViewById(R.id.et_wednesday);
        etThursday = getView().findViewById(R.id.et_thursday);
        etFriday = getView().findViewById(R.id.et_friday);
        etSaturday = getView().findViewById(R.id.et_saturday);

        etEstimatedQueue = getView().findViewById(R.id.et_estimated_queue);
        btnSave=getView().findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Bid = "";
                FirebaseAuth fbs = FirebaseAuth.getInstance();
                FirebaseUser currentUser = fbs.getCurrentUser();
                Bid = currentUser.getUid();


                int price = Integer.parseInt(etPrice.getText().toString());
                int estimatedQueue = Integer.parseInt(etEstimatedQueue.getText().toString());
                String sun = etSunday.getText().toString();
                String mon = etMonday.getText().toString();
                String tue = etTuesday.getText().toString();
                String wed = etWednesday.getText().toString();
                String thu = etThursday.getText().toString();
                String fri = etFriday.getText().toString();
                String sat = etSaturday.getText().toString();

// أنشئ الكائن باستخدام القيم الصحيحة
                BarberShop Shop = new BarberShop(Bid, price, estimatedQueue, sun, mon, tue, wed, thu, fri, sat);

              //  BarberShop Shop = new BarberShop( Bid.toString(), price, etEstimatedQueue,etSunday,etMonday,etTuesday,etWednesday,etThursday,etFriday,etSaturday);;

                saveBarberShopToFirestore(Bid, Shop);

            }
        });

    }

    private void saveBarberShopToFirestore(String Bid, BarberShop Shop) {
        FirebaseFirestore.getInstance().collection("BarberShops").document(Bid).set(Shop)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getActivity(), "Shop saved successfully!", Toast.LENGTH_SHORT).show();

                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.main, new Home_Barber_Fragment());
                    ft.commit();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Failed to save Shop!", Toast.LENGTH_SHORT).show();
                });
    }
}