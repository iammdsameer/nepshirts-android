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

import com.nepshirts.android.R;
import com.nepshirts.android.RecyclerViewAdapter;
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
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);
        Log.d(TAG, "onCreateView: started");


        String category = getArguments().getString("category");

        recyclerView = view.findViewById(R.id.recycler_view);
        String urlss= "https://res.cloudinary.com/nepshirts/image/upload/$wpsize_!_cld_full!,w_1024,h_1024,c_scale/v1589152475/wp-content/uploads/AF365BA9-82C9-432F-9AD6-37B5690BD2A1-1024x1024-1.jpeg";
        modelClassList.add(new ShirtModel("T1", "Visit Nepal 2020",urlss,"999", "5","Lorem Ipsum", "100",true,true,"Namaste"));

//        modelClassList.add(new ShirtModel(R.drawable.t1, "Visit Nepal 2020","Rs. 999", category, 4));
//        modelClassList.add(new ShirtModel(R.drawable.binary, "Binary","Rs. 699", category, 3));
//        modelClassList.add(new ShirtModel(R.drawable.t1, "getLaugh()","Rs. 500", category, 5));
//        modelClassList.add(new ShirtModel(R.drawable.incognito, "test","Free", category, 5));

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
