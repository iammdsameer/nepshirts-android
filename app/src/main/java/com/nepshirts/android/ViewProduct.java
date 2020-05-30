package com.nepshirts.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewProduct extends AppCompatActivity {
    private ImageView shirtImage;
    private TextView shirtName;
    private TextView shirtPrice;
    private TextView shirtCategory;
//    private RatingBar shirtRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_product);

        shirtImage = findViewById(R.id.shirt_image);
        shirtName = findViewById(R.id.shirt_name);
        shirtPrice = findViewById(R.id.shirt_price);
        shirtCategory = findViewById(R.id.shirt_category);
//        shirtRating = findViewById(R.id.shirt_rating);

        Intent intent = getIntent();
        int image = intent.getExtras().getInt("Image");
        String name = intent.getExtras().getString("Name");
        String price = intent.getExtras().getString("Price");
        String category = intent.getExtras().getString("Category");
//        int rating = (int) intent.getExtras().getFloat("Rating");

        shirtImage.setImageResource(image);
        shirtName.setText(name);
        shirtPrice.setText(price);
        shirtCategory.setText(category);
//        shirtRating.setRating(rating);




    }
}
