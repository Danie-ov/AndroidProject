package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    DrawerLayout historyDrawerLayout;

    private FirebaseFirestore db;

    private RecyclerView list_RV_workouts;

    private WorkoutAdapter adapter;
    private ArrayList<Workout> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        findViews();

        list_RV_workouts.setHasFixedSize(true);
        list_RV_workouts.setLayoutManager(new LinearLayoutManager(this));
        list_RV_workouts.setAdapter(adapter);

        addWorkoutsToList();
    }

    private void addWorkoutsToList() {
        db.collection("Workout")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Workout workout = new Workout();
                                workout.setType(document.get("Type").toString());
                                workout.setDate(document.get("Date").toString());
                                workout.setDate(document.get("Duration").toString());
                                workout.setDate(document.get("Distance").toString());
                                workout.setDate(document.get("Average").toString());
                                list.add(workout);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void findViews() {
        historyDrawerLayout = findViewById(R.id.nav_drawer_layout_menu);

        list_RV_workouts = findViewById(R.id.list_RV_workouts);

        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        adapter = new WorkoutAdapter(this, list);
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