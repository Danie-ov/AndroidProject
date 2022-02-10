package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.androidproject.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;

import Models.Workout;

public class ChartsActivity extends AppCompatActivity {

    DrawerLayout chartsDrawerLayout;

    private BarChart barChart;
    FirebaseFirestore db;
    Workout workout;
    ArrayList<String> dates = new ArrayList<>();
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        chartsDrawerLayout = findViewById(R.id.nav_drawer_layout_menu);
        barChart = findViewById(R.id.barChart);
        db = FirebaseFirestore.getInstance();
        LocalDate today = LocalDate.now();

        dates.add("Sun");
        dates.add("Mon");
        dates.add("Tue");
        dates.add("Wed");
        dates.add("Thu");
        dates.add("Fri");
        dates.add("Sat");

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
                            for(QueryDocumentSnapshot document : task.getResult()){
                                if(document.get("id").toString().equals(LoginActivity.getEmail()) &&
                                        today.getDayOfMonth() == Integer.parseInt(document.get("date").toString().substring(0,2))){
                                    workout = document.toObject(Workout.class);
                                    entries.add(new BarEntry(i, (float) workout.getDistance()));
                                    i++;
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

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(dates.size());
        xAxis.setLabelRotationAngle(270);
        barChart.animateY(2000);

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