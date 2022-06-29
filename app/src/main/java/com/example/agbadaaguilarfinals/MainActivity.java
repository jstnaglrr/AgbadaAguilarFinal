package com.example.agbadaaguilarfinals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button mLogout;

    private FirebaseAuth mAuth;

    private BottomNavigationView bottomNav;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new HomeFragment()).commit();
        }

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navHome:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new HomeFragment()).commit();
                        break;
                    case R.id.navMap:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new NavigationFragment()).commit();
                        break;
                    case R.id.navSettings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new SettingFragment()).commit();
                        break;
                }
                return true;
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
                startActivity(new Intent(MainActivity.this, LogInActivity.class));
            }
        });
    }

    private void logout(){
        mAuth.signOut();
    }

    private void initComponents(){
        bottomNav = findViewById(R.id.main_bottom_nav);
        frameLayout = findViewById(R.id.main_frameLayout);
        mLogout = findViewById(R.id.btnLogout);
    }
}
