package UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

import Activities.ChartsActivity;
import Activities.HistoryActivity;
import Activities.LoginActivity;
import Activities.NavDrawerMenu;
import Activities.ProfileActivity;
import com.example.androidproject.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import CallBacks.CallBackMapHistory;
import Fragments.EnduranceDataHistoryFragment;
import Fragments.MapHistoryFragment;
import Models.Workout;
import Models.myLocation;

public class WorkoutResult extends AppCompatActivity {

    DrawerLayout Workout_FR_DrawerLayout;

    MapHistoryFragment mapHistoryFragment;
    EnduranceDataHistoryFragment dataHistoryFragment;

    private FirebaseFirestore db;

    ArrayList<myLocation> locations = new ArrayList<>();
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
                                    locations = (ArrayList<myLocation>)document.get("locations");
                                    workout = new Workout(type, duration, distance, average, date, id, locations);
                                }
                            }
                        }
                    }
                });

        mapHistoryFragment = new MapHistoryFragment();
        mapHistoryFragment.setCallBackMapHistory(callBackMapHistory);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mapHistoryFrame, mapHistoryFragment)
                .commit();

        dataHistoryFragment = new EnduranceDataHistoryFragment();
        Bundle b = new Bundle();
        b.putString("myDate", dateTemp);
        b.putString("myType", typeTemp);
        dataHistoryFragment.setArguments(b);
        //dataHistoryFragment.setCallbackWorkout(callBackWorkout);
        dataHistoryFragment.setCallBackMapHistory(callBackMapHistory);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.dataHistoryFrame, dataHistoryFragment)
                .commit();
    }

    CallBackMapHistory callBackMapHistory = new CallBackMapHistory(){

        @Override
        public void showTrace(ArrayList<LatLng> locations) {
            mapHistoryFragment.displayTrack(locations);
        }
    };

    /*CallBackWorkout callBackWorkout = new CallBackWorkout() {
        @Override
        public Location getMapCurrentLocation() {
            return null;
        }

        @Override
        public float getDistancePoints(Location location) {
            return 0;
        }
    };*/

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