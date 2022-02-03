package com.example.androidproject;

import android.location.Location;

public interface CallBackWorkout {
    Location getMapCurrentLocation();
    float getDistancePoints(Location location);
    void showTrace(double lat, double lon);
}
