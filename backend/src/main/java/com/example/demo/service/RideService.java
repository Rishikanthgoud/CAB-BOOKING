package com.example.demo.service;

import com.example.demo.dto.RideRequest;
import com.example.demo.dto.FareResponse;   // ✅ NEW
import com.example.demo.entity.Ride;

import java.util.List;

public interface RideService {

    // ✅ Book Ride
    String bookRide(Long userId, RideRequest request);

    // ✅ NEW: Calculate Fare BEFORE booking
    FareResponse calculateFare(RideRequest request);

    // ✅ Get Ride By ID
    Ride getRideById(Long rideId);

    // ✅ Get All Rides
    List<Ride> getAllRides();

    // ✅ Get All Rides of a User
    List<Ride> getRidesByUser(Long userId);

    // ✅ Cancel Ride
    String cancelRide(Long rideId);

    // ✅ Update Ride Status
    String updateRideStatus(Long rideId, String status);
}