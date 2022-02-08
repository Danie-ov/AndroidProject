package com.example.androidproject;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public interface CallBackWorkout {
    Location getMapCurrentLocation();
    float getDistancePoints(Location location);
    void showTrace(LatLng start, LatLng end);
}
