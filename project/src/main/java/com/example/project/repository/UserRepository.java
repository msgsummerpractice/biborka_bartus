package com.example.project.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.project.model.User;

@Repository
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

    public User getUserById(Long id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
}
