package com.example.demo.service;
import com.twilio.Twilio;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Otp;
import com.example.demo.repository.OtpRepository;

@Service
public class OtpService {

    private final OtpRepository otpRepository;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    public OtpService(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    public void generateAndSendOtp(String mobile) {

        mobile = mobile.trim().replaceAll("\\D", "");
        if (mobile.length() == 10) mobile = "+91" + mobile;

        // 🔐 Generate secure OTP
        SecureRandom random = new SecureRandom();
        int otpValue = 100000 + random.nextInt(900000);

        // ⏳ Expiry = 5 minutes
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);

        // 💾 Save in DB
        Otp otp = new Otp();
        otp.setMobile(mobile);
        otp.setOtp(String.valueOf(otpValue));
        otp.setExpiryTime(expiry);
        otpRepository.save(otp);

        // 📩 Send SMS
        Message.creator(
                new PhoneNumber(mobile),
                new PhoneNumber(twilioPhoneNumber),
                "Your OTP is " + otpValue
        ).create();
    }

    // ✅ VERIFY METHOD
    public String verifyOtp(String mobile, String enteredOtp) {

        mobile = mobile.trim().replaceAll("\\D", "");
        if (mobile.length() == 10) mobile = "+91" + mobile;

        Otp latestOtp = otpRepository
                .findTopByMobileOrderByIdDesc(mobile)
                .orElse(null);

        if (latestOtp == null) {
            return "No OTP found";
        }

        if (LocalDateTime.now().isAfter(latestOtp.getExpiryTime())) {
            return "OTP Expired";
        }

        if (!latestOtp.getOtp().equals(enteredOtp)) {
            return "Invalid OTP";
        }

        return "Login Successful";
    }
}