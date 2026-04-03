package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.service.OtpService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class OtpController {

    private final OtpService service;

    public OtpController(OtpService service) {
        this.service = service;
    }

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String mobile) {
        service.generateAndSendOtp(mobile);
        return "OTP Sent Successfully";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String mobile,
                            @RequestParam String otp) {
        return service.verifyOtp(mobile, otp);
    }
}