package com.example.project.service;

import com.example.project.dto.SignInResponse;
import com.example.project.repository.UserRepository;
import com.example.project.security.JwtService;
import com.example.project.model.User;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import com.example.project.dto.SignInRequest;

@Service
public class AuthServiceImpl implements AuthService {

    public AuthServiceImpl(JwtService jwtService, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public SignInResponse login(SignInRequest loginRequest) {
        authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet());
        String token = jwtService.generateToken(user.getUsername(), roles.stream().collect(Collectors.toList()));
        return new SignInResponse(token, roles);
    }

}
