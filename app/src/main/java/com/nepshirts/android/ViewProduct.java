package com.nepshirts.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nepshirts.android.R;
import com.r0adkll.slidr.Slidr;
import com.squareup.picasso.Picasso;

public class ViewProduct extends AppCompatActivity {
    private ImageView shirtImage;
    private TextView shirtName;
    private TextView shirtPrice;
    private TextView shirtCategory;
    private ImageView backBtn;
    private Button addToCart;
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
        final Uri image = Uri.parse(intent.getExtras().getString("Image"));
        final String name = intent.getExtras().getString("Name");
        final String price = intent.getExtras().getString("Price");
        final String category = intent.getExtras().getString("Category");
//        int rating = (int) intent.getExtras().getFloat("Rating");

//        shirtImage.setImageResource(image);
        Picasso.get().load(image).into(shirtImage);
        shirtName.setText(name);
        shirtPrice.setText(price);
        shirtCategory.setText(category);
//        shirtRating.setRating(rating);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("cartInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", name);
                editor.putString("price", price);
                editor.putString("category", category);
                editor.putString("image", intent.getExtras().getString("Image"));
                editor.apply();
                Toast.makeText(ViewProduct.this, "Added to cart!", Toast.LENGTH_SHORT).show();
            }
        });

        Slidr.attach(this);

    }
}
