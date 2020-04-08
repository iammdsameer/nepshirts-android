package com.nepshirts.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;


public class Register extends AppCompatActivity {


    private Button  test;// I am testing firebase database here
    private Firebase testref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       Firebase.setAndroidContext(this);// I am testing firebase database here
        // connecting to database
        testref = new Firebase("https://nepshirts-d5435.firebaseio.com/");

        test =(Button) findViewById(R.id.reg_button);// I am testing firebase database here
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Firebase testrefChild = testref.child("Name");
                //sends value to  Name child
                testrefChild.setValue("Anil is Testing");

            }
        });

    }

    public void login_page(View view) {
        Intent myIntent = new Intent(Register.this, MainActivity.class);
        startActivity(myIntent);
        finish();
    }
}
