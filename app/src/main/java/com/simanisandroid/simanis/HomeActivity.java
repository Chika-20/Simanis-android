package com.simanisandroid.simanis;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class HomeActivity extends AppCompatActivity {
    public LoginPreference loginPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loginPref = new LoginPreference(getApplicationContext());
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
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new RiwayatNotifikasiFragment()).commit();
                        break;
                    case R.id.nav_info:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new InformationFragment()).commit();
                        break;
                }return true;
            }
        });
    }




    private void signOut() {
        //firebase sign out
        FirebaseAuth.getInstance().signOut();
//        loginPref.setLoginStatus(false);
//        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//        finish();
    }

    //method sign out dialog
    private void signoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sign Out")
                .setMessage("Do you want to sign out ?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        signOut();
                        loginPref.setLoginStatus(false);
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("NO", null);
        AlertDialog dialog = builder.create();
        dialog.show();
//        Toast.makeText(this, "Berhasil Logout", Toast.LENGTH_SHORT).show();
    }

    @Override
    //method create option menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                signoutDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
