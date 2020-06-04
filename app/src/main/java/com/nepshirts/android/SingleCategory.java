package com.nepshirts.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.nepshirts.android.models.ShirtModel;

import java.util.ArrayList;
import java.util.List;

public class SingleCategory extends AppCompatActivity {

    private static final int NUM_COLUMNS = 2; //staggered vs normal

    List<ShirtModel> modelClassList = new ArrayList<>();

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_category);

        recyclerView = findViewById(R.id.recycler_view);

        String shirtCategory = "Event";
        String urlss= "https://res.cloudinary.com/nepshirts/image/upload/$wpsize_!_cld_full!,w_1024,h_1024,c_scale/v1589152475/wp-content/uploads/AF365BA9-82C9-432F-9AD6-37B5690BD2A1-1024x1024-1.jpeg";
        modelClassList.add(new ShirtModel("T1", "Visit Nepal 2020",urlss,"999", "5","Lorem Ipsum", "100",true,true,"Namaste"));

//        modelClassList.add(new ShirtModel(R.drawable.t1, "Visit Nepal 2020","Rs. 999", "Event", 4));
//        modelClassList.add(new ShirtModel(R.drawable.t1, "Binary","Rs. 699", "Programming", 3));
//        modelClassList.add(new ShirtModel(R.drawable.t1, "getLaugh()","Rs. 500", "Humour", 5));
//        modelClassList.add(new ShirtModel(R.drawable.t1, "test","Free", "haha", 5));

        initRecyclerView();
    }

    private void initRecyclerView(){
//        Log.d(TAG, "initRecyclerView: init recyclerview.");

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(modelClassList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, NUM_COLUMNS));

    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater =getMenuInflater();
//        inflater.inflate(R.menu.toolbar,menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//       switch (item.getItemId()){
//           case R.id.profile_icon_id:
//               if(FirebaseAuth.getInstance().getCurrentUser() !=null){
//                Intent intent = new Intent(this,UserProfile.class);
//                this.startActivity(intent);
//                return true;
//               }else{
//                   Intent intent = new Intent(this, LoginActivity.class);
//                   this.startActivity(intent);
//                   return true;
//               }
//
//           default:
//               return super.onOptionsItemSelected(item);
//
//       }
//    }


}
