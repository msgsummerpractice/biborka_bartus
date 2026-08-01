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

import com.example.project.dto.UserRequest;
import com.example.project.dto.UserResponse;
import com.example.project.dto.UserPatchRequest;


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

    @Test
    public void testUpdateUser() {
        User user = new User(1L, "John Doe", null, null, "test@example.com", null);
        UserRequest mockUserRequest = new UserRequest();
        mockUserRequest.setFirstName("John");
        mockUserRequest.setLastName("Doe");
        mockUserRequest.setUsername("johndoe");
        mockUserRequest.setEmail("test@example.com");
        mockUserRequest.setPassword("password123");

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(org.mockito.Mockito.any(User.class))).thenReturn(user);

        UserResponse updatedUser = userService.updateUser(1L, mockUserRequest);
        assertEquals(mockUserRequest.getFirstName(), updatedUser.getFirstName());
        assertEquals(mockUserRequest.getLastName(), updatedUser.getLastName());
        assertEquals(mockUserRequest.getUsername(), updatedUser.getUsername());
        assertEquals(mockUserRequest.getEmail(), updatedUser.getEmail());
    }

    @Test
    public void testPatchUser() {
        User user = new User(1L, "John Doe", null, null, "test@example.com", null);

        UserPatchRequest mockUserPatchRequest = new UserPatchRequest();
        mockUserPatchRequest.setFirstName("John");
        mockUserPatchRequest.setLastName("Doe");
        mockUserPatchRequest.setUsername("johndoe");
        mockUserPatchRequest.setEmail("test@example.com");

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(org.mockito.Mockito.any(User.class))).thenReturn(user);

        UserResponse patchedUser = userService.patchUser(1L, mockUserPatchRequest);
        assertEquals(mockUserPatchRequest.getFirstName(), patchedUser.getFirstName());
        assertEquals(mockUserPatchRequest.getLastName(), patchedUser.getLastName());
        assertEquals(mockUserPatchRequest.getUsername(), patchedUser.getUsername());
        assertEquals(mockUserPatchRequest.getEmail(), patchedUser.getEmail());
    }
    
}
