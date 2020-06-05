package com.nepshirts.android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nepshirts.android.models.ShirtModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.searchViewHolder> {
        ArrayList<ShirtModel> list;
        private Context mContext;

    public SearchAdapter(ArrayList<ShirtModel>list, Context mContext){
        this.list = list;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public searchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list,parent,false);
        return new searchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull searchViewHolder holder,final int i) {
        int initialPrice  = Integer.parseInt(list.get(i).getPrice());
        int discPrice =Integer.parseInt(list.get(i).getDisPrice());
        try {
            int percent = (discPrice * 100) / initialPrice;
            if(percent <=0){
                holder.percentage.setVisibility(View.GONE);
            }else if(percent ==100){
                holder.percentage.setText("FREE!!");
            }else {
                String p = String.valueOf(percent);
                holder.percentage.setText(p + "%" + " Discount");
            }
        }catch (Exception e){


        }

        Uri img = Uri.parse(list.get(i).getImageUrl());


        holder.title.setText(list.get(i).getProductNames());

        holder.price1.setText(list.get(i).getPrice());
        holder.price1.setPaintFlags(holder.price1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.price2.setText(list.get(i).getDisPrice());


        holder.category.setText(list.get(i).getProductCategory());

        Picasso.get().load(img).into(holder.productImage);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ViewProduct.class);
                intent.putExtra("Image",list.get(i).getImageUrl());
                intent.putExtra("Name",list.get(i).getProductNames());
                intent.putExtra("Price",list.get(i).getPrice());
                intent.putExtra("Category",list.get(i).getProductCategory());
                intent.putExtra("Rating",list.get(i).getRating());

                mContext.startActivity(intent);
            }
        });

//        holder.desc.setText(list.get(i).getDescription());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class  searchViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage;
        TextView category,title,price1,price2,percentage,desc;
        CardView parentLayout;

        public searchViewHolder(@NonNull View view) {
            super(view);
            productImage = view.findViewById(R.id.search_result_image);
            category = view.findViewById(R.id.search_result_item_category);
            title = view.findViewById(R.id.search_result_item_title);
            price1 = view.findViewById(R.id.search_result_price1);
            price2 = view.findViewById(R.id.search_result_price2);
            percentage   = view.findViewById(R.id.discount_percentage);
//            desc = view.findViewById(R.id.search_result_item_desc);
            parentLayout = view.findViewById(R.id.search_result_card);

        }
    }
}
