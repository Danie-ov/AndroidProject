package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    DrawerLayout profileDrawerLayout;

    private MaterialButton Logout_BTN;

    private MaterialTextView first_name_TXV;
    private MaterialTextView last_name_TXV;
    private MaterialTextView email_TXV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViews();
        Logout_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToLoginPage();
            }
        });
    }

    private void moveToLoginPage() {
        Intent intent= new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void findViews() {

        profileDrawerLayout = findViewById(R.id.nav_drawer_layout_menu);

        Logout_BTN = findViewById(R.id.Logout_BTN);
        first_name_TXV = findViewById(R.id.first_name_TXV);
        last_name_TXV = findViewById(R.id.last_name_TXV);
        email_TXV = findViewById(R.id.email_TXV);

        first_name_TXV.setText(LoginActivity.getFirstName());
        last_name_TXV.setText(LoginActivity.getLastName());
        email_TXV.setText(LoginActivity.getEmail());
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