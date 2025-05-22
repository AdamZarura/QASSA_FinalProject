package com.example.qassa_finalproject.AddFragments;

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
import com.example.qassa_finalproject.BarberFragments.EditShopFragment;
import com.example.qassa_finalproject.R;
import com.example.qassa_finalproject.User;
import com.example.qassa_finalproject.UserFragments.Home_Customer_Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddDetails_Barber_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDetails_Barber_Fragment extends Fragment {

    private EditText etBarName;
    private Button btnBarSUbmit;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddDetails_Barber_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddDetails_Barber_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddDetails_Barber_Fragment newInstance(String param1, String param2) {
        AddDetails_Barber_Fragment fragment = new AddDetails_Barber_Fragment();
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
        return inflater.inflate(R.layout.fragment_add_details__barber_, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        etBarName = getView().findViewById(R.id.etBarName);
        btnBarSUbmit = getView().findViewById(R.id.btnBarSUbmit);

         btnBarSUbmit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 String name = etBarName.getText().toString();
                 String Uid = "";
                 FirebaseAuth fbs = FirebaseAuth.getInstance();
                 FirebaseUser currentUser = fbs.getCurrentUser();

                     Uid = currentUser.getUid();


                 if (name.trim().isEmpty()) {
                     Toast.makeText(getActivity(), "Some fields are empty !", Toast.LENGTH_SHORT).show();
                     return;
                 }
                 Barber b = new Barber(name, Uid);
                 saveBarberToFirestore(Uid, b);

             }
         });

    }


    private void saveBarberToFirestore(String uid, Barber barber) {
        FirebaseFirestore.getInstance().collection("Barbers").document(uid).set(barber)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getActivity(), "User saved successfully!", Toast.LENGTH_SHORT).show();

                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.main, new EditShopFragment());
                    ft.commit();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Failed to save user!", Toast.LENGTH_SHORT).show();
                });
    }
}