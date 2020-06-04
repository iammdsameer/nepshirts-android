package com.nepshirts.android.home;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
import com.nepshirts.android.R;
import com.nepshirts.android.ViewProduct;
import com.nepshirts.android.models.ShirtModel;
import com.nepshirts.android.SearchAdapter;
import java.util.ArrayList;

public class SearchFragment extends Fragment  implements View.OnClickListener{
        private static final String TAG = "SearchFragment";
        private RecyclerView search_results;
        SearchView searchView;
        private EditText search_text;
        private DatabaseReference ref;
        ArrayList<ShirtModel> list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view =inflater.inflate(R.layout.search_fragment,container,false);

            search_results = view.findViewById(R.id.search_results);
            searchView = view.findViewById(R.id.search_input);
            ref = FirebaseDatabase.getInstance().getReference().child("Products");
            return   view;
    }



//    //    // searches for products
//   private void  firebaseProductSearch(){
//       String userQuery = search_text.getText().toString();
//       if(!userQuery.isEmpty()){
//
//           Toast.makeText(getActivity(), userQuery, Toast.LENGTH_SHORT).show();
//
//               search(userQuery);
//
//       }else {
//           search_text.setError("Its Empty");
//           search_text.requestFocus();
//       }
//
//   }
//
////        FirebaseRecyclerOptions<ShirtModel> options = new FirebaseRecyclerOptions.Builder<ShirtModel>().setQuery(ref, new SnapshotParser<ShirtModel>() {
////            @NonNull
////            @Override
////            public ShirtModel parseSnapshot(@NonNull DataSnapshot snapshot) {
////                String id =snapshot.child("id").getValue().toString();
////                String name =  snapshot.child("productNames").getValue().toString();
////                String image = snapshot.child("productImageUrls").getValue().toString();
////                String price = snapshot.child("productPrice").getValue().toString();
////                String rating = snapshot.child("productRating").getValue().toString();
////                String description = snapshot.child("productDescription").getValue().toString();
////                String disPrice = snapshot.child("productDisprice").getValue().toString();
////                String XL = snapshot.child("productSizexl").getValue().toString();
////                String XXL = snapshot.child("productSizexll").getValue().toString();
////                String category  = "Programming"; //TODO
////                return new ShirtModel(  id,name, image,price,rating,description,disPrice,XL,XXL,category);
////            }
////        }).build();
//
//        Query query = FirebaseDatabase.getInstance()
//                .getReference()
//                .child("Products");
//        FirebaseListOptions<ShirtModel> options = new FirebaseListOptions.Builder<ShirtModel>()
//                .setQuery(query, ShirtModel.class)
//                .build();
//        FirebaseListAdapter<ShirtModel> adapter = new FirebaseListAdapter<ShirtModel>(options) {
//            @Override
//            protected void populateView(View v, ShirtModel model, int position) {
//
//            }
//        };
//
//        FirebaseRecyclerAdapter<ShirtModel, SearchItemViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<ShirtModel, SearchItemViewHolder>(
//                options) {
//
//            @Override
//            protected void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position, @NonNull ShirtModel model) {
//
//            }
//
//            @NonNull
//            @Override
//            public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return null;
//            }
//        };
//    }


    @Override
    public void onStart() {
        super.onStart();
        if (ref !=null ){
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        list = new ArrayList<>();
                        for(DataSnapshot res : dataSnapshot.getChildren()){
                            list.add(res.getValue(ShirtModel.class));
                        }
                        SearchAdapter adapter = new SearchAdapter(list,getActivity());
                        search_results.setAdapter(adapter);
                        search_results.setLayoutManager(new LinearLayoutManager(getActivity()));

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        if (searchView !=null){
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
    private void search(String str){
    ArrayList<ShirtModel> mList = new ArrayList<>();
for(ShirtModel shirt:list){
    try{
    if(shirt.getDescription().toLowerCase().contains(str.toLowerCase())
            || shirt.getProductNames().toLowerCase().contains(str.toLowerCase()) ||
            shirt.getProductCategory().toLowerCase().contains(str.toLowerCase())){
        mList.add(shirt);
    }}catch (Exception e){
        //Toast.makeText(getActivity(), "No Results", Toast.LENGTH_SHORT).show();
    }
}
        SearchAdapter searchAdapter = new SearchAdapter(mList,getActivity());
            search_results.setAdapter(searchAdapter);

    }

    @Override
    public void onClick(View v) {

    }

    public void viewProduct(View view) {
        //TODO
        Intent intent = new Intent(getActivity(), ViewProduct.class);
//        intent.putExtra()
    }
}
