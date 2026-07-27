package com.example.project.repository;

import java.util.ArrayList;
import java.util.List;
import com.example.project.model.User;

public class UserRepository {

    List<User> users;

    public UserRepository() {
        users = new ArrayList<>();
        users.add(new User(1L, "John Doe"));
        users.add(new User(2L, "Jane Smith"));
    }

    public List<User> getAllUsers() {
        return users;
    }
}
