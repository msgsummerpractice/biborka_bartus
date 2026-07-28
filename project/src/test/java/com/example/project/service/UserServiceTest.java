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
        userService = new UserServiceImpl(userRepository);
        List<User> mockUsers = List.of(new User(), new User());
        when(userRepository.getAllUsers()).thenReturn(mockUsers);

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
