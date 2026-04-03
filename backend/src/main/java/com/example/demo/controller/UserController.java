package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RideRepository rideRepository;

    // ✅ Create New User → /api/users/newuser
    @PostMapping("/newuser")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // ✅ Get All Users → /api/users/allusers
    @GetMapping("/allusers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Get all rides of a user
    @GetMapping("/{id}/rides")
    public List<Ride> getUserRides(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return rideRepository.findAll()
                .stream()
                .filter(ride -> ride.getUser().getId().equals(user.getId()))
                .toList();
    }

    // Delete user
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User deleted successfully";
    }
}