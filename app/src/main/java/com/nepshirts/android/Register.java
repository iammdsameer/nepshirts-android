package com.nepshirts.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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


public class Register extends AppCompatActivity {


    private Button reg_btn;
    private EditText uname, uemail, uphone, ubirthday, ugender, upassword1, upassword2;
    FirebaseAuth userRegister;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userRegister = FirebaseAuth.getInstance();

        uemail = findViewById(R.id.email);

        upassword1 = findViewById(R.id.password1);
        upassword2 = findViewById(R.id.password2);
        reg_btn = findViewById(R.id.reg_button);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = uemail.getText().toString();

                String password1 = upassword1.getText().toString();
                String password2 = upassword2.getText().toString();


                if (email.isEmpty()) {
                    uemail.setError("Email cannot be empty");
                    uemail.requestFocus();
                } else if (password1.isEmpty()) {
                    upassword1.setError("Password cannot be empty");
                    upassword1.setError("Password cannot be empty");
                    upassword1.requestFocus();
                }
                    else if (password1 == password2) {
                        upassword2.setError("Password does not match");
                        upassword2.requestFocus();
                    }
                 else {
                    userRegister.createUserWithEmailAndPassword(email, password2)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        startActivity(new Intent(Register.this, Home.class));

                                    } else {
                                        Toast.makeText(Register.this, "Something went wrong!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }


                            });
                }


            }
        });


    }

    public void login_page(View view) {
        Intent myIntent = new Intent(Register.this, MainActivity.class);
        startActivity(myIntent);
        finish();
    }
}
