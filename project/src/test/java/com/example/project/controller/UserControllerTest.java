package com.example.project.controller;

import com.example.project.dto.UserResponse;
import com.example.project.service.UserService;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    public void testGetUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(java.util.List.of());
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserById() throws Exception {
       UserResponse mockUserResponse = new UserResponse();
        mockUserResponse.setId(1L);
        mockUserResponse.setFirstName("John");
        mockUserResponse.setLastName("Doe");
        mockUserResponse.setUsername("johndoe");
        mockUserResponse.setEmail("john.doe@example.com");
        when(userService.getUserById(1L)).thenReturn(java.util.Optional.of(mockUserResponse));
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        UserResponse mockUserResponse = new UserResponse();
        mockUserResponse.setId(1L);
        mockUserResponse.setFirstName("John");
        mockUserResponse.setLastName("Doe");
        mockUserResponse.setUsername("johndoe");
        mockUserResponse.setEmail("test@example.com");
        when(userService.getUserByEmail("test@example.com")).thenReturn(java.util.Optional.of(mockUserResponse));
        mockMvc.perform(get("/users/email/test@example.com"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserByUsername() throws Exception {
        UserResponse mockUserResponse = new UserResponse();
        mockUserResponse.setId(1L);
        mockUserResponse.setFirstName("John");
        mockUserResponse.setLastName("Doe");
        mockUserResponse.setUsername("testuser");
        mockUserResponse.setEmail("test@example.com");
        when(userService.getUserByUsername("testuser")).thenReturn(java.util.Optional.of(mockUserResponse));
        mockMvc.perform(get("/users/username/testuser"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUserById() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUserByIdWithValidId() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveUser() throws Exception {
        String userJson = "{\"id\":1,\"name\":\"John Doe\",\"email\":\"john.doe@example.com\", \"username\":\"johndoe\",\"password\":\"password123\"}";
        UserResponse mockUserResponse = new UserResponse();
        mockUserResponse.setId(1L);
        mockUserResponse.setFirstName("John");
        mockUserResponse.setLastName("Doe");
        mockUserResponse.setUsername("johndoe");
        mockUserResponse.setEmail("john.doe@example.com");
        when(userService.saveUser(mockUserResponse)).thenReturn(mockUserResponse);
        mockMvc.perform(post("/users/save")
                .contentType("application/json")
                .content(userJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetUserByIdWithNegativeId() throws Exception {
        mockMvc.perform(get("/users/-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUserByIdWithNullId() throws Exception {
        mockMvc.perform(get("/users/null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUserByUsernameWithEmptyUsername() throws Exception {
        mockMvc.perform(get("/users/username/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetUserByEmailWithEmptyEmail() throws Exception {
        mockMvc.perform(get("/users/email/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUserByIdWithNegativeId() throws Exception {
        UserResponse mockUserResponse = new UserResponse();
        mockUserResponse.setId(-1L);
        when(userService.getUserById(-1L)).thenReturn(java.util.Optional.of(mockUserResponse));
        mockMvc.perform(delete("/users/-1"))
                .andExpect(status().isBadRequest());   
    }

    @Test
    public void testDeleteUserByIdWithNullId() throws Exception {
        mockMvc.perform(delete("/users/null"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSaveUserWithInvalidData() throws Exception {
        String userJson = "{\"id\":1,\"name\":\"\",\"email\":\"invalid-email\", \"username\":\"\",\"password\":\"\"}";
        mockMvc.perform(post("/users/save")
                .contentType("application/json")
                .content(userJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSaveUserWithNullData() throws Exception {
        mockMvc.perform(post("/users/save")
                .contentType("application/json")
                .content("{}"))
                .andExpect(status().isBadRequest());
    }
}