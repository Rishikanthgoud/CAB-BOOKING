package com.example.demo.controller;




import com.example.demo.entity.*;
import com.example.demo.repository.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private RideRepository rideRepository;

    // Get all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get all drivers
    @GetMapping("/drivers")
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    // Get all rides
    @GetMapping("/rides")
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    // Delete ride
    @DeleteMapping("/ride/{id}")
    public String deleteRide(@PathVariable Long id) {
        rideRepository.deleteById(id);
        return "Ride deleted";
    }

    // Delete driver
    @DeleteMapping("/driver/{id}")
    public String deleteDriver(@PathVariable Long id) {
        driverRepository.deleteById(id);
        return "Driver deleted";
    }
}
