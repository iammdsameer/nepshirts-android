package com.nepshirts.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG= "RecyclerViewAdapter";

    private List<ShirtsModelClass> modelClassList;

    public RecyclerViewAdapter(List<ShirtsModelClass> modelClassList){
        this.modelClassList = modelClassList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shirts_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        int img = modelClassList.get(position).getShirtImage();
        String name = modelClassList.get(position).getShirtName();
        String price = modelClassList.get(position).getShirtPrice();
        String category = modelClassList.get(position).getShirtCategory();
//        int rating = modelClassList.get(position).getShirtRating();
        holder.setData(img, name, price, category); //,rating

//        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                Toast.makeText()
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return modelClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         ImageView shirtImage;
        private TextView shirtName;
        private TextView shirtPrice;
        private TextView shirtCategory;
//        private TextView shirtRating;
//        private LinearLayout parentLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shirtImage = itemView.findViewById(R.id.shirt_image);
            shirtName = itemView.findViewById(R.id.shirt_name);
            shirtPrice = itemView.findViewById(R.id.shirt_price);
            shirtCategory = itemView.findViewById(R.id.shirt_category);
//            shirtRating = itemView.findViewById(R.id.shirt_rating);
//            parentLayout = itemView.findViewById(R.id.parent_layout);

        }

        public void setData(int image, String name, String price, String category) { //, int rating
            shirtImage.setImageResource(image);
            shirtName.setText(name);
            shirtPrice.setText(price);
            shirtCategory.setText(category);
//            shirtRating.setRawInputType(rating);
        }
    }
}
