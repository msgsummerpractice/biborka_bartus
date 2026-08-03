package com.example.project.service;

import com.example.project.dto.SignInRequest;
import com.example.project.dto.SignInResponse;
import com.example.project.dto.MfaChallengeResponse;
import com.example.project.dto.OtpVerifyRequest;

public interface AuthService {


    MfaChallengeResponse login(SignInRequest loginRequest);

    SignInResponse verifyOtp(OtpVerifyRequest otpVerifyRequest);

}
