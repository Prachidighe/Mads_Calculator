package com.calculator.mads;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.calculator.mads.databinding.ActivityMainBinding;
import com.calculator.mads.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        activityMainBinding.ivOpenMenu.setOnClickListener(view -> {
            if (activityMainBinding.mainDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                activityMainBinding.mainDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                activityMainBinding.mainDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        addFragment(new HomeFragment());
    }


    private void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutFragment, fragment).commit();
    }
}