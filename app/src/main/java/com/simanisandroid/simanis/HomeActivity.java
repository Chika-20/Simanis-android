package com.simanisandroid.simanis;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.firebase.messaging.FirebaseMessaging;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView bottomNav = findViewById(R.id.navigation);
        FirebaseMessaging.getInstance().subscribeToTopic("android");
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new DataPasienFragment()).commit();

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new DataPasienFragment()).commit();
                        break;
                    case R.id.nav_notif:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new NotifikasiFragment()).commit();
                        break;
                    case R.id.nav_info:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new InformationFragment()).commit();
                        break;
                }return true;
            }
        });
    }
}
