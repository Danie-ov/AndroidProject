package com.example.androidproject;

public class Workout {
    private String type = " ";
    private double duration = 0.0;
    private double distance = 0.0;
    private double average  = 0.0;
    private String date       = " ";

    public Workout(){
    }

    public Workout(String type, double duration, double distance, double average, String date){
        this.type = type;
        this.duration = duration;
        this.distance = distance;
        this.average = average;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
