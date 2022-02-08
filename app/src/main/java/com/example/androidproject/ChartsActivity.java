package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
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
        LocalDate today = LocalDate.now();

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);

        ArrayList<BarEntry> entries = new ArrayList<>();

        db.collection("Workout")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            int cur1 = today.getDayOfMonth();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                int cur2 = Integer.parseInt(document.get("date").toString().substring(0,2));
                                if(document.get("ID").toString().equals(LoginActivity.getEmail()) && cur1==cur2){
                                    entries.add(new BarEntry(cur1, Float.valueOf(document.get("distance").toString()).floatValue()));
                                    cur1 = (cur1+1)%30;
                                    Log.d("p/chart", "distance: " + document.get("Distance").toString());
                                }
                            }
                        }else{
                            Toast.makeText(ChartsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        BarDataSet barDataSet = new BarDataSet(entries, "Workouts");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextSize(16f);

        BarData data = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(data);
        barChart.animateY(2000);

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