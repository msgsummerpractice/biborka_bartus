package com.example.project.service;
import org.springframework.stereotype.Service;
import com.example.project.model.User;
import com.example.project.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl() {
        this.userRepository = new UserRepository();
    }

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
