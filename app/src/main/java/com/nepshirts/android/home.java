package com.nepshirts.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class home extends AppCompatActivity {

    private TextView email;
    private FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fauth = FirebaseAuth.getInstance();



        setContentView(R.layout.activity_home);
        email= findViewById(R.id.show_email);
        email.setText("Welcome !!");



    }
    public void home_page(View view) {
        Intent myIntent = new Intent(home.this, MainActivity.class);
        startActivity(myIntent);
        finish();
    }
}
