package com.example.project.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.project.dto.MfaChallengeResponse;
import com.example.project.dto.SignInRequest;
import com.example.project.dto.SignInResponse;
import com.example.project.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<MfaChallengeResponse> login(@RequestBody @Valid SignInRequest loginRequest) {
        MfaChallengeResponse mfaChallengeResponse = authService.login(loginRequest);
        return ResponseEntity.ok(mfaChallengeResponse);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<SignInResponse> verifyOtp(@RequestBody @Valid com.example.project.dto.OtpVerifyRequest otpVerifyRequest) {
        SignInResponse signInResponse = authService.verifyOtp(otpVerifyRequest);
        return ResponseEntity.ok(signInResponse);
    }
}
