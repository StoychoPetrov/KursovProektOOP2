package com.example.lubomir.kursovproektoop2.models;

public class Flight {

    String fromAirport;
    String toAirport;
    String airplane;
    String date;
    String time;
    int flightTime;
    String flightTimeString;

    public Flight(String fromAirport, String toAirport, String airplane, String date, String time,int flightTime) {
        this.fromAirport = fromAirport;
        this.toAirport = toAirport;
        this.airplane = airplane;
        this.date = date;
        this.time = time;
        this.flightTime = flightTime;
    }

    public Flight(String fromAirport, String toAirport, String airplane, String date, String time,String flightTime) {
        this.fromAirport = fromAirport;
        this.toAirport = toAirport;
        this.airplane = airplane;
        this.date = date;
        this.time = time;
        this.flightTimeString = flightTime;
    }

    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport;
    }

    public String getToAirport() {
        return toAirport;
    }

    public void setToAirport(String toAirport) {
        this.toAirport = toAirport;
    }

    public String getAirplane() {
        return airplane;
    }

    public void setAirplane(String airplane) {
        this.airplane = airplane;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String hour) {
        this.time = hour;
    }

    public int getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(int flightTime) {
        this.flightTime = flightTime;
    }

    public String getFlightTimeString() {
        return flightTimeString;
    }
}
