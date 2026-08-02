package com.example.project.service;

import com.example.project.dto.SignInRequest;
import com.example.project.dto.SignInResponse;

public interface AuthService {

    SignInResponse login(SignInRequest loginRequest);

}
