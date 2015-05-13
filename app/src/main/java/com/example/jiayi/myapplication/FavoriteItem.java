package com.example.jiayi.myapplication;

/**
 * Created by jiayi on 5/13/15.
 */
public class FavoriteItem{
    String depart;
    String destination;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public FavoriteItem(String depart, String destination) {
        this.depart = depart;
        this.destination = destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepart() {
        return depart;
    }
    @Override
    public String toString() {
        return depart+"-->"+destination;
    }

}