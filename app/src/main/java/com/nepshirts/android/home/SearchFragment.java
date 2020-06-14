package com.nepshirts.android.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nepshirts.android.AllProductsFragment;
import com.nepshirts.android.R;
import com.nepshirts.android.models.ProductModel;
import com.nepshirts.android.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "SearchFragment";
    private RecyclerView search_results;
    SearchView searchView;
    private EditText search_text;
    private DatabaseReference ref;
    List<ProductModel> list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        ImageView humour = view.findViewById(R.id.category_humour);
        ImageView programming = view.findViewById(R.id.category_programming);
        ImageView event = view.findViewById(R.id.category_event);
        ImageView fandom = view.findViewById(R.id.category_fandom);

        search_results = view.findViewById(R.id.search_results);
        searchView = view.findViewById(R.id.search_input);
        ref = FirebaseDatabase.getInstance().getReference().child("Products");

        humour.setOnClickListener(this);
        programming.setOnClickListener(this);
        event.setOnClickListener(this);
        fandom.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        list = new ArrayList<>();
                        for (DataSnapshot res : dataSnapshot.getChildren()) {
                            list.add(res.getValue(ProductModel.class));
                        }
                        SearchAdapter adapter = new SearchAdapter(list, getActivity());
                        search_results.setAdapter(adapter);
                        search_results.setLayoutManager(new LinearLayoutManager(getActivity()){
                            @Override
                            public boolean canScrollVertically() {
                                return false;
                            }
                        });

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    search(query);
                    return true;
                }
            });
        }

    }

    private void search(String str) {
        ArrayList<ProductModel> mList = new ArrayList<>();
        for (ProductModel shirt : list) {
            try {
                if (shirt.getDescription().toLowerCase().contains(str.toLowerCase())
                        || shirt.getProductNames().toLowerCase().contains(str.toLowerCase()) ||
                        shirt.getProductCategory().toLowerCase().contains(str.toLowerCase())) {
                    mList.add(shirt);
                }
            } catch (Exception e) {
                //Toast.makeText(getActivity(), "No Results", Toast.LENGTH_SHORT).show();
            }
        }
        SearchAdapter searchAdapter = new SearchAdapter(mList, getActivity());
        search_results.setAdapter(searchAdapter);

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
}
