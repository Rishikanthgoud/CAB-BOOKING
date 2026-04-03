package com.example.demo.service;



import com.example.demo.entity.User;

import java.util.List;

public interface UserService {

    // Register new user
    User register(User user);

    // Get user by id
    User getUserById(Long id);

    // Get all users
    List<User> getAllUsers();

    // Delete user
    void deleteUser(Long id);
}
