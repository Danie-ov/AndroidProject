package com.example.androidproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Timer;
import java.util.TimerTask;

public class EnduranceDataFragment extends Fragment {

    private CallBackWorkout callBackWorkout;

    private MaterialTextView TV_timer;
    private MaterialTextView TV_paceAVG;
    private MaterialTextView TV_distance;

    private MaterialButton BTN_start;

    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;

    Boolean isStart = false;

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

    public void setCallbackWorkout(CallBackWorkout callBackWorkout) {
        this.callBackWorkout = callBackWorkout;
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

    private void runTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        TV_timer.setText(getTimerText());
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
        return String.format("%02d",hours) + " : " + String.format("%02d",minutes) + " : " + String.format("%02d",seconds);
    }

    private void findViews(View view) {
        TV_timer = view.findViewById(R.id.TV_timer);
        TV_paceAVG = view.findViewById(R.id.TV_paceAVG);
        TV_distance = view.findViewById(R.id.TV_distance);

        BTN_start = view.findViewById(R.id.BTN_start);
        timer  = new Timer();
    }
}