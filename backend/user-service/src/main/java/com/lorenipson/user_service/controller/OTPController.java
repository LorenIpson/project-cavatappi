package com.lorenipson.user_service.controller;

import com.lorenipson.user_service.dto.OTPVerifyRequest;
import com.lorenipson.user_service.service.OTPService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class OTPController {

    private final OTPService otpService;

    public OTPController(OTPService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/api/user/register/otp/info")
    public ResponseEntity<Boolean> getRegistrationOTPInfo(@RequestBody UUID token) {
        boolean result = otpService.checkRegistrationOTPStatus(token);
        return ResponseEntity.ok(result); // TODO: 改成 ApiResponse
    }

    @PostMapping("/api/user/register/otp/verify")
    public ResponseEntity<Boolean> verifyRegistrationOTP(@RequestBody OTPVerifyRequest request) {
        boolean result = otpService.verifyRegistrationOTP(request);
        return ResponseEntity.ok(result); // TODO: 改成 ApiResponse
    }

}
