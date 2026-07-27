package com.example.project.service;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.project.model.User;
import com.example.project.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    
    @Test
    public void testGetAllUsers() {
        List<User> mockUsers = List.of(
            new User(1L, "John Doe"),
            new User(2L, "Jane Smith")
        );
        when(userRepository.getAllUsers()).thenReturn(mockUsers);
        userService = new UserServiceImpl(userRepository);
        assertEquals(mockUsers, userService.getAllUsers());

    }

}
