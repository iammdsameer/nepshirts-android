package com.nepshirts.android;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nepshirts.android.home.MainActivity;
import com.nepshirts.android.user.LoginActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CheckOut extends AppCompatActivity {
    private TextView totalAmount,delivery_date,shippingAddress,userName;
    private FirebaseUser user;
    private DatabaseReference ref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);




        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");

        Date todayDate = new Date();


        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(todayDate);
        c.add(Calendar.DATE, 5);
        dt = c.getTime();

        final String shippingDate = currentDate.format(dt);

        totalAmount = findViewById(R.id.payableAmount);
        delivery_date = findViewById(R.id.deliveryDate);
        shippingAddress = findViewById(R.id.shipping_address);
        userName = findViewById(R.id.user_name_checkout);

        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());

        if (user != null) {


            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                       String _CITY = dataSnapshot.child("city").getValue().toString();
                        String  _STREET = dataSnapshot.child("street").getValue().toString();
                        String  _NAME = dataSnapshot.child("fullName").getValue().toString();
                        shippingAddress.setText(_STREET + ", " + _CITY);
                        userName.setText(_NAME);
                        delivery_date.setText("Delivery Date: " + shippingDate.toString());


                        SharedPreferences sharedPreferences = CheckOut.this.getSharedPreferences("priceInfo", Context.MODE_PRIVATE);
                        String subTotal = sharedPreferences.getString("cartPrice", "0");
                        totalAmount.setText("Total Price: " + subTotal);


                    } catch (NullPointerException e) {
                        Toast.makeText(CheckOut.this, "Unable to extract Data", Toast.LENGTH_SHORT).show();
                    }





                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });


        } else {
            Intent intent = new Intent(CheckOut.this, LoginActivity.class);
            Toast.makeText(this, "Please Login", Toast.LENGTH_SHORT).show();
            startActivity(intent);

        }








    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        CheckOut.this.getSharedPreferences("priceInfo", 0).edit().clear().apply();
        CheckOut.this.getSharedPreferences("cartInfo", 0).edit().clear().apply();

    }
    @Override
    protected void onStop() {
        super.onStop();
        CheckOut.this.getSharedPreferences("priceInfo", 0).edit().clear().apply();
        CheckOut.this.getSharedPreferences("cartInfo", 0).edit().clear().apply();

    }

    public void redirect(View view) {
        Intent intent = new Intent(CheckOut.this, MainActivity.class);
        CheckOut.this.getSharedPreferences("priceInfo", 0).edit().clear().apply();
        CheckOut.this.getSharedPreferences("cartInfo", 0).edit().clear().apply();
        startActivity(intent);
        finish();
    }
}


