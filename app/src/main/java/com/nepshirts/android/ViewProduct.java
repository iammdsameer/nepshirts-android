package com.nepshirts.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nepshirts.android.R;
import com.nepshirts.android.models.OrderModel;
import com.r0adkll.slidr.Slidr;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ViewProduct extends AppCompatActivity {

    private static final String TAG = "ViewProduct";
    private ImageView shirtImage;
    private TextView shirtName;
    private TextView shirtPrice;
    private TextView shirtCategory;
    private ImageView backBtn;
    private Button addToCart;
    private DatabaseReference ref;
    private ArrayList<OrderModel> orderList = new ArrayList<>();
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener mauthAuthStateListener;

    private Spinner colorSpinner, sizeSpinner;
    private EditText quantityField;

    //    private RatingBar shirtRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_product);
        backBtn = findViewById(R.id.imageView6);
        shirtImage = findViewById(R.id.shirt_image);
        shirtName = findViewById(R.id.shirt_name);
        shirtPrice = findViewById(R.id.shirt_price);
        shirtCategory = findViewById(R.id.shirt_category);
//        shirtRating = findViewById(R.id.shirt_rating);
        addToCart = findViewById(R.id.add_to_cart);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Intent intent = getIntent();

        final String productId = intent.getExtras().getString("Id");
//        ref = FirebaseDatabase.getInstance().getReference().child("Products").child(productId);


        final Uri imageUrl = Uri.parse(intent.getExtras().getString("Image"));
        final String name = intent.getExtras().getString("Name");
        final String price = intent.getExtras().getString("Price");
        final String category = intent.getExtras().getString("Category");
//        int rating = (int) intent.getExtras().getFloat("Rating");
        final String image = intent.getExtras().getString("Image");


//        shirtImage.setImageResource(image);
        Picasso.get().load(imageUrl).into(shirtImage);
        shirtName.setText(name);
        shirtPrice.setText(price);
        shirtCategory.setText(category);
//        shirtRating.setRating(rating);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogBox = new Dialog(ViewProduct.this, android.R.style.Theme_Black_NoTitleBar);
                dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                dialogBox.setContentView(R.layout.confirm_dialogue);
                dialogBox.setCanceledOnTouchOutside(true); // todo
                dialogBox.setCancelable(true);
                dialogBox.show();

                colorSpinner = dialogBox.findViewById(R.id.color_spinner);
                sizeSpinner = dialogBox.findViewById(R.id.size_spinner);
                quantityField = dialogBox.findViewById(R.id.product_quantity);


                Button confirm = dialogBox.findViewById(R.id.confirm_button);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String productColor = colorSpinner.getSelectedItem().toString().toLowerCase();
                        String productSize = sizeSpinner.getSelectedItem().toString().toLowerCase();
                        String productQuantity = quantityField.getText().toString();
                        addToCart(productId, productColor, productSize, productQuantity);
                        dialogBox.dismiss();
                    }
                });

            }
        });

        Slidr.attach(this);

    }

    private void addToCart(String productId, String productColor, String productSize, String productQuantity) {

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String userId = user.getUid();

            OrderModel newOrder = new OrderModel(productId, userId, productColor, productSize, productQuantity);
            if (viewCart() == null) {
                orderList.add(newOrder);

                Gson gson = new Gson();
                String jsonString = gson.toJson(orderList);

                SharedPreferences sharedPreferences = getSharedPreferences("cartInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("cartItems", jsonString);
                editor.apply();
                Toast.makeText(ViewProduct.this, "Added to cart!", Toast.LENGTH_SHORT).show();
            } else {
                ArrayList<OrderModel> orderList = viewCart();
                orderList.add(newOrder);
                Gson gson = new Gson();
                String jsonString = gson.toJson(orderList);

                SharedPreferences sharedPreferences = getSharedPreferences("cartInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("cartItems", jsonString);
                editor.apply();
                Toast.makeText(ViewProduct.this, "Added to cart!", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(ViewProduct.this, "Login to add products to cart!", Toast.LENGTH_SHORT).show();
        }

    }

    public ArrayList<OrderModel> viewCart() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("cartInfo", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("cartItems", "");
        Type type = new TypeToken<ArrayList<OrderModel>>() {
        }.getType();
        ArrayList<OrderModel> cartList = gson.fromJson(json, type);

        for (OrderModel o : orderList) {
            Log.d(TAG, "viewCart: " + o.getQuantity());
        }

        return cartList;

    }

//    private void addToCart(final String id){
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    user = FirebaseAuth.getInstance().getCurrentUser();
//                    if (user != null){
//                        String userId = user.getUid();
//                        String productKey = dataSnapshot.getKey();
//
//                        OrderModel order = new OrderModel(productKey, userId, );
//
//
//                        orderList.add();
//
//
//                        Gson gson = new Gson();
//                        String jsonString = gson.toJson(cartList);
//
//                        SharedPreferences sharedPreferences = getSharedPreferences("cartInfo", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("cartItems", jsonString);
//                        editor.apply();
//                    }else {
//                        Toast.makeText(ViewProduct.this, "Login to add products to cart!", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    //todo


}

