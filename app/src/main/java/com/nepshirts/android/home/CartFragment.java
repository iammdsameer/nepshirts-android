package com.nepshirts.android.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nepshirts.android.AllProductsFragment;
import com.nepshirts.android.CartAdapter;
import com.nepshirts.android.R;
import com.nepshirts.android.RecyclerViewAdapter;
import com.nepshirts.android.SearchAdapter;
import com.nepshirts.android.models.OrderModel;
import com.nepshirts.android.models.ShirtModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CartFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "CartFragment";
    private RecyclerView recyclerView;
    TextView textView;
    private  RecyclerView high_rated;
    private DatabaseReference ref;
    ArrayList<ShirtModel> allTshirts = new ArrayList<>();
    ArrayList<ShirtModel> ratedItems = new ArrayList<>();
    private TextView subtotalView, totalView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.cart_fragment,container,false);
       textView = view.findViewById(R.id.test);
       recyclerView = view.findViewById(R.id.cart_items);
       high_rated = view.findViewById(R.id.high_rated_items);

        ImageView humour = view.findViewById(R.id.category_humour);
        ImageView programming = view.findViewById(R.id.category_programming);
        ImageView event = view.findViewById(R.id.category_event);
        ImageView fandom = view.findViewById(R.id.category_fandom);

        viewCart();

       ref = FirebaseDatabase.getInstance().getReference().child("Products");

        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot res : dataSnapshot.getChildren()) {
                            allTshirts.add(res.getValue(ShirtModel.class));
                        }

                        for (ShirtModel shirt : allTshirts) {
                            int rating  = Integer.parseInt(shirt.getRating());

                                if (rating>=4){
                                    ratedItems.add(shirt);
                                }

                        }


                    }

                    initRecyclerView();


                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            subtotalView = view.findViewById(R.id.subtotal_price);
            totalView = view.findViewById(R.id.total_price);

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("priceInfo", Context.MODE_PRIVATE);
            String subTotal = sharedPreferences.getString("cartPrice", "0");
            subtotalView.setText("Rs." + subTotal);

            int grandTotal = Integer.parseInt(subTotal) + 150;

            totalView.setText("Rs." + Integer.toString(grandTotal));
        }

        humour.setOnClickListener(this);
        programming.setOnClickListener(this);
        event.setOnClickListener(this);
        fandom.setOnClickListener(this);

       return view;
    }
    private void initRecyclerView() {
        RecyclerViewAdapter adpt = new RecyclerViewAdapter(ratedItems, getActivity());
        high_rated.setAdapter(adpt);
        Log.d(TAG, "initRecyclerView: "+ ratedItems.toString());
        if (viewCart() != null) {
            CartAdapter cartAdpt = new CartAdapter(viewCart(), getActivity());
            recyclerView.setAdapter(cartAdpt);
        } else {
            Toast.makeText(getActivity(), "Your Cart is empty!", Toast.LENGTH_SHORT).show();
        }



        high_rated.setLayoutManager(new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

//        SearchAdapter searchAdapter = new SearchAdapter(getActivity(), viewCart());

    }

    @Override
    public void onClick(View v) {

        Fragment currentFragment = null;
        Bundle args = new Bundle();
        switch (v.getId()) {
            case R.id.all_products:
                currentFragment = new AllProductsFragment();
                break;
            case R.id.category_humour:
                currentFragment = new CategoryFragment();
                args.putString("category", "humour");
                currentFragment.setArguments(args);
                break;

            case R.id.category_event:
                currentFragment = new CategoryFragment();
                args.putString("category", "event");
                currentFragment.setArguments(args);
                break;

            case R.id.category_fandom:
                currentFragment = new CategoryFragment();
                args.putString("category", "fandom");
                currentFragment.setArguments(args);
                break;

            case R.id.category_programming:
                currentFragment = new CategoryFragment();
                args.putString("category", "programming");
                currentFragment.setArguments(args);
                break;
        }
        getFragmentManager().beginTransaction().replace(R.id.frame_id, currentFragment).commit();

    }

    public ArrayList<OrderModel> viewCart() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("cartInfo", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("cartItems", "");
        Type type = new TypeToken<ArrayList<OrderModel>>() {
        }.getType();
        ArrayList<OrderModel> cartList = gson.fromJson(json, type);

        return cartList;
    }

    public void deleteFromCart(View view) {
        //TODO
    }
}
