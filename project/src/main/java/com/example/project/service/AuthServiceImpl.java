package com.example.project.service;

import com.example.project.dto.*;
import com.example.project.model.User;
import com.example.project.repository.UserRepository;
import com.example.project.security.JwtService;
import com.example.project.security.OtpService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final OtpService otpService;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                            UserRepository userRepository,
                            JwtService jwtService,
                            OtpService otpService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.otpService = otpService;
    }

    public MfaChallengeResponse login(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        otpService.generateOtp(request.getUsername());
        String mfaToken = jwtService.generateMfaToken(request.getUsername());

        return new MfaChallengeResponse(mfaToken, "Enter the 6-digit code (check server console for this exercise)");
    }

    public SignInResponse verifyOtp(OtpVerifyRequest request) {
        if (!jwtService.isMfaPendingToken(request.getMfaToken())) {
            throw new BadCredentialsException("Invalid or expired MFA session");
        }

        String username = jwtService.extractUsername(request.getMfaToken());

        if (!otpService.validateOtp(username, request.getCode())) {
            throw new BadCredentialsException("Invalid or expired OTP code");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        Set<String> roleNames = user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet());

        String finalToken = jwtService.generateToken(user.getUsername(), roleNames.stream().toList());

        return new SignInResponse(finalToken, roleNames);
    }
}