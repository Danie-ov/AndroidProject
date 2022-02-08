package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class WorkoutResult extends AppCompatActivity {

    DrawerLayout Workout_FR_DrawerLayout;

    MapFragment mapHistoryFragment;
    EnduranceDataHistoryFragment dataHistoryFragment;

    private FirebaseFirestore db;

    Workout workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_result);

        Workout_FR_DrawerLayout = findViewById(R.id.nav_drawer_layout_menu);
        db = FirebaseFirestore.getInstance();

        String dateTemp = getIntent().getStringExtra("message_key1");
        String typeTemp = getIntent().getStringExtra("message_key2");

        db.collection("Workout")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                String id = LoginActivity.getEmail();
                                if(dateTemp.equals(document.get("date").toString()) && typeTemp.equals(document.get("type").toString())){
                                    String type = (document.get("type").toString());
                                    String date = (document.get("date").toString());
                                    String duration = (document.get("duration").toString());
                                    double distance = Double.parseDouble(document.get("distance").toString());
                                    double average = Double.parseDouble(document.get("average").toString());
                                    workout = new Workout(type, duration, distance, average, date, id, workout.getLocations());
                                }
                            }
                        }
                    }
                });

        mapHistoryFragment = new MapFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mapHistoryFrame, mapHistoryFragment)
                .commit();

        dataHistoryFragment = new EnduranceDataHistoryFragment();
        Bundle b = new Bundle();
        b.putString("myDate", dateTemp);
        b.putString("myType", typeTemp);
        dataHistoryFragment.setArguments(b);
        dataHistoryFragment.setCallbackWorkout(callBackWorkout);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.dataHistoryFrame, dataHistoryFragment)
                .commit();
    }

    CallBackWorkout callBackWorkout = new CallBackWorkout(){

        @Override
        public Location getMapCurrentLocation() {
            Location location = mapHistoryFragment.getCurrentLocation();
            return location;
        }

        @Override
        public float getDistancePoints(Location location) {
            float dis = mapHistoryFragment.checkDistance(location);
            return dis;
        }

        @Override
        public void showTrace(LatLng start, LatLng end) {
            mapHistoryFragment.displayTrack(start, end);
        }
    };

    public void ClickMenu(View view){

        NavDrawerMenu.openDrawer(Workout_FR_DrawerLayout);
    }

    public void ClickLogo(View view){

        NavDrawerMenu.closeDrawer(Workout_FR_DrawerLayout);
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

        NavDrawerMenu.redirectActivity(this, ProfileActivity.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NavDrawerMenu.closeDrawer(Workout_FR_DrawerLayout);
    }
}