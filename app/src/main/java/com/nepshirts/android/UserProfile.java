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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class UserProfile extends AppCompatActivity {
    private TextView userName;
    private EditText email,phone,fname,lname,city,street,landmark;
    private ImageView profileImage;
    private GoogleSignInAccount acct;
    FirebaseAuth mauth;
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
        fname  = findViewById(R.id.firtName);
        lname = findViewById(R.id.lastName);
        city = findViewById(R.id.cityName);
        street = findViewById(R.id.cityName);
        landmark = findViewById(R.id.landmarkName);

        acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();


            //setting to views
            Picasso.get().load(personPhoto).into(profileImage);
            userName.setText(personName);
            email.setText(personEmail);
            fname.setText(personGivenName);
            lname.setText(personFamilyName);
            phone.setText(personId);

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
