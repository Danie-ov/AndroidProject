package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class ChartsActivity extends AppCompatActivity {

    DrawerLayout chartsDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        chartsDrawerLayout = findViewById(R.id.nav_drawer_layout_menu);


    }

    public void ClickMenu(View view){

        NavDrawerMenu.openDrawer(chartsDrawerLayout);
    }

    public void ClickLogo(View view){

        NavDrawerMenu.closeDrawer(chartsDrawerLayout);
    }

    public void ClickWorkouts(View view){

        NavDrawerMenu.redirectActivity(this, NavDrawerMenu.class);
    }

    public void ClickHistory(View view){

        NavDrawerMenu.redirectActivity(this, HistoryActivity.class);
    }

    public void ClickCharts(View view){

        recreate();
    }

    public void ClickProfile(View view){

        NavDrawerMenu.redirectActivity(this, ProfileActivity.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NavDrawerMenu.closeDrawer(chartsDrawerLayout);
    }
}