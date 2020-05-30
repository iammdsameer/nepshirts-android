package com.nepshirts.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigation = findViewById(R.id.bottom_nav_id);
        bottomNavigation.setOnNavigationItemSelectedListener(navListner);
        bottomNavigation.setSelectedItemId(R.id.home_id);

        init();

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.main_toolbar);
        }

    }


    private void init(){
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_id, homeFragment, "home_fragment" );
        //back stack
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment currentFragment=null;

                    switch (menuItem.getItemId()){
                        case R.id.home_id:
                            currentFragment= new HomeFragment();
                            break;

                        case R.id.search_id:
                            currentFragment= new SearchFragment();
                            break;

                        case R.id.cart_id:
                            currentFragment= new CartFragment();
                            break;
                    }

                    //for displaying frag
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_id, currentFragment).commit();
                    return true;
                }
            };


}
