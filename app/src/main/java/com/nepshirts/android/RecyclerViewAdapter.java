package com.nepshirts.android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nepshirts.android.models.ShirtsModel;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG= "RecyclerViewAdapter";

    private List<ShirtsModel> modelClassList;
    private Context mContext;

    public RecyclerViewAdapter(List<ShirtsModel> modelClassList, Context mContext){
        this.modelClassList = modelClassList;
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
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.ViewHolder holder, final int position) {
        int img = modelClassList.get(position).getShirtImage();
        final String name = modelClassList.get(position).getShirtName();
        String price = modelClassList.get(position).getShirtPrice();
        String category = modelClassList.get(position).getShirtCategory();
        int rating = (int) modelClassList.get(position).getShirtRating();
        holder.setData(img, name, price, category, rating); //,rating

        holder.parentLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext,SingleProduct.class);
                intent.putExtra("Image",modelClassList.get(position).getShirtImage());
                intent.putExtra("Name",modelClassList.get(position).getShirtName());
                intent.putExtra("Price",modelClassList.get(position).getShirtPrice());
                intent.putExtra("Category",modelClassList.get(position).getShirtCategory());
                intent.putExtra("Rating",modelClassList.get(position).getShirtRating());

                mContext.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return modelClassList.size();
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

        public void setData(int image, String name, String price, String category, float rating) { //, int rating
            shirtImage.setImageResource(image);
            shirtName.setText(name);
            shirtPrice.setText(price);
            shirtCategory.setText(category);
            shirtRating.setRating(rating);
        }
    }
}
