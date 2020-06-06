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
import com.nepshirts.android.models.ShirtModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {


    private static final String TAG = "CategoryFragment";


    private static final int NUM_COLUMNS = 2; //staggered vs normal

    List<ShirtModel> modelClassList = new ArrayList<>();
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
//        String urlss= "https://res.cloudinary.com/nepshirts/image/upload/$wpsize_!_cld_full!,w_1024,h_1024,c_scale/v1589152475/wp-content/uploads/AF365BA9-82C9-432F-9AD6-37B5690BD2A1-1024x1024-1.jpeg";
//        modelClassList.add(new ShirtModel("T1", "Visit Nepal 2020",urlss,"999", "5","Lorem Ipsum", "100",true,true,"Namaste"));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ref = FirebaseDatabase.getInstance().getReference().child("Products");
        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String category = getArguments().getString("category");
                    if (dataSnapshot.exists()) {
                        ArrayList<ShirtModel> filteredList = new ArrayList<>();
                        for (DataSnapshot res : dataSnapshot.getChildren()) {
                            if(res.getValue(ShirtModel.class).getProductCategory().toLowerCase().equals(category.toLowerCase())) {
                                if(category.toLowerCase().equals("programming") ){
                                    headerImage.setImageResource(R.drawable.header4);
                                }else if(category.toLowerCase().equals("patriotic") ){
                                    headerImage.setImageResource(R.drawable.header3);
                                }else if(category.toLowerCase().equals("humour") ){
                                    headerImage.setImageResource(R.drawable.header6);
                                }else if(category.toLowerCase().equals("fandom") ){
                                    headerImage.setImageResource(R.drawable.header8);
                                }

                                filteredList.add(res.getValue(ShirtModel.class));
                            }
                        }

                        initRecyclerView(filteredList);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else{
            Toast.makeText(getActivity(), "NULL", Toast.LENGTH_LONG).show();
            Log.d(TAG, "onStart: null");
        }


    }


    private void initRecyclerView(ArrayList<ShirtModel> filteredList){
//        Log.d(TAG, "initRecyclerView: init recyclerview.");

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(filteredList, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), NUM_COLUMNS));

    }
}
