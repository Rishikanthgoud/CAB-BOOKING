package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.Vehicle;

public interface VehicleService {

    Vehicle addVehicle(Vehicle vehicle);

    List<Vehicle> getAllVehicles();

    Vehicle getVehicleById(Long id);

    void deleteVehicle(Long id);
}