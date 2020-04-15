package com.nepshirts.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private EditText email,password;
    private Button login;

    private FirebaseAuth authenticate;
    private FirebaseAuth.AuthStateListener isauthenticated;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authenticate = FirebaseAuth.getInstance();
        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        login = findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uemail = email.getText().toString();
                String upassword = password.getText().toString();

                if(uemail.isEmpty()){
                    email.setError("Email is empty");
                    email.requestFocus();
                }
                else if(upassword.isEmpty()){
                    password.setError("Password is empty");
                    password.requestFocus();
                }

                isauthenticated = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if(firebaseAuth.getCurrentUser() !=null){

                       startActivity(new Intent(MainActivity.this,home.class));
                    }

                    }
                };
                authenticate.signInWithEmailAndPassword(uemail,upassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Email or password is wrong!!!", Toast.LENGTH_SHORT).show();
                        }
                        else{

                            Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,home.class));
                        }
                    }
                });
            }
        });





    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void register_page(View view) {
        Intent myIntent = new Intent(MainActivity.this, Register.class);
        startActivity(myIntent);
        finish();
    }


}
