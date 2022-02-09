package com.example.androidproject;

public class myLocation {

    private double lat = 0.0;
    private double lon = 0.0;
    private String date;

    public myLocation(){
    }

    public myLocation(double lat, double lon, String date){
        this.lat = lat;
        this.lon = lon;
        this.date = date;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
