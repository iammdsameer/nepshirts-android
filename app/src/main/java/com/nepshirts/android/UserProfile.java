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
    private EditText email,phone,fname,lname,city,street,landmark;
    private ImageView profileImage;
    private FirebaseUser user;
    private DatabaseReference ref;

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
        fname  = findViewById(R.id.firstName);
        city = findViewById(R.id.cityName);
        street = findViewById(R.id.streetName);
        landmark = findViewById(R.id.landmarkName);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            ref = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("fullName").getValue() == null){

                        String personName = user.getDisplayName();
                        String personEmail = user.getEmail();
                        Uri personPhoto = user.getPhotoUrl();


                        //setting to views
                        Picasso.get().load(personPhoto).into(profileImage);
                        userName.setText(personName);
                        fname.setText(personName);
                        email.setText(personEmail);

                }else{
                    String userFullName = dataSnapshot.child("fullName").getValue().toString();
                    String phoneNumber = dataSnapshot.child("userPhoneNumber").getValue().toString();
                    String userEmailAddress  =dataSnapshot.child("userEmail").getValue().toString();

                    fname.setText(userFullName);
                    userName.setText(userFullName);
                    phone.setText(phoneNumber);
                    email.setText(userEmailAddress);
                }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });




        }else{
            Intent intent = new Intent(UserProfile.this, LoginActivity.class);
            startActivity(intent);

        }

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();

    }
    private void setupFirebaseListener(){
        mauthAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user ==null){
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
        if(mauthAuthStateListener !=null){
            FirebaseAuth.getInstance().removeAuthStateListener(mauthAuthStateListener);
        }
    }
}
