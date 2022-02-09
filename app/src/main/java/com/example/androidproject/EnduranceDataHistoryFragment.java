package com.example.androidproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EnduranceDataHistoryFragment extends Fragment {

    private FirebaseFirestore db;

    private CallBackWorkout callBackWorkout;

    private MaterialTextView resWorkout;
    private MaterialTextView resDate;
    private MaterialTextView resDuration;
    private MaterialTextView resDistance;
    private MaterialTextView resAverage;

    private String myDate, myType;
    ArrayList<LatLng> latLngs = new ArrayList<>();

    Workout workout;

    public void setCallbackWorkout(CallBackWorkout callBackWorkout) {
        this.callBackWorkout = callBackWorkout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_endurance_data_history, container, false);
        findViews(view);
        fillDetails();
        return view;
    }

    private void fillDetails() {
        db.collection("Workout")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                String id = LoginActivity.getEmail();
                                if(id.equals(document.get("id").toString()) && myDate.equals(document.get("date").toString())
                                            && myType.equals(document.get("type").toString())){
                                    workout = document.toObject(Workout.class);
                                    resWorkout.setText(workout.getType());
                                    resDate.setText(workout.getDate());
                                    resDuration.setText(workout.getDuration());
                                    resDistance.setText(String.valueOf(workout.getDistance()));
                                    resAverage.setText(String.valueOf(workout.getAverage()));
                                    for(int i = 0; i < workout.getLocations().size(); i++) {
                                        latLngs.add(new LatLng(workout.getLocations().get(i).getLat(), workout.getLocations().get(i).getLon()));
                                    }
                                    callBackWorkout.showTrace(latLngs);
                                    Toast.makeText(getContext(), "After displayTrack", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
    }

    private void findViews(View view) {
        db = FirebaseFirestore.getInstance();
        resWorkout = view.findViewById(R.id.resWorkout_TXV);
        resDate = view.findViewById(R.id.resDate_TXV);
        resDuration = view.findViewById(R.id.resDuration_TXV);
        resDistance = view.findViewById(R.id.resDistance_TXV);
        resAverage = view.findViewById(R.id.resAverage_TXV);
        myDate = getArguments().getString("myDate");
        myType = getArguments().getString("myType");
    }
}