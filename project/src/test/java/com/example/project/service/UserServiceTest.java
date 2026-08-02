package com.example.project.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.MockitoAnnotations.openMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.project.model.Role;
import com.example.project.model.User;
import com.example.project.repository.RoleRepository;
import com.example.project.repository.UserRepository;

import com.example.project.dto.UserRequest;
import com.example.project.dto.UserResponse;
import com.example.project.dto.UserPatchRequest;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    private User sampleUser(Long id, Role role) {
        User user = new User();
        user.setId(id);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("johndoe");
        user.setEmail("test@example.com");
        user.setPassword("$2a$10$hashedvalue"); // pretend bcrypt hash
        user.setRoles(Set.of(role));
        return user;
    }

    @Test
    public void testGetAllUsers() {
        Role role = new Role(1L, "ROLE_USER");
        when(userRepository.findAll()).thenReturn(List.of(
                sampleUser(1L, role),
                sampleUser(2L, role)
        ));

        List<User> users = userService.getAllUsers();
        assertEquals(2, users.size());
    }

    @Test
    public void testGetUserById() {
        Role role = new Role(1L, "ROLE_USER");
        User mockUser = sampleUser(1L, role);
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        Optional<UserResponse> user = userService.getUserById(1L);

        assertTrue(user.isPresent());
        assertEquals(mockUser.getId(), user.get().getId());
    }

    @Test
    public void testGetUserByIdWithNonExistingId() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<UserResponse> user = userService.getUserById(999L);

        assertTrue(user.isEmpty());
    }

    @Test
    public void testGetUserByEmail() {
        Role role = new Role(1L, "ROLE_USER");
        User mockUser = sampleUser(1L, role);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));

        Optional<UserResponse> user = userService.getUserByEmail("test@example.com");

        assertTrue(user.isPresent());
        assertEquals(mockUser.getId(), user.get().getId());
    }

    @Test
    public void testGetUserByEmailWithNonExistingEmail() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        Optional<UserResponse> user = userService.getUserByEmail("nonexistent@example.com");

        assertTrue(user.isEmpty());
    }

    @Test
    public void testGetUserByUsername() {
        Role role = new Role(1L, "ROLE_USER");
        User mockUser = sampleUser(1L, role);
        when(userRepository.findByUsername("johndoe")).thenReturn(Optional.of(mockUser));

        Optional<UserResponse> user = userService.getUserByUsername("johndoe");

        assertTrue(user.isPresent());
        assertEquals(mockUser.getId(), user.get().getId());
    }

    @Test
    public void testGetUserByUsernameWithNonExistingUsername() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());

        Optional<UserResponse> user = userService.getUserByUsername("nonexistentuser");

        assertTrue(user.isEmpty());
    }

    @Test
    public void testDeleteUserById() {
        userService.deleteUserById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    public void testSaveUser_hashesPasswordBeforeSaving() {
        UserRequest request = new UserRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setUsername("johndoe");
        request.setEmail("test@example.com");
        request.setPassword("plaintext123");
        request.setRoleIds(Set.of(1L));

        Role role = new Role(1L, "ROLE_USER");
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("plaintext123")).thenReturn("$2a$10$hashedvalue");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(1L);
            return u;
        });

        UserResponse response = userService.saveUser(request);

        // Capture what was actually persisted
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User persisted = captor.getValue();

        // The stored password must be the encoded value, never the raw one
        assertEquals("$2a$10$hashedvalue", persisted.getPassword());
        assertNotEquals("plaintext123", persisted.getPassword());

        // The role must be resolved and attached
        assertTrue(persisted.getRoles().contains(role));

        // The response must never expose a password field at all
        assertEquals("johndoe", response.getUsername());
        assertEquals("test@example.com", response.getEmail());
        // (No assertion on response.getPassword() — it shouldn't exist as a method/field.)
    }

    @Test
    public void testSaveUser_throwsWhenRoleIdInvalid() {
        UserRequest request = new UserRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setUsername("johndoe");
        request.setEmail("test@example.com");
        request.setPassword("plaintext123");
        request.setRoleIds(Set.of(999L));

        when(roleRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.saveUser(request));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testUpdateUser_reHashesPasswordWhenProvided() {
        Role existingRole = new Role(1L, "ROLE_USER");
        User existing = sampleUser(1L, existingRole);

        UserRequest request = new UserRequest();
        request.setFirstName("Johnny");
        request.setLastName("Doe");
        request.setUsername("johndoe");
        request.setEmail("test@example.com");
        request.setPassword("newplaintext");
        request.setRoleIds(null); // not changing roles this time

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(passwordEncoder.encode("newplaintext")).thenReturn("$2a$10$newhash");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        UserResponse updated = userService.updateUser(1L, request);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertEquals("$2a$10$newhash", captor.getValue().getPassword());
        assertEquals("Johnny", updated.getFirstName());
    }

    @Test
    public void testUpdateUser_doesNotWipePasswordWhenNotProvided() {
        Role existingRole = new Role(1L, "ROLE_USER");
        User existing = sampleUser(1L, existingRole); // password = "$2a$10$hashedvalue"

        UserRequest request = new UserRequest();
        request.setFirstName("Johnny");
        request.setLastName("Doe");
        request.setUsername("johndoe");
        request.setEmail("test@example.com");
        request.setPassword(null); // no new password given
        request.setRoleIds(null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        userService.updateUser(1L, request);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertEquals("$2a$10$hashedvalue", captor.getValue().getPassword()); // unchanged
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    public void testPatchUser() {
        Role role = new Role(1L, "ROLE_USER");
        User user = sampleUser(1L, role);

        UserPatchRequest patchRequest = new UserPatchRequest();
        patchRequest.setFirstName("Johnny");
        patchRequest.setLastName(null); // not patched
        patchRequest.setUsername(null);
        patchRequest.setEmail(null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse patched = userService.patchUser(1L, patchRequest);

        assertEquals("Johnny", patched.getFirstName());
        assertEquals("Doe", patched.getLastName()); // untouched field preserved
    }
}