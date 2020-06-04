package com.nepshirts.android.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nepshirts.android.LoginActivity;
import com.nepshirts.android.R;
import com.nepshirts.android.UserProfile;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView userName;
    private EditText email, phone, fname, lname, city, street, landmark;
    private ImageView profileImage;
    private FirebaseUser user;
    private DatabaseReference ref;
    String _PHONE, _NAME, _CITY, _STREET, _LANDMARK, _EMAIL;

    private FirebaseAuth.AuthStateListener mauthAuthStateListener;

    private Button logoutButton;
    private Button updateButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user_profile, container, false);
        setupFirebaseListener();

        logoutButton = view.findViewById(R.id.logout_button);
        updateButton = view.findViewById(R.id.profile_update_button);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ref.child("landmark").setValue(landmark.getText().toString());
                    ref.child("street").setValue(street.getText().toString());
                    ref.child("city").setValue(city.getText().toString());
                    ref.child("fullName").setValue(fname.getText().toString());
                    ref.child("userPhoneNumber").setValue(phone.getText().toString());
                    Toast.makeText(getActivity(), "Information Updated", Toast.LENGTH_SHORT).show();
                    // Reloading activity
//                    finish();
//                    overridePendingTransition(0, 0);
//                    startActivity(getIntent());
//                    overridePendingTransition(0, 0);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Unable to update", Toast.LENGTH_SHORT).show();
                }
            }
        });

        profileImage = view.findViewById(R.id.profile_image);
        userName = view.findViewById(R.id.user_name);
        email = view.findViewById(R.id.userEmail);
        phone = view.findViewById(R.id.userPhone);
        fname = view.findViewById(R.id.firstName);
        city = view.findViewById(R.id.cityName);
        street = view.findViewById(R.id.streetName);
        landmark = view.findViewById(R.id.landmarkName);

        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        if (user != null) {

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        _CITY = dataSnapshot.child("city").getValue().toString();
                        _STREET = dataSnapshot.child("street").getValue().toString();
                        _PHONE = dataSnapshot.child("userPhoneNumber").getValue().toString();
                        _NAME = dataSnapshot.child("fullName").getValue().toString();
                        _LANDMARK = dataSnapshot.child("landmark").getValue().toString();
                        _EMAIL = dataSnapshot.child("userEmail").getValue().toString();
                    } catch (NullPointerException e) {
                        Toast.makeText(getActivity(), "Unable to extract Data", Toast.LENGTH_SHORT).show();
                    }
                    try {

                        Uri personPhoto = user.getPhotoUrl();


                        Picasso.get().load(personPhoto).placeholder(R.drawable.ic_user).into(profileImage);



                    } catch (Exception e) {

                        Toast.makeText(getActivity(), "Profile Photo Could not be loaded", Toast.LENGTH_LONG).show();
                    }


                    fname.setText(_NAME);
                    userName.setText(_NAME);
                    phone.setText(_PHONE);
                    email.setText(_EMAIL);
                    landmark.setText(_LANDMARK);
                    city.setText(_CITY);
                    street.setText(_STREET);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });


        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);

        }
        return view;
    }

    private void setupFirebaseListener() {
        mauthAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            }
        };
    }



    @Override
    public void onStart() {
        super.onStart();

        FirebaseAuth.getInstance().addAuthStateListener(mauthAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mauthAuthStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mauthAuthStateListener);
        }
    }


}
