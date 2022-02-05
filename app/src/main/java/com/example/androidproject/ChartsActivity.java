package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;

public class ChartsActivity extends AppCompatActivity {

    DrawerLayout chartsDrawerLayout;

    private BarChart barChart;
    FirebaseFirestore db;
    int day, month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        chartsDrawerLayout = findViewById(R.id.nav_drawer_layout_menu);
        barChart = findViewById(R.id.barChart);
        db = FirebaseFirestore.getInstance();

        ArrayList<BarEntry> workouts = new ArrayList<>();
        LocalDate today = LocalDate.now();
        checkDate(today);

        db.collection("Workout")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                int currDay = 0, currMonth = 0;
                                splitDateFromDB(currDay, currMonth,document.get("Date").toString());
                                /*if(currDay == day && currMonth == month){
                                    workouts.add(new BarEntry(day+"/"+month, document.get("Distance").toString()));
                                }*/
                            }

                        }else{
                            Toast.makeText(ChartsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private void splitDateFromDB(int day, int month, String date) {
        day = Integer.parseInt(date.substring(0, 2));
        month = Integer.parseInt(date.substring(3, 5));
    }

    private void checkDate(LocalDate today) {
        day = today.getDayOfMonth();
        month = today.getMonthValue();
        Log.d("p/day", "day = " + day);
        Log.d("p/month", "month = " + month);
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