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

/**
 * A simple {@link Fragment} subclass.
 */
public class AllProductsFragment extends Fragment {

    public AllProductsFragment() {
        // Required empty public constructor
    }

    private static final String TAG = "CategoryFragment";


    private static final int NUM_COLUMNS = 2; //staggered vs normal

    List<ShirtModel> modelClassList = new ArrayList<>();

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_fragment, container, false);
        Log.d(TAG, "onCreateView: started");


        recyclerView = view.findViewById(R.id.recycler_view);
        String urlss= "https://res.cloudinary.com/nepshirts/image/upload/$wpsize_!_cld_full!,w_1024,h_1024,c_scale/v1589152475/wp-content/uploads/AF365BA9-82C9-432F-9AD6-37B5690BD2A1-1024x1024-1.jpeg";
        modelClassList.add(new ShirtModel("T1", "Visit Nepal 2020",urlss,"999", "5","Lorem Ipsum", "100",true,true,"Namaste"));
//        modelClassList.add(new ShirtModel(R.drawable.binary, "Binary","Rs. 699", "Programming", 3));
//        modelClassList.add(new ShirtModel(R.drawable.t1, "getLaugh()","Rs. 500", "Fandom", 5));
//        modelClassList.add(new ShirtModel(R.drawable.incognito, "test","Free", "Humour", 5));

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
