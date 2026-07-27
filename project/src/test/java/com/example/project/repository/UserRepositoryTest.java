package com.example.project.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
    
public class UserRepositoryTest {
    private UserRepository userRepository;

    @Test
    public void testGetAllUsers() {
        userRepository = new UserRepository();
        var users = userRepository.getAllUsers();
        assertNotNull(users);
    }
}
