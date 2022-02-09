package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class WorkoutEndurance extends AppCompatActivity {

    DrawerLayout Workout_FR_DrawerLayout;

    MapFragment mapFragment;
    EnduranceDataFragment dataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_endurance);

        Workout_FR_DrawerLayout = findViewById(R.id.nav_drawer_layout_menu);

        mapFragment = new MapFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mapFrame, mapFragment)
                .commit();

        dataFragment = new EnduranceDataFragment();
        dataFragment.setCallbackWorkout(callBackWorkout);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.dataFrame, dataFragment)
                .commit();
    }

    CallBackWorkout callBackWorkout = new CallBackWorkout() {

        @Override
        public Location getMapCurrentLocation() {
            Location location = mapFragment.getCurrentLocation();
            return location;
        }

        @Override
        public float getDistancePoints(Location location) {
            float dis = mapFragment.checkDistance(location);
            return dis;
        }

        @Override
        public void showTrace(ArrayList<LatLng> locations) {
            mapFragment.displayTrack(locations);
        }
    };

    public void ClickMenu(View view){

        NavDrawerMenu.openDrawer(Workout_FR_DrawerLayout);
    }

    public void ClickLogo(View view){

        NavDrawerMenu.closeDrawer(Workout_FR_DrawerLayout);
    }

    public void ClickWorkouts(View view){

        NavDrawerMenu.redirectActivity(this, NavDrawerMenu.class);
    }

    public void ClickHistory(View view){

        NavDrawerMenu.redirectActivity(this, HistoryActivity.class);
    }

    public void ClickCharts(View view){

        NavDrawerMenu.redirectActivity(this, ChartsActivity.class);
    }

    public void ClickProfile(View view){

        NavDrawerMenu.redirectActivity(this, ProfileActivity.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NavDrawerMenu.closeDrawer(Workout_FR_DrawerLayout);
    }
}