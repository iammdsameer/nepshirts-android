package com.nepshirts.android;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nepshirts.android.models.ShirtModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG= "RecyclerViewAdapter";

    private List<ShirtModel> list;
    private Context mContext;

    public RecyclerViewAdapter(List<ShirtModel> modelClassList, Context mContext){
        this.list = modelClassList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shirts_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.ViewHolder holder, final int i) {
        int initialPrice  = Integer.parseInt(list.get(i).getPrice());
        int discPrice =Integer.parseInt(list.get(i).getDisPrice());
        int percent = (discPrice*100)/initialPrice;
        int rating = Integer.parseInt(list.get(i).getRating());
        String p = String.valueOf(percent);
        final Uri img = Uri.parse(list.get(i).getImageUrl());

        holder.shirtName.setText(list.get(i).getProductNames());
//        holder.price1.setText(list.get(i).getPrice());
//        holder.price1.setPaintFlags(holder.price1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.shirtPrice.setText(list.get(i).getDisPrice());
//        holder.percentage.setText(p+"%"+" Discount");

        holder.shirtCategory.setText(list.get(i).getProductCategory());
        holder.shirtRating.setRating(rating);

        Picasso.get().load(img).into(holder.shirtImage);


        holder.parentLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ViewProduct.class);
                intent.putExtra("Image",img.toString());
                intent.putExtra("Name",list.get(i).getProductNames());
                intent.putExtra("Price",list.get(i).getPrice());
                intent.putExtra("Category",list.get(i).getProductCategory());
                intent.putExtra("Rating",list.get(i).getRating());

                mContext.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView shirtImage;
        private TextView shirtName;
        private TextView shirtPrice;
        private TextView shirtCategory;
        private RatingBar shirtRating;
        private LinearLayout parentLayout;


        public ViewHolder(@NonNull View View) {
            super(View);
            shirtImage = View.findViewById(R.id.shirt_image);
            shirtName = View.findViewById(R.id.shirt_name);
            shirtPrice = View.findViewById(R.id.shirt_price);
            shirtCategory = View.findViewById(R.id.shirt_category);
            shirtRating = View.findViewById(R.id.shirt_rating);
            parentLayout = View.findViewById(R.id.parent_layout);

        }

//        public void setData(int image, String name, String price, String category, float rating) { //, int rating
//            shirtImage.setImageResource(image);
//            shirtName.setText(name);
//            shirtPrice.setText(price);
//            shirtCategory.setText(category);
//            shirtRating.setRating(rating);
//        }
    }
}
