package com.example.navigationbar;



import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

      DrawerLayout dl;
      Toolbar tl;
      NavigationView nv;
      ActionBarDrawerToggle at;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
           tl = findViewById(R.id.toolbar);
         setSupportActionBar(tl);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         dl = findViewById(R.id.drawer_layout);
         nv = findViewById(R.id.nvView);
        setupDrawerContent(nv);

    }

    private void setupDrawerContent(NavigationView nv) {
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                getitem(item);
                return true;
            }


        });
    }

    private void getitem(MenuItem item) {
        Fragment fragment =null;
        Class fragmentclass;
        switch(item.getItemId()){
            case R.id.itme1:
                fragmentclass = FirstFragment.class;
                break;
            case R.id.item2:
                fragmentclass =SecondFragment.class;
                break;
            case R.id.item3:
                fragmentclass = ThirdFragment.class;
                break;
            default:
                fragmentclass = FirstFragment.class;

        }

        try{
          fragment = (Fragment) fragmentclass.newInstance();
        }catch (IllegalAccessException e ){
            e.printStackTrace();
        }catch (InstantiationException e){
            e.printStackTrace();
        }

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.flCOntent,fragment).commit();
         item.setChecked(true);
        setTitle(item.getTitle());
       dl.close();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                dl.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}






