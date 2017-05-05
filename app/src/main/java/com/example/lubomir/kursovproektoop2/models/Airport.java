package com.example.lubomir.kursovproektoop2.models;

public class Airport {

    double latitude;
    double longitude;
    int runway = 1;
    int berths;
    String name;
    int places;

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

    public int getRunway() {
        return runway;
    }

    public void setRunway(int runway) {
        this.runway = runway;
    }

    public int getBerths() {
        return berths;
    }

    public void setBerths(int berths) {
        this.berths = berths;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }
}
