package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        list_RV_workouts.setItemAnimator(new DefaultItemAnimator());
        list_RV_workouts.setAdapter(adapter);
    }

    private void addWorkoutsToList() {
        db.collection("Workout")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                String id = LoginActivity.getEmail();
                                if(id.equals(document.get("ID").toString())){
                                    String type = (document.get("Type").toString());
                                    String date = (document.get("Date").toString());
                                    String duration = (document.get("Duration").toString());
                                    float distance = Float.valueOf(document.get("Distance").toString()).floatValue();
                                    float average = Float.valueOf(document.get("Average").toString()).floatValue();
                                    Workout workout = new Workout(type, duration, distance, average, date, id);
                                    list.add(workout);
                                    sortByDate();
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void sortByDate() {
        list.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
    }

    private void findViews() {
        historyDrawerLayout = findViewById(R.id.nav_drawer_layout_menu);

        list_RV_workouts = findViewById(R.id.list_RV_workouts);
        db = FirebaseFirestore.getInstance();

        list = new ArrayList<>();
        addWorkoutsToList();
        adapter = new WorkoutAdapter(this, list);

        adapter.setWorkoutItemClickListener(new WorkoutAdapter.WorkoutItemClickListener() {
            @Override
            public void workoutItemClick(Workout workout, int position) {
                Intent intent = new Intent(HistoryActivity.this,WorkoutResult.class);
                intent.putExtra("message_key1", workout.getDate());
                intent.putExtra("message_key2", workout.getType());
                startActivity(intent);
            }
        });
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