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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nepshirts.android.user.LoginActivity;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;

public class CheckOut extends AppCompatActivity {
    private TextView totalAmount,delivery_date,shippingAddress,userName;
    private FirebaseUser user;
    private DatabaseReference ref;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        LocalDate date = LocalDate.now();
        final LocalDate shippingDate = date.plusDays(5);

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
                        shippingAddress.setText("Shipping Address " +_STREET +" "+ _CITY );
                        userName.setText("The delivery will be taken by "+_NAME);
                        delivery_date.setText("Expected Delivery Date: "+ shippingDate.toString());


                        SharedPreferences sharedPreferences = CheckOut.this.getSharedPreferences("priceInfo", Context.MODE_PRIVATE);
                        String subTotal = sharedPreferences.getString("cartPrice", "0");
                        totalAmount.setText("Total Payable Price: " + subTotal);


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
}


