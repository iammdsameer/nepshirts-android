package com.nepshirts.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserProfile extends AppCompatActivity {
    private TextView userName;
    private EditText email, phone, fname, lname, city, street, landmark;
    private ImageView profileImage;
    private FirebaseUser user;
    private DatabaseReference ref;
    String _PHONE, _NAME, _CITY, _STREET, _LANDMARK, _EMAIL;

    private FirebaseAuth.AuthStateListener mauthAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setupFirebaseListener();
        profileImage = findViewById(R.id.profile_image);
        userName = findViewById(R.id.user_name);
        email = findViewById(R.id.userEmail);
        phone = findViewById(R.id.userPhone);
        fname = findViewById(R.id.firstName);
        city = findViewById(R.id.cityName);
        street = findViewById(R.id.streetName);
        landmark = findViewById(R.id.landmarkName);

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
                        Toast.makeText(UserProfile.this, "Unable to extract Data", Toast.LENGTH_SHORT).show();
                    }
                    try {

                        Uri personPhoto = user.getPhotoUrl();


                        Picasso.get().load(personPhoto).placeholder(R.drawable.ic_user).into(profileImage);



                    } catch (Exception e) {

                        Toast.makeText(UserProfile.this, "Profile Photo Could not be loaded", Toast.LENGTH_LONG).show();
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
            Intent intent = new Intent(UserProfile.this, LoginActivity.class);
            startActivity(intent);

        }

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();

    }

    private void setupFirebaseListener() {
        mauthAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Intent intent = new Intent(UserProfile.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseAuth.getInstance().addAuthStateListener(mauthAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mauthAuthStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mauthAuthStateListener);
        }
    }

    public void onUpdateListener(View view) {

        try {
            ref.child("landmark").setValue(landmark.getText().toString());
            ref.child("street").setValue(street.getText().toString());
            ref.child("city").setValue(city.getText().toString());
            ref.child("fullName").setValue(fname.getText().toString());
            ref.child("userPhoneNumber").setValue(phone.getText().toString());
            Toast.makeText(this, "Information Updated", Toast.LENGTH_SHORT).show();
            // Reloading activity
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        } catch (Exception e) {
            Toast.makeText(this, "Unable to update", Toast.LENGTH_SHORT).show();
        }

    }

}
