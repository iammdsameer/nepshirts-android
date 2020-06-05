package com.nepshirts.android.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nepshirts.android.R;
import com.nepshirts.android.RecyclerViewAdapter;
import com.nepshirts.android.models.ShirtModel;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<ShirtModel> cartList;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.cart_fragment,container,false);
       textView = view.findViewById(R.id.test);
       recyclerView = view.findViewById(R.id.cart_items);

       SharedPreferences sharedPreferences = getActivity().getSharedPreferences("cartInfo", Context.MODE_PRIVATE);
       String test = sharedPreferences.getString("name","");
       textView.setText(test);

       return view;
    }



    public void deleteFromCart(View view) {
        //TODO
    }
}
