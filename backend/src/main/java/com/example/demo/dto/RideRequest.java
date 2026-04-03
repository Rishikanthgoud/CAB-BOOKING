package com.example.demo.dto;
import com.example.demo.dto.RideRequest;




public class RideRequest {

    private String pickupLocation;
    private String dropLocation;

    // Coordinates (needed for OpenRouteService)
    private double pickupLat;
    private double pickupLon;

    private double dropLat;
    private double dropLon;

    private Long userId;
    private String vehicleType;

    // Getters & Setters

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public double getPickupLat() {
        return pickupLat;
    }

    public void setPickupLat(double pickupLat) {
        this.pickupLat = pickupLat;
    }

    public double getPickupLon() {
        return pickupLon;
    }

    public void setPickupLon(double pickupLon) {
        this.pickupLon = pickupLon;
    }

    public double getDropLat() {
        return dropLat;
    }

    public void setDropLat(double dropLat) {
        this.dropLat = dropLat;
    }

    public double getDropLon() {
        return dropLon;
    }

    public void setDropLon(double dropLon) {
        this.dropLon = dropLon;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}