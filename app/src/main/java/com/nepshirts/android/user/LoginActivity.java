package com.nepshirts.android.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nepshirts.android.R;
import com.nepshirts.android.home.MainActivity;
import com.nepshirts.android.models.UserModel;
import com.nepshirts.android.utils.ProgressDialog;


public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private TextView forgotPassword;
    private Button login;
    private SignInButton g_btn;
    private GoogleSignInClient client;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener isauthenticated;
    private static int RC_SIGN_IN = 0;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onStart() {
        super.onStart();

        isauthenticated = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }

            }
        };
        mAuth.addAuthStateListener(isauthenticated);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//      getSupportActionBar().hide();

        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        login = findViewById(R.id.login_btn);
        forgotPassword = findViewById(R.id.forget_password);
        g_btn = findViewById(R.id.google_sign_in);
        mAuth = FirebaseAuth.getInstance();

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ResetPassword.class);
                startActivity(intent);
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this, gso);

        g_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.google_sign_in:
                        ProgressDialog.start(LoginActivity.this);
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

                if (uemail.isEmpty()) {
                    email.setError("Email is empty");
                    email.requestFocus();
                } else if (upassword.isEmpty()) {
                    password.setError("Password is empty");
                    password.requestFocus();
                }

                mAuth.signInWithEmailAndPassword(uemail, upassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast toast=Toast.makeText(LoginActivity.this,"Email or Password Wrong!",Toast.LENGTH_LONG);
                            View view =toast.getView();
                            view.setBackgroundColor(Color.RED);
                            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                            toastMessage.setTextColor(Color.WHITE);
                            toast.show();
                            email.requestFocus();
                        } else {

                            Toast toast=Toast.makeText(LoginActivity.this,"Login Successful!!",Toast.LENGTH_LONG);
                            View view =toast.getView();
                            view.setBackgroundColor(Color.GREEN);
                            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                            toastMessage.setTextColor(Color.WHITE);
                            toast.show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    }
                });
            }
        });

    }


    private void signIn() {
        Intent signInIntent = client.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("ERROR: ", "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("ERROR: ", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("ERROR: ", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            // cusom Toast
                            Toast toast=Toast.makeText(LoginActivity.this,"Login Successful!!",Toast.LENGTH_LONG);
                            View view =toast.getView();
                            view.setBackgroundColor(Color.GREEN);
                            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                            toastMessage.setTextColor(Color.WHITE);
                            toast.show();

                            //storing user information in firebase database
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.exists()) {
                                        String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString();
                                        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
                                        String phone = "Update Your Phone Number";
                                        String gender = "";
                                        String birthday = "";
                                        String city = "Update city";
                                        String street = "update street";
                                        String landmark = "update landmark";
                                        UserModel newUser = new UserModel(name, email, phone, gender, birthday, city, street, landmark);
                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(LoginActivity.this, "User Data Added", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(LoginActivity.this, "Could not add Data", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        })
                                        ;
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            ProgressDialog.stop();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("ERROR: ", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


    public void register_page(View view) {
        Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(myIntent);
        finish();
    }


    public void homepage(View view) {
        Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(myIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
