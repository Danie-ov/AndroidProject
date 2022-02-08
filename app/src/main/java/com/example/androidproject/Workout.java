package com.example.androidproject;

import android.location.Location;

import java.util.ArrayList;

public class Workout {
    private String type = " ";
    private String duration = " ";
    private double distance = 0.0;
    private double average  = 0.0;
    private String date  = " ";
    private String id = " ";
    private ArrayList<myLocation> locations;

    public Workout(){
    }
    public Workout(String type, String duration, double distance, double average, String date, String id, ArrayList<myLocation> locations){
        this.type = type;
        this.duration = duration;
        this.distance = distance;
        this.average = average;
        this.date = date;
        this.id = id;
        this.locations = locations;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<myLocation> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<myLocation> locations) {
        this.locations = locations;
    }
}
