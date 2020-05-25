package com.nepshirts.android;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class UserProfile extends AppCompatActivity {
    private TextView userName;
    private EditText email,phone,fname,lname,city,street,landmark;
    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        profileImage = findViewById(R.id.profile_image);
        userName = findViewById(R.id.user_name);
        email = findViewById(R.id.userEmail);
        phone = findViewById(R.id.userPhone);
        fname  = findViewById(R.id.firtName);
        lname = findViewById(R.id.lastName);
        city = findViewById(R.id.cityName);
        street = findViewById(R.id.cityName);
        landmark = findViewById(R.id.landmarkName);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();


            //setting to views
            Picasso.get().load(personPhoto).into(profileImage);
            userName.setText(personName);
            email.setText(personEmail);
            fname.setText(personGivenName);
            lname.setText(personFamilyName);
            phone.setText(personId);

        }





    }
}
