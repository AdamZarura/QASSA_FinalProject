package com.example.qassa_finalproject.AddFragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.example.qassa_finalproject.R;
import com.example.qassa_finalproject.User;
import com.example.qassa_finalproject.UserFragments.Home_Customer_Fragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddDetails_User_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDetails_User_Fragment extends Fragment {

    private EditText Name, Age;
    private Button Submit, btnUserImage;
    private ImageView userImageView;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 100;

    // Factory method parameters (optional)
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public AddDetails_User_Fragment() {
        // Required empty public constructor
    }

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
        View view = inflater.inflate(R.layout.fragment_add_details__user_, container, false);

        userImageView = view.findViewById(R.id.ImageView); // Matches layout ID
        btnUserImage = view.findViewById(R.id.btnSelectImage);

        btnUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkStoragePermissionAndPickImage();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Submit = getView().findViewById(R.id.btSubmit);
        Name = getView().findViewById(R.id.etName);
        Age = getView().findViewById(R.id.etAge);
        userImageView = getView().findViewById(R.id.ImageView);
        btnUserImage = getView().findViewById(R.id.btnSelectImage);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString().trim();
                String age = Age.getText().toString().trim();
                String uid = "";
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = auth.getCurrentUser();

                if (currentUser != null) {
                    uid = currentUser.getUid();
                } else {
                    Toast.makeText(getActivity(), "User not authenticated!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (name.isEmpty() || age.isEmpty()) {
                    Toast.makeText(getActivity(), "Some fields are empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int ageInt;
                try {
                    ageInt = Integer.parseInt(age);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Please enter a valid age!", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String finalUid = uid;
                if (imageUri != null) {
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference("user_images/" + finalUid + ".jpg");
                    storageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    User user = new User(name, ageInt, finalUid);
                                    user.setImageUr(imageUrl);
                                    saveUserToFirestore(finalUid, user);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("DOWNLOAD_URL_ERROR", "Failed to get download URL", e);
                                    Toast.makeText(getActivity(), "Failed to get image URL: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                         //   Log.e("UPLOAD_ERROR", "Image upload failed", e);
                            Toast.makeText(getActivity(), "Image upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    User user = new User(name, ageInt, finalUid);
                    saveUserToFirestore(finalUid, user);
                }
            }
        });


        btnUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromGallery();
            }
        }) ;

    }

    private void checkStoragePermissionAndPickImage() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE);
        } else {
            pickImageFromGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImageFromGallery();
            } else {
                Toast.makeText(getContext(), "Storage permission denied. Cannot access gallery.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Intent chooserIntent = Intent.createChooser(intent, "Select Picture");
        if (chooserIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST);
        } else {
            Toast.makeText(getContext(), "No app found to select image", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                imageUri = data.getData();
                if (userImageView != null) {
                    userImageView.setImageURI(imageUri);
                } else {
                    Toast.makeText(getActivity(), "Failed to load image. ImageView is null.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getActivity(), "No image selected or operation cancelled.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error loading image: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void saveUserToFirestore(String uid, User user) {
        FirebaseFirestore.getInstance().collection("Users").document(uid).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "User saved successfully!", Toast.LENGTH_LONG).show();
                        if (getActivity() != null) {
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.main, new Home_Customer_Fragment());
                            ft.commit();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed to save user: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}