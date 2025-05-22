package com.example.qassa_finalproject.AddFragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qassa_finalproject.FirebaseServices;
import com.example.qassa_finalproject.R;
import com.example.qassa_finalproject.User;
import com.example.qassa_finalproject.UserFragments.Home_Customer_Fragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddDetails_User_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDetails_User_Fragment extends Fragment {

    private EditText Name,Age;
    private Button Submit;
    private Button btnUserImage;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private ImageView userImageView;

    public AddDetails_User_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddDetails_User_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddDetails_User_Fragment newInstance(String param1, String param2) {
        AddDetails_User_Fragment fragment = new AddDetails_User_Fragment();
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
        return inflater.inflate(R.layout.fragment_add_details__user_, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Submit = getView().findViewById(R.id.btSubmit);
        Name = getView().findViewById(R.id.etName);
        Age = getView().findViewById(R.id.etAge);
        btnUserImage = getView().findViewById(R.id.btnSelectImage);
        userImageView = getView().findViewById(R.id.imageView);


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString();
                String age = Age.getText().toString();
                String Uid = "";
                FirebaseAuth fbs = FirebaseAuth.getInstance();
                FirebaseUser currentUser = fbs.getCurrentUser();
                        Uid = currentUser.getUid();

                if (name.trim().isEmpty() || age.trim().isEmpty()) {
                    Toast.makeText(getActivity(), "Some fields are empty !", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                    if (imageUri != null) {
                        StorageReference storageRef = FirebaseStorage.getInstance().getReference("user_images/" + Uid + ".jpg");
                        String finalUid = Uid;
                        storageRef.putFile(imageUri) .addOnSuccessListener(taskSnapshot -> {
                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        User u = new User(name, Integer.parseInt(age), finalUid);
                                        u.setImageUr(imageUrl);
                                        saveUserToFirestore(finalUid, u);
                            });
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("UPLOAD_ERROR", "Image upload failed", e);
                                    Toast.makeText(getActivity(), "Image upload failed.", Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        // No image selected, continue with null imageUr
                        User u = new User(name, Integer.parseInt(age), Uid);
                        saveUserToFirestore(Uid, u);
                    }


                // User u = new User(name, Integer.parseInt(age), Uid);
               // db.collection("Users").document(Uid).set(u)
                //        .addOnSuccessListener(new OnSuccessListener<Void>() {
                 //           @Override
                  //          public void onSuccess(Void aVoid) {
                   //             Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                    //        }
                     //   })
                      //  .addOnFailureListener(new OnFailureListener()
                       // {
                        //    @Override
                         //   public void onFailure(@NonNull Exception e) {
                          //      Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                       // }
               // });
            }
        });

        btnUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
          //          startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image URI
            imageUri = data.getData();
            userImageView.setImageURI(imageUri);

            // Show the selected image in the ImageView
            if (imageUri != null && userImageView != null) {
                userImageView.setImageURI(imageUri);
            } else {
                Toast.makeText(getActivity(), "Failed to load image.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void saveUserToFirestore(String uid, User user) {
        FirebaseFirestore.getInstance().collection("Users").document(uid).set(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getActivity(), "User saved successfully!", Toast.LENGTH_SHORT).show();

                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.main, new Home_Customer_Fragment());
                    ft.commit();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Failed to save user!", Toast.LENGTH_SHORT).show();
                });
    }

}