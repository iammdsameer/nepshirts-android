package com.nepshirts.android.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nepshirts.android.R;
import com.nepshirts.android.RecyclerViewAdapter;
import com.nepshirts.android.models.ShirtModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CartFragment extends Fragment {

    private static final String TAG = "CartFragment";
    private RecyclerView recyclerView;
    ArrayList<ShirtModel> cartList;
    TextView textView;
    private  RecyclerView high_rated;
    private DatabaseReference ref;
    ArrayList<ShirtModel> allTshirts = new ArrayList<>();
    ArrayList<ShirtModel> ratedItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.cart_fragment,container,false);
       textView = view.findViewById(R.id.test);
       recyclerView = view.findViewById(R.id.cart_items);
       high_rated = view.findViewById(R.id.high_rated_items);


       SharedPreferences sharedPreferences = getActivity().getSharedPreferences("cartInfo", Context.MODE_PRIVATE);
       String test = sharedPreferences.getString("name","");
       textView.setText(test);
       ref = FirebaseDatabase.getInstance().getReference().child("Products");

        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot res : dataSnapshot.getChildren()) {
                            allTshirts.add(res.getValue(ShirtModel.class));
                        }
                        int i =1;

                        for (ShirtModel shirt : allTshirts) {
                            int rating  = Integer.parseInt(shirt.getRating());
                            Toast.makeText(getActivity(), "Rating: "+rating, Toast.LENGTH_SHORT).show();

                                if (rating>=4){
                                    ratedItems.add(shirt);
                                    Toast.makeText(getActivity(), "Added"+i, Toast.LENGTH_SHORT).show();
                                    i++;
                                }

                        }


                    }
                    initRecyclerView();


                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

       return view;
    }
    private void initRecyclerView() {
        RecyclerViewAdapter adpt = new RecyclerViewAdapter(ratedItems, getActivity());
        high_rated.setAdapter(adpt);
        Log.d(TAG, "initRecyclerView: "+ ratedItems.toString());

        high_rated.setLayoutManager(new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }



    public void deleteFromCart(View view) {
        //TODO
    }
}
