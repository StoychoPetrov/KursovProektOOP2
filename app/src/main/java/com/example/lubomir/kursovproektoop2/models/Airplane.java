package com.example.lubomir.kursovproektoop2.models;

public class Airplane {

    int weight;
    int maxFlightTime;
    int timeForService;
    String name;
    String type;
    int maxWeight;
    int maxPlaces;

    public Airplane() {}

    public Airplane(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getMaxFlightTime() {
        return maxFlightTime;
    }

    public void setMaxFlightTime(int maxFlightTime) {
        this.maxFlightTime = maxFlightTime;
    }

    public int getTimeForService() {
        return timeForService;
    }

    public void setTimeForService(int timeForService) {
        this.timeForService = timeForService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public int getMaxPlaces() {
        return maxPlaces;
    }

    public void setMaxPlaces(int maxPlaces) {
        this.maxPlaces = maxPlaces;
    }
}
