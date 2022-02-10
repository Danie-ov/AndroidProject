package Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.androidproject.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import UI.WorkoutEndurance;

public class NavDrawerMenu extends AppCompatActivity {

    DrawerLayout nav_drawer_layout_menu;

    private CardView menu_CV_running;
    private CardView menu_CV_walking;
    private CardView menu_CV_cycling;

    private MaterialTextView TV_typeNameCycle;
    private MaterialTextView TV_typeNameWalk;
    private MaterialTextView TV_typeNameRun;

    private ShapeableImageView atCycling;
    private ShapeableImageView atRunning;
    private ShapeableImageView atWalking;

    private static String type;

    public static String getWorkoutType() {
        return type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer_menu);

        findViews();
        initButtons();
    }

    private void findViews() {
        nav_drawer_layout_menu = findViewById(R.id.nav_drawer_layout_menu);

        menu_CV_running = findViewById(R.id.menu_CV_running);
        menu_CV_walking = findViewById(R.id.menu_CV_walking);
        menu_CV_cycling = findViewById(R.id.menu_CV_cycling);

        TV_typeNameCycle = findViewById(R.id.TV_typeNameCycle);
        TV_typeNameWalk = findViewById(R.id.TV_typeNameWalk);
        TV_typeNameRun = findViewById(R.id.TV_typeNameRun);

        atCycling = findViewById(R.id.at_cycling);
        atRunning = findViewById(R.id.at_running);
        atWalking = findViewById(R.id.at_walking);

        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/androidfinalproject-e08ce.appspot.com/o/cycling.jpg?alt=media&token=1e4ea9c3-ee04-46d0-a48e-6873d97ff8dc").centerCrop().into(atCycling);
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/androidfinalproject-e08ce.appspot.com/o/run.jpg?alt=media&token=6e8a1065-2208-4c80-8002-96f6bdbe2d04").centerCrop().into(atRunning);
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/androidfinalproject-e08ce.appspot.com/o/walk.jpg?alt=media&token=69646538-9b46-4388-862b-d3f929ff384d").centerCrop().into(atWalking);


    }

    private void initButtons() {
        menu_CV_running.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = TV_typeNameRun.getText().toString();
                moveToWorkout();
            }
        });

        menu_CV_walking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = TV_typeNameWalk.getText().toString();
                moveToWorkout();
            }
        });

        menu_CV_cycling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = TV_typeNameCycle.getText().toString();
                moveToWorkout();
            }
        });
    }

    private void moveToWorkout() {
        Intent intent = new Intent(this, WorkoutEndurance.class);
        startActivity(intent);
        finish();
    }

    public void ClickMenu(View view) {

        openDrawer(nav_drawer_layout_menu);
    }

    public static void openDrawer(DrawerLayout nav_drawer_layout_menu) {

        nav_drawer_layout_menu.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {

        closeDrawer(nav_drawer_layout_menu);
    }

    public static void closeDrawer(DrawerLayout nav_drawer_layout_menu) {

        if (nav_drawer_layout_menu.isDrawerOpen(GravityCompat.START)) {
            nav_drawer_layout_menu.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickWorkouts(View view) {

        recreate();
    }

    public void ClickHistory(View view) {

        redirectActivity(this, HistoryActivity.class);
    }

    public void ClickCharts(View view) {

        redirectActivity(this, ChartsActivity.class);
    }

    public void ClickProfile(View view) {

        redirectActivity(this, ProfileActivity.class);
    }

    public static void redirectActivity(Activity activity, Class aClass) {

        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(nav_drawer_layout_menu);
    }
}