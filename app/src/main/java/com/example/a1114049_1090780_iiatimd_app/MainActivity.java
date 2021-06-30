package com.example.a1114049_1090780_iiatimd_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity  {

    // Variable for the bottom navigation view
    private BottomNavigationView bottomNavigationView;
    public String loginToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the bottomnavigationview
        // Setting the correct listener for it
        // and open the homefragment
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(HomeFragment.newInstance("","" ));

    }

    // Method for opening fragment in the correct container
    public void openFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // Listener for the bottomnavigationview
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
        new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.bottom_menu_page_home:
                        openFragment(HomeFragment.newInstance("",""));
                        return true;
                    case R.id.bottom_menu_page_list:
                        openFragment(ListFragment.newInstance("",""));
                        return true;
                    case R.id.bottom_menu_page_search:
                        openFragment(SearchFragment.newInstance("",""));
                        return true;
                    case R.id.bottom_menu_page_account:
                        openFragment(AccountFragment.newInstance("",""));
                        return true;
                }
                return false;
            }
        };
}