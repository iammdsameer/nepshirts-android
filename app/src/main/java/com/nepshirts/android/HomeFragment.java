package com.nepshirts.android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "HomeFragment";

     
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        Log.d(TAG, "onCreateView: started");

        ImageView humour = view.findViewById(R.id.category_humour);
        ImageView programming = view.findViewById(R.id.category_programming);
        ImageView event = view.findViewById(R.id.category_event);
        ImageView fandom = view.findViewById(R.id.category_fandom);

        humour.setOnClickListener(this);
        programming.setOnClickListener(this);
        event.setOnClickListener(this);
        fandom.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {

        Fragment currentFragment=null;
        Bundle args = new Bundle();
        switch (v.getId()){
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


}
