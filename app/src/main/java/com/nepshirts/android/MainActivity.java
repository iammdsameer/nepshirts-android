package com.nepshirts.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private EditText email,password;
    private Button login;
    private SignInButton g_btn;
    private GoogleSignInClient client;
    private FirebaseAuth authenticate;
    private FirebaseAuth.AuthStateListener isauthenticated;
    private static int RC_SIGN_IN = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        authenticate = FirebaseAuth.getInstance();
        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        login = findViewById(R.id.login_btn);
        g_btn = findViewById(R.id.google_sign_in);


        GoogleSignInOptions g_login = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        client = GoogleSignIn.getClient(this, g_login);

        g_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.google_sign_in:
                        signIn();
                        break;
                }
            }
        });

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent next   = new Intent(MainActivity.this,home.class);
            startActivity(next);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();


        }
    }
    private void signIn() {
        Intent signInIntent = client.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
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
