package com.example.project.controller;

import com.example.project.service.UserService;
import com.example.project.model.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    public void testGetUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(
                new User(1L, "John Doe"),
                new User(2L, "Jane Smith")));
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"John Doe\"},{\"id\":2,\"name\":\"Jane Smith\"}]"));
    }

    @Test
    public void testGetUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(new User(1L, "John Doe"));
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"John Doe\"}"));
    }

    @Test
    public void testGetUserByIdWithInvalidId() throws Exception {
        assertThrows(Exception.class, () -> {
            mockMvc.perform(get("/users/-1"));
        });
    }
}
