package com.nepshirts.android.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nepshirts.android.AllProductsFragment;
import com.nepshirts.android.CartAdapter;
import com.nepshirts.android.CheckOut;
import com.nepshirts.android.R;
import com.nepshirts.android.RecyclerViewAdapter;
import com.nepshirts.android.ViewProduct;
import com.nepshirts.android.models.OrderModel;
import com.nepshirts.android.models.ProductModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CartFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "CartFragment";
    private RecyclerView recyclerView;
    TextView textView;
    private RecyclerView high_rated;
    private DatabaseReference ref;
    ArrayList<ProductModel> allTshirts = new ArrayList<>();
    ArrayList<ProductModel> ratedItems = new ArrayList<>();
    private TextView subtotalView, totalView;
    Button checkout_button;
    private DatabaseReference mDatabase;
    private String name, city, street, phone;
    private CardView calculationCard;
    private TextView cartFragmetTitle;
    private ImageView emptyCart;

    private int grandTotal;

    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment, container, false);

        recyclerView = view.findViewById(R.id.cart_items);
        high_rated = view.findViewById(R.id.high_rated_items);
        checkout_button = view.findViewById(R.id.check_out_button);
        ImageView humour = view.findViewById(R.id.category_humour);
        ImageView programming = view.findViewById(R.id.category_programming);
        ImageView event = view.findViewById(R.id.category_event);
        ImageView fandom = view.findViewById(R.id.category_fandom);
        user = FirebaseAuth.getInstance().getCurrentUser();
        calculationCard = view.findViewById(R.id.calculation_card);
        cartFragmetTitle = view.findViewById(R.id.cart_fragment_title);
        emptyCart = view.findViewById(R.id.empty_cart);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (user != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    name = dataSnapshot.child("fullName").getValue().toString();
                    city = dataSnapshot.child("city").getValue().toString();
                    street = dataSnapshot.child("street").getValue().toString();
                    phone = dataSnapshot.child("userPhoneNumber").getValue().toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        viewCart();

        ref = FirebaseDatabase.getInstance().getReference().child("Products");

        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot res : dataSnapshot.getChildren()) {
                            allTshirts.add(res.getValue(ProductModel.class));
                        }

                        for (ProductModel shirt : allTshirts) {
                            int rating = Integer.parseInt(shirt.getRating());

                            if (rating >= 4) {
                                if (ratedItems.size() < 6) {
                                    ratedItems.add(shirt);
                                }
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

            grandTotal = Integer.parseInt(subTotal) + 150;

            totalView.setText("Rs." + Integer.toString(grandTotal));
        }

        humour.setOnClickListener(this);
        programming.setOnClickListener(this);
        event.setOnClickListener(this);
        fandom.setOnClickListener(this);

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.swipe_refresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Fragment frg;
                frg = getActivity().getSupportFragmentManager().findFragmentByTag("cart_fragment");
                final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();
                pullToRefresh.setRefreshing(false);
            }
        });

        return view;
    }

    private void initRecyclerView() {
        RecyclerViewAdapter adpt = new RecyclerViewAdapter(ratedItems, getActivity());
        high_rated.setAdapter(adpt);
        Log.d(TAG, "initRecyclerView: " + ratedItems.toString());
        if (viewCart() != null) {
            CartAdapter cartAdpt = new CartAdapter(viewCart(), getActivity());
            recyclerView.setAdapter(cartAdpt);
        } else {
            calculationCard.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            checkout_button.setVisibility(View.GONE);
            emptyCart.setVisibility(View.VISIBLE);
           // Toast.makeText(getActivity(), "Your Cart is empty!", Toast.LENGTH_SHORT).show();

            Toast.makeText(getActivity(), "Your Cart is Empty!!", Toast.LENGTH_SHORT).show();

        }
        checkout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = getLayoutInflater();
                    builder.setView(inflater.inflate(R.layout.checkout_dialogue, null));
                    builder.setCancelable(true);

                    final AlertDialog dialogBox = builder.create();
                    dialogBox.show();
                    dialogBox.getWindow().setLayout(820, 520);
//                    dialogBox.setCanceledOnTouchOutside(true);  todo
                    TextView totalPrice = dialogBox.findViewById(R.id.total_price);
                    totalPrice.setText("Rs." + grandTotal);


                    Button confirm = dialogBox.findViewById(R.id.confirm_checkout);
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkout();
                            dialogBox.dismiss();
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "Please Sign In to  Checkout", Toast.LENGTH_SHORT).show();
                }

            }
        });


        high_rated.setLayoutManager(new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

//        SearchAdapter searchAdapter = new SearchAdapter(getActivity(), viewCart());

    }


    private void checkout() {

        for (OrderModel om : viewCart()) {
            try {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                String key = database.getReference().child("Orders").push().getKey();
                mDatabase.child("Orders").child(key).child("productId").setValue(om.getProductId());
                mDatabase.child("Orders").child(key).child("userName").setValue(name);
                mDatabase.child("Orders").child(key).child("userId").setValue(om.getUserId());
                mDatabase.child("Orders").child(key).child("productColor").setValue(om.getColor());
                mDatabase.child("Orders").child(key).child("size").setValue(om.getSize());
                mDatabase.child("Orders").child(key).child("quantity").setValue(om.getQuantity());
                mDatabase.child("Orders").child(key).child("city").setValue(city);
                mDatabase.child("Orders").child(key).child("street").setValue(street);
                mDatabase.child("Orders").child(key).child("phone").setValue(phone);

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), CheckOut.class);
        startActivity(intent);
        getActivity().finish();

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
                args.putString("category", "patriotic");
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
        getFragmentManager().beginTransaction().replace(R.id.shimmer_frame_id, currentFragment).commit();

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
