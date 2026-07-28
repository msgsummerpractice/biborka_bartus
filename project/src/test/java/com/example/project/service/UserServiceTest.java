package com.example.project.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import static org.mockito.MockitoAnnotations.openMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.example.project.model.User;
import com.example.project.repository.UserRepository;

public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }
    
    @Test
    public void testGetAllUsers() {
        when(userRepository.getAllUsers()).thenReturn(List.of(
                new User(1L, "John Doe"),
                new User(2L, "Jane Smith")
        ));
        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    public void testGetUserById() {
        userService = new UserServiceImpl(userRepository);
        User mockUser = new User();
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(mockUser));
        User user = userService.getUserById(1L);
        assertEquals(mockUser, user);
    }

    @Test
    public void testGetUserByEmail() {
        userService = new UserServiceImpl(userRepository);
        User mockUser = new User();
        when(userRepository.findByEmail("test@example.com")).thenReturn(java.util.Optional.of(mockUser));
        User user = userService.getUserByEmail("test@example.com");
        assertEquals(mockUser, user);
    }

    @Test
    public void testDeleteUserById() {
        userService = new UserServiceImpl(userRepository);
        userService.deleteUserById(1L);
        org.mockito.Mockito.verify(userRepository).deleteById(1L);
    }

    @Test
    public void testSaveUser() {
        userService = new UserServiceImpl(userRepository);
        User mockUser = new User();
        when(userRepository.save(mockUser)).thenReturn(mockUser);
        User savedUser = userService.saveUser(mockUser);
        assertEquals(mockUser, savedUser);
    }
}
