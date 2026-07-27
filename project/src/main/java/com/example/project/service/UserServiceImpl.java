package com.example.project.service;
import org.springframework.stereotype.Service;
import com.example.project.model.User;
import com.example.project.repository.AKindOfRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final AKindOfRepository userRepository;

    public UserServiceImpl() {
        this.userRepository = new AKindOfRepository();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
