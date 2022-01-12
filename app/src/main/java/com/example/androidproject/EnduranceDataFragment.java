package com.example.androidproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class EnduranceDataFragment extends Fragment {

    private MaterialTextView TV_timer;
    private MaterialTextView TV_paceAVG;
    private MaterialTextView TV_distance;

    private MaterialButton BTN_start;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_endurance_data, container, false);
        findViews(view);
        BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTimer();
            }
        });
        return view;
    }

    private void runTimer() {
    }

    private void findViews(View view) {
        TV_timer = view.findViewById(R.id.TV_timer);
        TV_paceAVG = view.findViewById(R.id.TV_paceAVG);
        TV_distance = view.findViewById(R.id.TV_distance);

        BTN_start = view.findViewById(R.id.BTN_start);
    }
}