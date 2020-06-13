package com.nepshirts.android.user;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nepshirts.android.R;
import com.nepshirts.android.utils.ProgressDialog;

public class ResetPassword extends AppCompatActivity {

    private EditText emailField;
    private Button sendButton;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        emailField = findViewById(R.id.email_input);
        sendButton = findViewById(R.id.send_btn);
        firebaseAuth = FirebaseAuth.getInstance();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!emailField.getText().toString().isEmpty()) {
                    ProgressDialog.start(ResetPassword.this);
                    firebaseAuth.sendPasswordResetEmail(emailField.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    ProgressDialog.stop();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ResetPassword.this, "Password reset email has been sent.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ResetPassword.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(ResetPassword.this, "Account with this email does not exists.", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                } else {
                    Toast.makeText(ResetPassword.this, "Email field cannot be empty.", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void login_page(View view) {
        Intent myIntent = new Intent(ResetPassword.this, LoginActivity.class);
        startActivity(myIntent);
        finish();
    }
}
