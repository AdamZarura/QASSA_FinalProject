package com.example.qassa_finalproject.__LoginFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.qassa_finalproject.AddFragments.AddDetails_Barber_Fragment;
import com.example.qassa_finalproject.AddFragments.AddDetails_User_Fragment;
import com.example.qassa_finalproject.Barber;
import com.example.qassa_finalproject.FirebaseServices;
import com.example.qassa_finalproject.R;
import com.example.qassa_finalproject.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Sign_Up_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sign_Up_Fragment extends Fragment {


    private EditText etUsername,etPassword;
    private Button btSignup;
    private FirebaseServices fbs;
    private Switch AreYouBarber;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Sign_Up_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sign_Up_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Sign_Up_Fragment newInstance(String param1, String param2) {
        Sign_Up_Fragment fragment = new Sign_Up_Fragment();
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
        return inflater.inflate(R.layout.fragment_sign__up_, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        // connect components
        fbs = FirebaseServices.getInstance();
        etUsername = getView().findViewById(R.id.etEmail);
        etPassword = getView().findViewById(R.id.etPassword);
        btSignup   = getView().findViewById(R.id.btSignUp);
        AreYouBarber = getView().findViewById(R.id.S_AreU);
   //     boolean isOn = AreYou.isChecked();


        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // للفحص
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if(username.trim().isEmpty() || password.trim().isEmpty()){
                    Toast.makeText(getActivity(), "Some fields are empty !", Toast.LENGTH_SHORT).show();
                    return;
                }
                // للصنع
                fbs.getAuth().createUserWithEmailAndPassword(username,password).addOnSuccessListener(
                        new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(getActivity(), "Success !", Toast.LENGTH_SHORT).show();


                                if(AreYouBarber.isChecked() == false){
                                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.main, new AddDetails_User_Fragment());
                                    ft.commit();
                                }else {
                                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                    ft.replace(R.id.main, new AddDetails_Barber_Fragment());
                                    ft.commit();
                                }

                            }
                        }
                ).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}