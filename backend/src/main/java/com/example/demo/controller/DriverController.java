package com.example.demo.controller;



import com.example.demo.entity.*;
import com.example.demo.repository.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private RideRepository rideRepository;

    // Add new driver
    @PostMapping("/add")
    public Driver addDriver(@RequestBody Driver driver) {
        return driverRepository.save(driver);
    }

    // Get all drivers
    @GetMapping
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    // Update driver availability
    @PutMapping("/{id}/availability")
    public String updateAvailability(@PathVariable Long id,
                                     @RequestParam boolean available) {

        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setAvailable(available);
        driverRepository.save(driver);

        return "Availability updated";
    }

    // Accept ride
    @PutMapping("/accept/{rideId}")
    public String acceptRide(@PathVariable Long rideId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        ride.setStatus("ACCEPTED");
        rideRepository.save(ride);

        return "Ride accepted";
    }

    // Complete ride
    @PutMapping("/complete/{rideId}")
    public String completeRide(@PathVariable Long rideId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        ride.setStatus("COMPLETED");

        Driver driver = ride.getDriver();
        driver.setAvailable(true);

        rideRepository.save(ride);
        driverRepository.save(driver);

        return "Ride completed";
    }
}
