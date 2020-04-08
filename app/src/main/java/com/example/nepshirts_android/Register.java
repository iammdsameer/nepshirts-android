package com.example.nepshirts_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void login_page(View view) {
        Intent myIntent = new Intent(Register.this, MainActivity.class);
        startActivity(myIntent);
    }
}
