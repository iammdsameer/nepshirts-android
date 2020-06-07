package com.nepshirts.android;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.client.core.Tag;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nepshirts.android.models.OrderModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.cartViewHolder> {

    ArrayList<OrderModel> list;
        private Context mContext;
        DatabaseReference ref;
    private static final String TAG = "CartAdapter";


    public CartAdapter(ArrayList<OrderModel> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public cartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items,parent,false);
        return new cartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final cartViewHolder holder, final int i) {
            ref = FirebaseDatabase.getInstance().getReference().child("Products").child(list.get(i).getProductId());
        Log.d(TAG, "onBindViewHolder: "+list.get(i).getProductId());
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                    try {
                        holder.category.setText(dataSnapshot.child("productCategory").getValue().toString());
                        holder.title.setText(dataSnapshot.child("productNames").getValue().toString());

                        Picasso.get().load(dataSnapshot.child("imageUrl").getValue().toString()).into(holder.productImage);
                    }catch (NullPointerException e){
                        Toast.makeText(mContext, "Madarchod", Toast.LENGTH_SHORT).show();
                    }
                        if(list.get(i).getColor().toLowerCase().equals("white")) {
                            holder.productColor.setBackgroundColor(Color.parseColor("#FFFFFF"));

                        }else{
                            holder.productColor.setBackgroundColor(Color.parseColor("#000000"));
                        }
                        try {
                            int price = Integer.parseInt( dataSnapshot.child("price").getValue().toString());
                            int disPrice = Integer.parseInt( dataSnapshot.child("disPrice").getValue().toString());
                            if(disPrice !=0 && price == disPrice){
                                holder.price.setText(disPrice);
                            }else{
                                holder.price.setText(price);
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        holder.size.setText(list.get(i).getSize());
                        holder.quantity.setText(list.get(i).getQuantity());



                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }






    @Override
    public int getItemCount() {
        return list.size();
    }
    class cartViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage;
        TextView category,title,price,quantity,size;
        CardView parentLayout;
        RadioButton productColor;

        public cartViewHolder(@NonNull View view) {
            super(view);
            productImage = view.findViewById(R.id.cart_item_image);
            category = view.findViewById(R.id.cart_item_category);
            price = view.findViewById(R.id.cart_item_price);
            title = view.findViewById(R.id.cart_item_title);
            quantity = view.findViewById(R.id.cart_item_quantity);
            size  = view.findViewById(R.id.cart_item_size);
            productColor = view.findViewById(R.id.cart_tshirt_color);
        }
    }
}
