package com.calculator.mads;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.calculator.mads.databinding.ActivityMainBinding;
import com.calculator.mads.fragment.HistoryFragment;
import com.calculator.mads.fragment.HomeFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        activityMainBinding.ivOpenMenu.setOnClickListener(this);
        activityMainBinding.mainNavigationView.setNavigationItemSelectedListener(this);
        addFragment(new HomeFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_history:
                activityMainBinding.mainDrawerLayout.closeDrawer(GravityCompat.START);
                addFragment(new HistoryFragment());
                break;

            case R.id.nav_calculate:
                activityMainBinding.mainDrawerLayout.closeDrawer(GravityCompat.START);
                addFragment(new HomeFragment());
                break;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivOpenMenu) {
            if (activityMainBinding.mainDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                activityMainBinding.mainDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                activityMainBinding.mainDrawerLayout.openDrawer(GravityCompat.START);
            }
        }
    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutFragment, fragment).commit();
    }
}