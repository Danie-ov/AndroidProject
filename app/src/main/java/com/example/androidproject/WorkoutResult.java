package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class WorkoutResult extends AppCompatActivity {

    DrawerLayout Workout_FR_DrawerLayout;

    MapFragment mapHistoryFragment;
    EnduranceDataHistoryFragment dataHistoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_result);

        Workout_FR_DrawerLayout = findViewById(R.id.nav_drawer_layout_menu);

        String date = getIntent().getStringExtra("message_key1");
        String type = getIntent().getStringExtra("message_key2");

        mapHistoryFragment = new MapFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mapHistoryFrame, mapHistoryFragment)
                .commit();

        dataHistoryFragment = new EnduranceDataHistoryFragment();
        Bundle b = new Bundle();
        b.putString("myDate", date);
        b.putString("myType", type);
        dataHistoryFragment.setArguments(b);
        dataHistoryFragment.setCallbackWorkout(callBackWorkout);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.dataHistoryFrame, dataHistoryFragment)
                .commit();

    }

    CallBackWorkout callBackWorkout = new CallBackWorkout(){

        @Override
        public Location getMapCurrentLocation() {
            return null;
        }

        @Override
        public float getDistancePoints(Location location) {
            return 0;
        }

        @Override
        public void showTrace(double lat, double lon) {

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