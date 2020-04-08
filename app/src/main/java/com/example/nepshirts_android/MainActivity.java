package com.example.nepshirts_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private  String email,  password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Message", Toast.LENGTH_SHORT).show();
    }

    public void register_page(View view) {
        Intent myIntent = new Intent(MainActivity.this, Register.class);
        startActivity(myIntent);
        finish();
    }


}
