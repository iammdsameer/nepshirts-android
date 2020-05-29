package com.nepshirts.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText fullName,userEmail,userPhoneNumber,userGender,userBirthDate,userPassword, confirmPassword;
    private Button registerBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        // initializing
        fullName = findViewById(R.id.name);
        userEmail = findViewById(R.id.email);
        userPhoneNumber = findViewById(R.id.phone);
        userGender = findViewById(R.id.gender);
        userBirthDate = findViewById(R.id.birthday);
        userPassword = findViewById(R.id.password2);
        confirmPassword  = findViewById(R.id.password1);
        registerBtn = findViewById(R.id.reg_button);



           registerBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    registerNewUser();
               }
           });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Intent home = new Intent(Register.this,Home.class);
            startActivity(home);
        }
    }

    public void login_page(View view) {
        Intent myIntent = new Intent(Register.this, MainActivity.class);
        startActivity(myIntent);
        finish();
    }
    public void registerNewUser(){
        //getting text from input fields
        final String name = fullName.getText().toString();
        final String email = userEmail.getText().toString();
        final String phone = userPhoneNumber.getText().toString();
        final String gender = userGender.getText().toString();
        final String birthday = userBirthDate.getText().toString();
        final String confirmPass = confirmPassword.getText().toString();
        final String password = userPassword.getText().toString();


        if(name.isEmpty()){
            fullName.setError("required!");
            fullName.requestFocus();
        }else if(email.isEmpty()){
            userEmail.setError("required!");
            userEmail.requestFocus();
        }else if(phone.isEmpty()){
            userPhoneNumber.setError("required!");
            userPhoneNumber.requestFocus();
        }else if(password.isEmpty()) {
            userPassword.setError("required!");
            userPassword.requestFocus();
        }
//        }else if(password != confirmPass ) {
//            userPassword.setError("Passwords did'nt match");
//        }
        else{


            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(Register.this, "Firebase User Created", Toast.LENGTH_SHORT).show();
                        UserModel user = new UserModel(name,email,phone,gender,birthday);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Register.this, "User Data Added", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(Register.this, "Could not add Data", Toast.LENGTH_SHORT).show();
                                }
                                
                            }
                        })
                        ;

                    }else{
                        Toast.makeText(Register.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }

    public void homepage(View view) {
        Intent intent = new Intent(Register.this,SingleCategory.class);
        startActivity(intent);
        finish();
    }
}
