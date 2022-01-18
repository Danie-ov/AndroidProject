package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class ProfileActivity extends AppCompatActivity {

    DrawerLayout profileDrawerLayout;

    private MaterialButton Logout_BTN;

    private MaterialTextView user_name_TXV;
    private MaterialTextView first_name_TXV;
    private MaterialTextView last_name_TXV;
    private MaterialTextView email_TXV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileDrawerLayout = findViewById(R.id.nav_drawer_layout_menu);

        findViews();
    }

    private void findViews() {

        Logout_BTN = findViewById(R.id.Logout_BTN);
        user_name_TXV = findViewById(R.id.user_name_TXV);
        first_name_TXV = findViewById(R.id.first_name_TXV);
        last_name_TXV = findViewById(R.id.last_name_TXV);
        email_TXV = findViewById(R.id.email_TXV);
    }

    public void ClickMenu(View view){

        NavDrawerMenu.openDrawer(profileDrawerLayout);
    }

    public void ClickLogo(View view){

        NavDrawerMenu.closeDrawer(profileDrawerLayout);
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

        recreate();
    }

    @Override
    protected void onPause() {
        super.onPause();
        NavDrawerMenu.closeDrawer(profileDrawerLayout);
    }
}