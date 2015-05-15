package com.example.jiayi.myapplication;

/**
 * Created by jiayi on 5/13/15.
 */
public class StopItem {
    double latitude;
    double longitude;
    int id;

    public StopItem(double latitude, double longitude, int id, String stopname, String routes) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.stopname = stopname;
        this.routes = routes;
    }

    String stopname;
    String routes;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStopname() {
        return stopname;
    }

    public void setStopname(String stopname) {
        this.stopname = stopname;
    }

    public String getRoutes() {
        return routes;
    }

    public void setRoutes(String routes) {
        this.routes = routes;
    }





}