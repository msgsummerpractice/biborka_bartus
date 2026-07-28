package com.example.project.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
public class UserRepositoryTest {
    private UserRepository userRepository = new UserRepository();

    @Test
    public void testGetAllUsers() {
        var users = userRepository.getAllUsers();
        assertNotNull(users);
        assertEquals(2, users.size());
    }
    
}
