package com.simanisandroid.simanis;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    public String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getIntent().getStringExtra("id_pasien");

        setContentView(R.layout.activity_detail);
        BottomNavigationView bottomNav2 = findViewById(R.id.navigation2);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container2,new DetailPasienFragment()).commit();

        bottomNav2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_pasien:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container2,new DetailPasienFragment()).commit();
                        break;
                    case R.id.nav_vol:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container2,new InfusFragment()).commit();
                        break;
                    case R.id.nav_grafik:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container2,new GrafikFragment()).commit();
                        break;
                }return true;
            }
        });
    }
}
