package com.nepshirts.android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.List;

public class SingleCategory extends AppCompatActivity {
    List<ShirtsModelClass> modelClassList = new ArrayList<>();
    private GoogleSignInAccount acct;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case R.id.profileIcon:
               acct = GoogleSignIn.getLastSignedInAccount(this);
               if(acct ==null){
                Intent intent = new Intent(SingleCategory.this,UserProfile.class);
                startActivity(intent);
                return true;
               }else{
                   Intent intent = new Intent(SingleCategory.this,MainActivity.class);
                   startActivity(intent);
                   return true;
               }

           default:
               return super.onOptionsItemSelected(item);

       }

    }
}
