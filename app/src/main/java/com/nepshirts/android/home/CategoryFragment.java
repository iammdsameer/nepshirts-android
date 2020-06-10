package com.nepshirts.android.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nepshirts.android.R;
import com.nepshirts.android.RecyclerViewAdapter;
import com.nepshirts.android.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {


    private static final String TAG = "CategoryFragment";


    private static final int NUM_COLUMNS = 2; //staggered vs normal

    List<ProductModel> modelClassList = new ArrayList<>();
    private ImageView headerImage;

    private DatabaseReference ref;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);
        Log.d(TAG, "onCreateView: started");

        headerImage = view.findViewById(R.id.category_header_image);
        String category = getArguments().getString("category");

        recyclerView = view.findViewById(R.id.recycler_view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        final String category = getArguments().getString("category");
        ref = FirebaseDatabase.getInstance().getReference().child("Products");
        if (category.toLowerCase().equals("programming")) {
            headerImage.setImageResource(R.drawable.header4);
        } else if (category.toLowerCase().equals("patriotic")) {
            headerImage.setImageResource(R.drawable.header3);
        } else if (category.toLowerCase().equals("humour")) {
            headerImage.setImageResource(R.drawable.header6);
        } else if (category.toLowerCase().equals("fandom")) {
            headerImage.setImageResource(R.drawable.header8);
        }
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        ArrayList<ProductModel> filteredList = new ArrayList<>();
                        for (DataSnapshot res : dataSnapshot.getChildren()) {
                            if (res.getValue(ProductModel.class).getProductCategory().toLowerCase().equals(category.toLowerCase())) {
                                filteredList.add(res.getValue(ProductModel.class));
                            }
                        }

                        initRecyclerView(filteredList);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {
            Toast.makeText(getActivity(), "NULL", Toast.LENGTH_LONG).show();
            Log.d(TAG, "onStart: null");
        }


    }


    private void initRecyclerView(ArrayList<ProductModel> filteredList) {
//        Log.d(TAG, "initRecyclerView: init recyclerview.");

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(filteredList, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), NUM_COLUMNS));

    }
}
