package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.*;
import com.example.demo.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideServiceimpl implements RideService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DistanceService distanceService;

    @Autowired
    private GeocodingService geocodingService;

    // ✅ Book Ride
    @Override
    public String bookRide(Long userId, RideRequest request) {

        // 🔹 Step 1: Get User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔹 Step 2: Get Available Driver
        Driver driver = driverRepository.findFirstByAvailableTrue()
                .orElseThrow(() -> new RuntimeException("No drivers available"));

        driver.setAvailable(false);
        driverRepository.save(driver);

        // 🔹 Step 3: Convert location → coordinates
        double[] pickupCoords = geocodingService.getCoordinates(request.getPickupLocation());
        double[] dropCoords = geocodingService.getCoordinates(request.getDropLocation());

        // 🔹 Step 4: Calculate distance
        double distance = distanceService.calculateDistance(
                pickupCoords[0], pickupCoords[1],
                dropCoords[0], dropCoords[1]
        );

        // 🔹 Step 5: Calculate fare
        double pricePerKm;

        if (request.getVehicleType().equalsIgnoreCase("mini")) {
            pricePerKm = 8;
        } else if (request.getVehicleType().equalsIgnoreCase("sedan")) {
            pricePerKm = 10;
        } else if (request.getVehicleType().equalsIgnoreCase("suv")) {
            pricePerKm = 15;
        } else {
            pricePerKm = 10;
        }

        double fare = distance * pricePerKm;

        // 🔹 Step 6: Create Ride
        Ride ride = new Ride();
        ride.setPickupLocation(request.getPickupLocation());
        ride.setDropLocation(request.getDropLocation());
        ride.setDistance(distance);
        ride.setFare(fare);
        ride.setStatus("BOOKED");
        ride.setUser(user);
        ride.setDriver(driver);

        // 🔹 Step 7: Save Ride
        rideRepository.save(ride);

        return "Ride booked successfully!";
    }

    // ✅ NEW: Calculate Fare BEFORE booking 🔥
    @Override
    public FareResponse calculateFare(RideRequest request) {

        double[] pickupCoords = geocodingService.getCoordinates(request.getPickupLocation());
        double[] dropCoords = geocodingService.getCoordinates(request.getDropLocation());

        double distance = distanceService.calculateDistance(
                pickupCoords[0], pickupCoords[1],
                dropCoords[0], dropCoords[1]
        );

        double pricePerKm;

        if (request.getVehicleType().equalsIgnoreCase("mini")) pricePerKm = 8;
        else if (request.getVehicleType().equalsIgnoreCase("sedan")) pricePerKm = 10;
        else if (request.getVehicleType().equalsIgnoreCase("suv")) pricePerKm = 15;
        else pricePerKm = 10;

        double fare = distance * pricePerKm;

        return new FareResponse(distance, fare);
    }

    // ✅ Get Ride By ID
    @Override
    public Ride getRideById(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));
    }

    // ✅ Get All Rides
    @Override
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    // ✅ Get All Rides of a User
    @Override
    public List<Ride> getRidesByUser(Long userId) {
        return rideRepository.findByUserId(userId);
    }

    // ✅ Cancel Ride
    @Override
    public String cancelRide(Long rideId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        ride.setStatus("CANCELLED");

        Driver driver = ride.getDriver();
        driver.setAvailable(true);
        driverRepository.save(driver);

        rideRepository.save(ride);

        return "Ride cancelled successfully!";
    }

    // ✅ Update Ride Status
    @Override
    public String updateRideStatus(Long rideId, String status) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        ride.setStatus(status);
        rideRepository.save(ride);

        return "Ride status updated to " + status;
    }
}