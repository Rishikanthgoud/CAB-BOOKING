package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.Ride;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    @Autowired
    private RideService rideService;

    // ✅ Book Ride
    @PostMapping("/book/{userId}")
    public String bookRide(@PathVariable Long userId,
                           @RequestBody RideRequest request) {
        return rideService.bookRide(userId, request);
    }

    // ✅ Get Ride By ID
    @GetMapping("/{rideId}")
    public Ride getRideById(@PathVariable Long rideId) {
        return rideService.getRideById(rideId);
    }

    // ✅ Get All Rides
    @GetMapping("/all")
    public List<Ride> getAllRides() {
        return rideService.getAllRides();
    }

    // ✅ Get All Rides of a User
    @GetMapping("/user/{userId}")
    public List<Ride> getUserRides(@PathVariable Long userId) {
        return rideService.getRidesByUser(userId);
    }

    // ✅ Cancel Ride
    @PutMapping("/cancel/{rideId}")
    public String cancelRide(@PathVariable Long rideId) {
        return rideService.cancelRide(rideId);
    }

    // ✅ Update Ride Status
    @PutMapping("/status/{rideId}")
    public String updateRideStatus(@PathVariable Long rideId,
                                   @RequestParam String status) {
        return rideService.updateRideStatus(rideId, status);
    }
    @PostMapping("/fare")
    public FareResponse getFare(@RequestBody RideRequest request) {
        return rideService.calculateFare(request);
    }
}