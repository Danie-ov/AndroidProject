package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

public class HistoryActivity extends AppCompatActivity {

    DrawerLayout historyDrawerLayout;

    RecyclerView list_RV_records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyDrawerLayout = findViewById(R.id.nav_drawer_layout_menu);

        list_RV_records = findViewById(R.id.list_RV_records);
    }

    public void ClickMenu(View view){

        NavDrawerMenu.openDrawer(historyDrawerLayout);
    }

    public void ClickLogo(View view){

        NavDrawerMenu.closeDrawer(historyDrawerLayout);
    }

    public void ClickWorkouts(View view){

        NavDrawerMenu.redirectActivity(this, NavDrawerMenu.class);
    }

    public void ClickHistory(View view){

        recreate();
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
        NavDrawerMenu.closeDrawer(historyDrawerLayout);
    }
}