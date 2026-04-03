package com.example.demo.dto;

public class FareResponse {

    private double distance;
    private double fare;

    public FareResponse(double distance, double fare) {
        this.distance = distance;
        this.fare = fare;
    }

    public double getDistance() {
        return distance;
    }

    public double getFare() {
        return fare;
    }
}