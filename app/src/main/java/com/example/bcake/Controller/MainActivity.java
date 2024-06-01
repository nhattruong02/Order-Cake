package com.example.bcake.Controller;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bcake.Fragment.FragmentFavorite;
import com.example.bcake.Fragment.FragmentHome;
import com.example.bcake.Fragment.FragmentOrder;
import com.example.bcake.Fragment.FragmentProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.bcake.R;
public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        replaceFragment(new FragmentHome());
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.page_home:
                    replaceFragment(new FragmentHome());
                    break;
                case R.id.page_order:
                    replaceFragment(new FragmentOrder());
                    break;
                case R.id.page_favorite:
                    replaceFragment(new FragmentFavorite());
                    break;
                case R.id.page_profile:
                    replaceFragment(new FragmentProfile());
                    break;
            }
            return true;
        });
    }

    private void init() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}