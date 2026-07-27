package com.example.project.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.project.service.UserService;
import com.example.project.model.User;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userservice;

    public UserController(UserService userservice) {
        this.userservice = userservice;
    }

    @Value("${app.title}")
    private String title;

    @GetMapping
    public ResponseEntity<String> getUsers(@RequestParam(required = false) Long id) {
        if(id != null && id < 0) {
            return ResponseEntity.badRequest().body("Invalid ID");
        }

        List<User> users = userservice.getAllUsers();
        StringBuilder sb = new StringBuilder();
        for (User user : users) {
            sb.append("ID: ").append(user.getId()).append(", Name: ").append(user.getName()).append("\n");
        }

        return ResponseEntity.ok(sb.toString());
    }
}
