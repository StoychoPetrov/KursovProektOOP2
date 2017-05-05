package com.example.lubomir.kursovproektoop2.models;

public class Ticket {

    String fromAirport;
    String toAirport;
    String airplane;
    String date;
    String time;
    int maxFlightTime;
    int price;

    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String mFromAirport) {
        this.fromAirport = mFromAirport;
    }

    public String getToAirport() {
        return toAirport;
    }

    public void setToAirport(String mToAirport) {
        this.toAirport = mToAirport;
    }

    public String getAirplane() {
        return airplane;
    }

    public void setAirplane(String mAirplane) {
        this.airplane = mAirplane;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String mDate) {
        this.date = mDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String mTime) {
        this.time = mTime;
    }

    public int getMaxFlightTime() {
        return maxFlightTime;
    }

    public void setMaxFlightTime(int mMaxFlightTime) {
        this.maxFlightTime = mMaxFlightTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int mPrice) {
        this.price = mPrice;
    }
}
