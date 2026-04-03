package com.example.demo.service;


import com.example.demo.dto.LoginRequest;

public interface AuthService {

    // Login user and return JWT token
    String login(LoginRequest request);
}
