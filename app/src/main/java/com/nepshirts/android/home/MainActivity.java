package com.nepshirts.android.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nepshirts.android.user.LoginActivity;
import com.nepshirts.android.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private ImageView profileIcon;
    private FirebaseAuth.AuthStateListener mauthAuthStateListener;
    private FirebaseUser user;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_nav_id);
        bottomNavigation.setOnNavigationItemSelectedListener(navListner);
        bottomNavigation.setSelectedItemId(R.id.home_id);
        profileIcon = findViewById(R.id.profile_icon_id);
        user = FirebaseAuth.getInstance().getCurrentUser();
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewProfile();
            }
        });

        init();


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.main_toolbar);
        }

    }

    private void viewProfile() {

        if (user == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        } else {
            ProfileFragment profile = new ProfileFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.shimmer_frame_id, profile);
            transaction.commit();
        }


    }


    private void init() {
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.shimmer_frame_id, homeFragment, "home_fragment");
        //back stack
        transaction.commit();

    }

    BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment currentFragment;

                    switch (menuItem.getItemId()) {
                        case R.id.home_id:
                            currentFragment = new HomeFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.shimmer_frame_id, currentFragment, "home_fragment").commit();
                            break;

                        case R.id.search_id:
                            currentFragment = new SearchFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.shimmer_frame_id, currentFragment, "search_fragment").commit();

                            break;

                        case R.id.cart_id:
                            currentFragment = new CartFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.shimmer_frame_id, currentFragment, "cart_fragment").commit();
                            break;
                    }

                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.shimmer_frame_id);
        if (fragment instanceof HomeFragment) {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                backToast.cancel();
                super.onBackPressed();
            } else {
                backToast = Toast.makeText(getBaseContext(), "Press back again to exit.", Toast.LENGTH_SHORT);
                backToast.show();
            }
            backPressedTime = System.currentTimeMillis();

        } else {
            HomeFragment newFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.shimmer_frame_id, newFragment, "home_fragment").commit();
            bottomNavigation.setSelectedItemId(R.id.home_id);
        }
    }


}
