package com.example.androidproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class EnduranceDataFragment extends Fragment {

    private FirebaseFirestore db;

    private CallBackWorkout callBackWorkout;

    private Location startLocation;

    private MaterialTextView TV_timer;
    private MaterialTextView TV_paceAVG;
    private MaterialTextView TV_distance;

    private MaterialButton BTN_start;

    Timer timer;
    TimerTask timerTask;
    double time = 0.0;
    float distance = 0;
    int lastMin = 0, lastSec = 0, currentMin = 0, currentSec = 0;

    Boolean isStart = false;

    public void setCallbackWorkout(CallBackWorkout callBackWorkout) {
        this.callBackWorkout = callBackWorkout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_endurance_data, container, false);
        findViews(view);
        BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startClicked(view);
            }
        });
        return view;
    }

    private void startClicked(View view) {
        if(isStart == false){
            isStart = true;
            setButtonUI("Stop", Color.RED);

            runTimer();
        }else{
            isStart = false;
            setButtonUI("Start", Color.GREEN);
            stopClicked(view);

            timerTask.cancel();
            TV_distance.setText("0.0");
            TV_paceAVG.setText("0.0");
        }
    }

    private void setButtonUI(String str, int color) {
        BTN_start.setText(str);
        BTN_start.setBackgroundColor(color);
    }

    private void stopClicked(View view){
        AlertDialog.Builder stopAlert = new AlertDialog.Builder(this.getContext());
        stopAlert.setTitle("Quit workout");
        stopAlert.setMessage("Are you sure you want to quit?");
        stopAlert.setPositiveButton("Stop", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(timerTask != null){
                    timerTask.cancel();
                    setButtonUI("Start", Color.green(R.color.green_200));

                    Map<String, Object> workout = new HashMap<>();
                    workout.put("Type", NavDrawerMenu.getWorkoutType());
                    workout.put("Duration", TV_timer.getText().toString());
                    workout.put("Distance", TV_distance.getText().toString());
                    workout.put("Average", TV_paceAVG.getText().toString());
                    workout.put("Date", getDate());
                    workout.put("ID", LoginActivity.getEmail());

                    db.collection("Workout")
                            .add(workout)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(@NonNull DocumentReference documentReference) {
                                    Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

                    time = 0.0;
                    isStart = false;
                    TV_timer.setText(formateTime(0, 0, 0));
                }
            }
        });

        stopAlert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                setButtonUI("Continue", Color.CYAN);
            }
        });

        stopAlert.show();
    }

    private String getDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        String str = formatter.format(date);
        return str;
    }

    private void runTimer() {
        startLocation = callBackWorkout.getMapCurrentLocation();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        TV_timer.setText(getTimerText());
                        if(callBackWorkout != null){
                            if(Integer.parseInt(getTimerText().substring(6)) % 10 == 0){
                                distance = callBackWorkout.getDistancePoints(startLocation);
                                Log.d("distance", "equal to = " + distance);
                                TV_distance.setText(String.format("%.1f",distance));
                            }
                            if(String.valueOf(distance).endsWith("0") && distance >= 1){
                                if(lastMin == 0 && lastSec == 0){
                                    lastMin = Integer.parseInt(getTimerText().substring(3, 5));
                                    lastSec = Integer.parseInt(getTimerText().substring(6));
                                }else{
                                    currentMin = Integer.parseInt(getTimerText().substring(3, 5));
                                    currentSec = Integer.parseInt(getTimerText().substring(6));
                                    int m = currentMin - lastMin;
                                    int s = currentSec = lastSec;
                                    TV_paceAVG.setText(m + ":" + s);
                                    lastMin = currentMin;
                                    lastSec = currentSec;
                                }
                            }
                        }
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private String getTimerText() {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);

        return formateTime(seconds, minutes, hours);
    }

    private String formateTime(int seconds, int minutes, int hours) {
        return String.format("%02d",hours) + ":" + String.format("%02d",minutes) + ":" + String.format("%02d",seconds);
    }

    private void findViews(View view) {
        db = FirebaseFirestore.getInstance();
        TV_timer = view.findViewById(R.id.TV_timer);
        TV_paceAVG = view.findViewById(R.id.TV_paceAVG);
        TV_distance = view.findViewById(R.id.TV_distance);

        BTN_start = view.findViewById(R.id.BTN_start);
        timer  = new Timer();
    }
}