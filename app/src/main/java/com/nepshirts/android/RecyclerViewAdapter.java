package com.nepshirts.android;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nepshirts.android.models.ShirtModel;
import com.nepshirts.android.utils.Constant;
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
        View view;
        switch (viewType){
            case (Constant.HORIZONTAL_VIEW_TYPE):
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shirts_layout_horizontal, parent, false);
                return new ViewHolder(view);

            case (Constant.GRID_VIEW_TYPE):
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shirts_layout_grid, parent, false);
                return new ViewHolder(view);

            default:
                throw new IllegalArgumentException();
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(list.size() > 5){
            return Constant.GRID_VIEW_TYPE;
        }else {
            return Constant.HORIZONTAL_VIEW_TYPE;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.ViewHolder holder, final int i) {
        int initialPrice  = Integer.parseInt(list.get(i).getPrice());
        int discPrice =Integer.parseInt(list.get(i).getDisPrice());
//        int percent = (discPrice*100)/initialPrice;
        int rating = Integer.parseInt(list.get(i).getRating());
//        String p = String.valueOf(percent);
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
        private CardView parentLayout;


        public ViewHolder(@NonNull View View) {
            super(View);
            shirtImage = View.findViewById(R.id.shirt_image);
            shirtName = View.findViewById(R.id.shirt_name);
            shirtPrice = View.findViewById(R.id.shirt_price);
            shirtCategory = View.findViewById(R.id.shirt_category);
            shirtRating = View.findViewById(R.id.shirt_rating);
            parentLayout = View.findViewById(R.id.parent_layout);

        }

    }
}
