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
import android.widget.TextView;
import android.widget.Toast;

import com.example.qassa_finalproject.BarberFragments.Home_Barber_Fragment;
import com.example.qassa_finalproject.FirebaseServices;
import com.example.qassa_finalproject.R;
import com.example.qassa_finalproject.UserFragments.Home_Customer_Fragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LogIn_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogIn_Fragment extends Fragment {

    private EditText etUsername,etPassword;
    private TextView tvSignUp;
    private TextView tvForgotPassword;
    private Button btSignIn;
    private FirebaseServices fbs;

    private TextView bSignUp;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LogIn_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogIn_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogIn_Fragment newInstance(String param1, String param2) {
        LogIn_Fragment fragment = new LogIn_Fragment();
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
        return inflater.inflate(R.layout.fragment_log_in_, container, false);
    }


    public void onStart() {
        super.onStart();

        fbs = FirebaseServices.getInstance();
        etUsername = getView().findViewById(R.id.etEmail);
        etPassword = getView().findViewById(R.id.etPassword);
        btSignIn = getView().findViewById(R.id.btSignIn);
        bSignUp = getView().findViewById(R.id.btSignUp);
        tvForgotPassword = getView().findViewById(R.id.ForgotPassword);


        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main, new Sign_Up_Fragment());
                ft.commit();
            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main, new ForgotPassword_Fragment());
                ft.commit();
            }
        });

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // للفحص
               String username = etUsername.getText().toString();
               String password = etPassword.getText().toString();
                if(username.trim().isEmpty() && password.trim().isEmpty()){
                    Toast.makeText(getActivity(), "Some fields are empty !", Toast.LENGTH_SHORT).show();
                    return;
                }
                // للصنع
                fbs.getAuth().signInWithEmailAndPassword(username,password)
                        .addOnSuccessListener(
                        new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(getActivity(), "Success !", Toast.LENGTH_SHORT).show();

                                FirebaseFirestore db = FirebaseFirestore.getInstance();

                                FirebaseAuth fbs = FirebaseAuth.getInstance();
                                FirebaseUser currentUser = fbs.getCurrentUser();
                                String uid = currentUser.getUid();

                                // Check if the UID exists in the "Users" collection
                                db.collection("Users").document(uid).get()
                                        .addOnSuccessListener(documentSnapshot -> {
                                            if (documentSnapshot.exists()) {
                                                // ✅ The UID exists in Users
                                             //   Toast.makeText(getActivity(), "User exists", Toast.LENGTH_SHORT).show();

                                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                                ft.replace(R.id.main, new Home_Customer_Fragment());
                                                ft.commit();

                                            } else {
                                                // ❌ UID not found in Users
                                             //   Toast.makeText(getActivity(), "User does not exist", Toast.LENGTH_SHORT).show();
                                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                                ft.replace(R.id.main, new Home_Barber_Fragment());
                                                ft.commit();
                                            }
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(getActivity(), "Failed to check Users collection", Toast.LENGTH_SHORT).show();
                                        });



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