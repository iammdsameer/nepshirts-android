package com.nepshirts.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

public class home extends AppCompatActivity {

    private TextView email;
    private FirebaseAuth fauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fauth = FirebaseAuth.getInstance();
//        String f_string = fauth.getCurrentUser().getEmail();


        setContentView(R.layout.activity_home);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
       String personName = "";
        email= findViewById(R.id.show_email);
        if (acct != null) {
             personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            email.setText(personName);
           
        }
        else{
            fauth = FirebaseAuth.getInstance();
            String f_string = fauth.getCurrentUser().getEmail();

            email.setText("HI " + f_string + "Welcome to NepShirts!!");
        }










    }
    public void home_page(View view) {
        Intent myIntent = new Intent(home.this, MainActivity.class);
        startActivity(myIntent);
        finish();
    }
}
