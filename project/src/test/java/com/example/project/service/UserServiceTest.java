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

import java.util.Optional;

import com.example.project.dto.UserResponse;


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
        when(userService.getAllUsers()).thenReturn(List.of(
                new User(1L, "John Doe", null, null, null, null),
                new User(2L, "Jane Smith", null, null, null, null)
        ));
        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    public void testGetUserById() {
        User mockUser = new User(1L, "John Doe", null, null, null, null);
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(mockUser));
        Optional<UserResponse> user = userService.getUserById(1L);
        assertEquals(mockUser.getId(), user.get().getId());
    }

    @Test
    public void testGetUserByEmail() {
        User mockUser = new User(1L, "John Doe", null, null, "test@example.com", null);
        when(userRepository.findByEmail("test@example.com")).thenReturn(java.util.Optional.of(mockUser));
        Optional<UserResponse> user = userService.getUserByEmail("test@example.com");
        assertEquals(mockUser.getId(), user.get().getId());
    }

    @Test
    public void testDeleteUserById() {
        userService.deleteUserById(1L);
        org.mockito.Mockito.verify(userRepository).deleteById(1L);
    }

    @Test
    public void testSaveUser() {
        UserResponse mockUserResponse = new UserResponse();
        mockUserResponse.setId(1L);
        mockUserResponse.setFirstName("John");
        mockUserResponse.setLastName("Doe");
        mockUserResponse.setUsername("johndoe");
        mockUserResponse.setEmail("test@example.com");
        when(userRepository.save(org.mockito.Mockito.any(User.class))).thenReturn(new User(1L, "John Doe", null, null, "test@example.com", null));
        UserResponse savedUser = userService.saveUser(mockUserResponse);
        assertEquals(mockUserResponse.getId(), savedUser.getId());
    }

    @Test
    public void testGetUserByUsername() {
        User mockUser = new User(1L, "John Doe", null, "testuser", null, null);
        when(userRepository.findByUsername("testuser")).thenReturn(java.util.Optional.of(mockUser));
        Optional<UserResponse> user = userService.getUserByUsername("testuser");
        assertEquals(mockUser.getId(), user.get().getId());

    }

    @Test
    public void testGetUserByIdWithNonExistingId() {
        when(userRepository.findById(999L)).thenReturn(java.util.Optional.empty());
        java.util.Optional<UserResponse> user = userService.getUserById(999L);
        assertEquals(java.util.Optional.empty(), user);

    }

    @Test
    public void testGetUserByEmailWithNonExistingEmail() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(java.util.Optional.empty());
        java.util.Optional<UserResponse> user = userService.getUserByEmail("nonexistent@example.com");
        assertEquals(java.util.Optional.empty(), user);
    }

    @Test
    public void testGetUserByUsernameWithNonExistingUsername() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(java.util.Optional.empty());
        java.util.Optional<UserResponse> user = userService.getUserByUsername("nonexistentuser");
        assertEquals(java.util.Optional.empty(), user);
    }
    
}
