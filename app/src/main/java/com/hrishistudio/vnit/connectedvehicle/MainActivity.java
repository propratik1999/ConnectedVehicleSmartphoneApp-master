package com.hrishistudio.vnit.connectedvehicle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;

import java.io.FileInputStream;
import java.io.IOError;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private NavigationView mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawer = (DrawerLayout)findViewById(R.id.mDrawer);
        mNavigation = (NavigationView) findViewById(R.id.mNavigation);

        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.drawer_profile:
                        startActivity(new Intent(MainActivity.this, DrivingActivity.class));
                        break;

                    case R.id.drawer_dashboard:
                        //startActivity(new Intent(MainActivity.this, SleepTime.class));
                        break;

                    case R.id.drawer_navigation:
                        //tartActivity(new Intent(MainActivity.this, HospitalReviews.class));
                        break;

                    case R.id.drawer_about:
                        //startActivity(new Intent(MainActivity.this, AboutUs.class));
                        break;

                    case R.id.drawer_fine:
                        //startActivity(new Intent(MainActivity.this, FineLogs.class));
                        break;

                    case R.id.vehicles:
                        startActivity(new Intent(MainActivity.this, VehicleList.class));
                        break;

                    default:
                        break;
                }
                mDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    public void toggleMenu(View view){
        mDrawer.openDrawer(GravityCompat.START);
    }

    public void toggleSwitch(View view){
        ImageButton button = (ImageButton)view;
        switch (button.getContentDescription().toString()){
            case "on":
                button.setImageDrawable(getResources().getDrawable(R.drawable.button_on));
                button.setContentDescription("off");
                break;

            case "off":
                button.setImageDrawable(getResources().getDrawable(R.drawable.button_off));
                button.setContentDescription("on");
                break;
        }
    }
}