package com.example.project.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.project.service.UserService;

import jakarta.validation.Valid;

import com.example.project.dto.UserRequest;
import com.example.project.dto.UserResponse;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userservice;

    @Value("${app.title}")
    private String title;

    @GetMapping
    public List<UserResponse> getUsers() {
        List<UserResponse> users = userservice.getAllUsers().stream()
                .map(user -> {
                    UserResponse response = new UserResponse();
                    response.setId(user.getId());
                    response.setUsername(user.getUsername());
                    response.setEmail(user.getEmail());
                    response.setCreatedAt(user.getCreatedAt());
                    return response;
                })
                .toList();
        StringBuilder sb = new StringBuilder();
        for (UserResponse user : users) {
            sb.append("ID: ").append(user.getId()).append(", Name: ").append(user.getUsername()).append("\n");
        }

        return users;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        UserResponse response = userservice.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse response = userservice.createUser (request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}