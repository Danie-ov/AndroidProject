package com.example.androidproject;

public class Workout {
    private String type = " ";
    private double duration = 0.0;
    private double distance = 0.0;
    private double average  = 0.0;
    private int date       = 0;
    private double startLat = 0.0;
    private double endLat   = 0.0;
    private double startLon = 0.0;
    private double endLon   = 0.0;

    public Workout(){
    }

    public Workout(String type, double duration, double distance, double average, int date, double startLat
            , double endLat, double startLon, double endLon){
        this.type = type;
        this.duration = duration;
        this.distance = distance;
        this.average = average;
        this.date = date;
        this.startLat = startLat;
        this.endLat = endLat;
        this.startLon = startLon;
        this.endLon = endLon;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
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

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public double getStartLat() {
        return startLat;
    }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    public double getEndLat() {
        return endLat;
    }

    public void setEndLat(double endLat) {
        this.endLat = endLat;
    }

    public double getStartLon() {
        return startLon;
    }

    public void setStartLon(double startLon) {
        this.startLon = startLon;
    }

    public double getEndLon() {
        return endLon;
    }

    public void setEndLon(double endLon) {
        this.endLon = endLon;
    }
}
