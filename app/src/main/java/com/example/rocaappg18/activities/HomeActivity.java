package com.example.rocaappg18.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.rocaappg18.R;
import com.example.rocaappg18.fragments.ChatsFragment;
import com.example.rocaappg18.fragments.FiltersFragment;
import com.example.rocaappg18.fragments.HomeFragment;
import com.example.rocaappg18.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigation=findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(new HomeFragment());

    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    if (item.getItemId()==R.id.item_Home){
                         openFragment(new HomeFragment());
                    }else if (item.getItemId()==R.id.item_Filtros) {
                          openFragment(new FiltersFragment());
                    }else if (item.getItemId()==R.id.item_Chat) {
                          openFragment(new ChatsFragment());
                    }else if (item.getItemId()==R.id.item_Perfil) {
                          openFragment(new ProfileFragment());
                    }
                    return false;
                }
            };
}