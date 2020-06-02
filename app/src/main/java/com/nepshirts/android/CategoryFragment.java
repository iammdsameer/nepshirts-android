package com.nepshirts.android;

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
import android.widget.TextView;

import com.nepshirts.android.models.ShirtModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {


    private static final String TAG = "CategoryFragment";


    private static final int NUM_COLUMNS = 2; //staggered vs normal

    List<ShirtModel> modelClassList = new ArrayList<>();

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        Log.d(TAG, "onCreateView: started");


        String category = getArguments().getString("category");

        recyclerView = view.findViewById(R.id.recycler_view);

        modelClassList.add(new ShirtModel(R.drawable.t1, "Visit Nepal 2020","Rs. 999", category, 4));
        modelClassList.add(new ShirtModel(R.drawable.binary, "Binary","Rs. 699", category, 3));
        modelClassList.add(new ShirtModel(R.drawable.t1, "getLaugh()","Rs. 500", category, 5));
        modelClassList.add(new ShirtModel(R.drawable.incognito, "test","Free", category, 5));

        initRecyclerView();

        return view;
    }


    private void initRecyclerView(){
//        Log.d(TAG, "initRecyclerView: init recyclerview.");

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(modelClassList, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), NUM_COLUMNS));

    }
}
