package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NavDrawerMenu extends AppCompatActivity {

    DrawerLayout nav_drawer_layout_menu;

    CardView menu_CV_running;
    CardView menu_CV_walking;
    CardView menu_CV_cycling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer_menu);

        findViews();
        initButtons();
    }

    private void findViews() {
        nav_drawer_layout_menu = findViewById(R.id.nav_drawer_layout_menu);

        menu_CV_running = findViewById(R.id.menu_CV_running);
        menu_CV_walking = findViewById(R.id.menu_CV_walking);
        menu_CV_cycling = findViewById(R.id.menu_CV_cycling);
    }

    private void initButtons() {
        menu_CV_running.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToWorkout();
            }
        });

        menu_CV_walking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToWorkout();
            }
        });

        menu_CV_cycling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToWorkout();
            }
        });
    }

    private void moveToWorkout() {
        Intent intent= new Intent(this,WorkoutEndurance.class);
        startActivity(intent);
        finish();
    }

    public void ClickMenu(View view){

        openDrawer(nav_drawer_layout_menu);
    }

    public static void openDrawer(DrawerLayout nav_drawer_layout_menu) {

        nav_drawer_layout_menu.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){

        closeDrawer(nav_drawer_layout_menu);
    }

    public static void closeDrawer(DrawerLayout nav_drawer_layout_menu) {

        if(nav_drawer_layout_menu.isDrawerOpen(GravityCompat.START)){
            nav_drawer_layout_menu.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickWorkouts(View view){

        recreate();
    }

    public void ClickHistory(View view){

        redirectActivity(this, HistoryActivity.class);
    }

    public void ClickCharts(View view){

        redirectActivity(this, ChartsActivity.class);
    }

    public void ClickProfile(View view){

        redirectActivity(this, ProfileActivity.class);
    }

    public static void redirectActivity(Activity activity, Class aClass) {

        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(nav_drawer_layout_menu);
    }
}