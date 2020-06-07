package com.nepshirts.android.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nepshirts.android.AllProductsFragment;
import com.nepshirts.android.ImageSliderAdapter;
import com.nepshirts.android.R;
import com.nepshirts.android.RecyclerViewAdapter;
import com.nepshirts.android.models.ShirtModel;
import com.nepshirts.android.models.SliderModel;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "HomeFragment";
    ArrayList<ShirtModel> tshirts = new ArrayList<>();
    private DatabaseReference ref;
    ArrayList<ShirtModel> randomShirts = new ArrayList<>();
    private RecyclerView recyclerView;
    ArrayList<ShirtModel> onSaleTshirts = new ArrayList<>();
    private RecyclerView onSaleItems;
    private SliderView sliderView;
    private List<SliderModel> images;
    private CardView cardView;
    private RecyclerView highRatedItems;
    ArrayList<ShirtModel> ratedItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        Log.d(TAG, "onCreateView: started");

        TextView viewMore = view.findViewById(R.id.all_products);
        ImageView humour = view.findViewById(R.id.category_humour);
        ImageView programming = view.findViewById(R.id.category_programming);
        ImageView event = view.findViewById(R.id.category_event);
        ImageView fandom = view.findViewById(R.id.category_fandom);

        onSaleItems = view.findViewById(R.id.on_sale_items);

        recyclerView = view.findViewById(R.id.homepage_products);
        ref = FirebaseDatabase.getInstance().getReference().child("Products");

        highRatedItems = view.findViewById(R.id.high_rated_items);

        images = new ArrayList<SliderModel>();
        sliderView = view.findViewById(R.id.home_header_image);
        images.add(new SliderModel(R.drawable.header1));
        images.add(new SliderModel(R.drawable.header2));
        images.add(new SliderModel(R.drawable.header5));

        sliderView.setSliderAdapter(new ImageSliderAdapter(getActivity(), images));

        viewMore.setOnClickListener(this);
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

                        for (DataSnapshot res : dataSnapshot.getChildren()) {
                            tshirts.add(res.getValue(ShirtModel.class));

                        }
                        for (ShirtModel shirt : tshirts) {
                            int initialPrice = Integer.parseInt(shirt.getPrice());
                            int discountPrice = Integer.parseInt(shirt.getDisPrice());
                            try {
                                if (discountPrice < initialPrice) {
                                    onSaleTshirts.add(shirt);
                                }
                            } catch (Exception e) {
                                //Toast.makeText(getActivity(), "No Results", Toast.LENGTH_SHORT).show();
                            }
                        }
                        int i =1;
                        for (ShirtModel shirt : tshirts) {
                            int rating  = Integer.parseInt(shirt.getRating());
                            Toast.makeText(getActivity(), "Rating: "+rating, Toast.LENGTH_SHORT).show();

                            if (rating>=4){
                                ratedItems.add(shirt);
                                Toast.makeText(getActivity(), "Added"+i, Toast.LENGTH_SHORT).show();
                                i++;
                            }

                        }


                        Log.d(TAG, "onDataChange: " + tshirts);
                        try {
                            List<Integer> nums = new ArrayList<>();
                            Random random = new Random();
                            while (nums.size() < 4) {
                                int randNum = random.nextInt(tshirts.size());
                                if (!nums.contains(randNum)) {
                                    nums.add(randNum);
                                    randomShirts.add(tshirts.get(randNum));
                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Something went wrong in for loop", Toast.LENGTH_SHORT).show();
                        }
                    }
                    initRecyclerView();


                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

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
                args.putString("category", "event");
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
        getFragmentManager().beginTransaction().replace(R.id.frame_id, currentFragment).commit();

    }

    private void initRecyclerView() {
        RecyclerViewAdapter adpt = new RecyclerViewAdapter(randomShirts, getActivity());
        recyclerView.setAdapter(adpt);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        RecyclerViewAdapter onSale = new RecyclerViewAdapter(onSaleTshirts, getActivity());
        onSaleItems.setAdapter(onSale);

        onSaleItems.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false) {

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });


        RecyclerViewAdapter adapter = new RecyclerViewAdapter(ratedItems, getActivity());
        highRatedItems.setAdapter(adapter);
        Log.d(TAG, "initRecyclerView: " + ratedItems.toString());

        highRatedItems.setLayoutManager(new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }
}
