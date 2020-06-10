package com.nepshirts.android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nepshirts.android.models.OrderModel;
import com.nepshirts.android.models.ProductModel;
import com.r0adkll.slidr.Slidr;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ViewProduct extends AppCompatActivity {

    private static final String TAG = "ViewProduct";
    private ImageView shirtImage;
    private TextView shirtName;
    private TextView shirtPrice;
    private TextView shirtDiscounted;
    private TextView shirtCategory;
    private TextView shirtDetails;
    private ImageView backBtn;
    private Button addToCart;
    private Button buyNow;
    private ArrayList<OrderModel> orderList = new ArrayList<>();
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener mauthAuthStateListener;
    private DatabaseReference mDatabase;

    private String name, city, street, phone;

    private Spinner colorSpinner, sizeSpinner;
    private EditText quantityField;
    private RatingBar shirtRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_product);
        backBtn = findViewById(R.id.imageView6);
        shirtImage = findViewById(R.id.shirt_image);
        shirtName = findViewById(R.id.shirt_name);
        shirtPrice = findViewById(R.id.shirt_price);
        shirtDiscounted = findViewById(R.id.shirt_discounted);
        shirtDetails = findViewById(R.id.product_detail);
        shirtCategory = findViewById(R.id.shirt_category);
        shirtRating = findViewById(R.id.shirt_rating);
        addToCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Intent intent = getIntent();
        ProductModel product = intent.getParcelableExtra("selected_product");

        final String productId = product.getId();

        final Uri imageUrl = Uri.parse(product.getImageUrl());
        final String name = product.getProductNames();
        final String discounted = product.getDisPrice();
        final String price = product.getPrice();
        final String category = product.getProductCategory();
        final String description = product.getDescription();
        int rating = Integer.parseInt(product.getRating());
//        int rating = (int) intent.getExtras().getFloat("Rating");


//        shirtImage.setImageResource(image);
        Picasso.get().load(imageUrl).into(shirtImage);
        shirtName.setText(name);
        shirtPrice.setText("Rs." + price);
        shirtDiscounted.setText(discounted);
        shirtDiscounted.setPaintFlags(shirtDiscounted.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        shirtCategory.setText(category);
        shirtDetails.setText(description);
        shirtRating.setRating(rating);


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogBox = new Dialog(ViewProduct.this, android.R.style.Theme_Black_NoTitleBar);
                dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                dialogBox.setContentView(R.layout.confirm_dialogue);
                dialogBox.setCancelable(true);
                dialogBox.setCanceledOnTouchOutside(true); // todo
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
                        if (Integer.parseInt(productQuantity) != 0) {
                            addToCart(productId, productColor, productSize, productQuantity);
                            dialogBox.dismiss();
                        } else {
                            Toast.makeText(ViewProduct.this, "Invalid Quantity", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogBox = new Dialog(ViewProduct.this, android.R.style.Theme_Black_NoTitleBar);
                dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                dialogBox.setContentView(R.layout.confirm_dialogue);
                dialogBox.setCancelable(true);
                dialogBox.setCanceledOnTouchOutside(true); // todo
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
                        if (Integer.parseInt(productQuantity) != 0) {
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {

                                checkout(productId, productColor, productSize, productQuantity);
                                dialogBox.dismiss();
                            } else {
                                Toast.makeText(ViewProduct.this, "Please Sign In to  Checkout", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ViewProduct.this, "Invalid Quantity", Toast.LENGTH_SHORT).show();
                        }


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

        return cartList;

    }

    private void checkout(String productId, String productColor, String productSize, String productQuantity) {
        user = FirebaseAuth.getInstance().getCurrentUser();
//        for(OrderModel om: viewCart()){
        try {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String key = database.getReference().child("Orders").push().getKey();
            mDatabase.child("Orders").child(key).child("productId").setValue(productId);
            mDatabase.child("Orders").child(key).child("userName").setValue(name);
            mDatabase.child("Orders").child(key).child("userId").setValue(user.getUid());
            mDatabase.child("Orders").child(key).child("productColor").setValue(productColor);
            mDatabase.child("Orders").child(key).child("size").setValue(productSize);
            mDatabase.child("Orders").child(key).child("quantity").setValue(productQuantity);
            mDatabase.child("Orders").child(key).child("city").setValue(city);
            mDatabase.child("Orders").child(key).child("street").setValue(street);
            mDatabase.child("Orders").child(key).child("phone").setValue(phone);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
//        }

        Toast.makeText(ViewProduct.this, "Done", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ViewProduct.this, CheckOut.class));
    }

}

