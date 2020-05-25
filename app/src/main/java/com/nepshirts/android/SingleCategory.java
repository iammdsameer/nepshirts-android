package com.nepshirts.android;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SingleCategory extends AppCompatActivity {
    List<ShirtsModelClass> modelClassList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_category);

        modelClassList.add(new ShirtsModelClass(R.drawable.t1, "Visit Nepal 2020","Rs. 999", "Event", 4)); //rating todo
        modelClassList.add(new ShirtsModelClass(R.drawable.t1, "Binary","Rs. 699", "Programming", 3));
        modelClassList.add(new ShirtsModelClass(R.drawable.t1, "getLaugh()","Rs. 500", "Humour", 5));
        modelClassList.add(new ShirtsModelClass(R.drawable.t1, "test","Free", "haha", 5));

        initRecyclerView();
    }

    private void initRecyclerView(){
//        Log.d(TAG, "initRecyclerView: init recyclerview.");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(modelClassList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }
}
