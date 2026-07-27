package com.example.project.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping
    public String getUsers() {
        List<User> users = userservice.getAllUsers();
        StringBuilder sb = new StringBuilder();
        for (User user : users) {
            sb.append("ID: ").append(user.getId()).append(", Name: ").append(user.getName()).append("\n");
        }
        return sb.toString();
    }
}
