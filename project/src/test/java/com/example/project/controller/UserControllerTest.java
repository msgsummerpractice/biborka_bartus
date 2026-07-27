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
                new User(2L, "Jane Smith")
        ));
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().string("ID: 1, Name: John Doe\nID: 2, Name: Jane Smith\n"));
    }

    @Test
    public void testGetUsersWithInvalidId() throws Exception {
        mockMvc.perform(get("/users").param("id", "-1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid ID"));
    }
}
