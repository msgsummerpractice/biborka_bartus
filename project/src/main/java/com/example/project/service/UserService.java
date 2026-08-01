package com.example.project.service;

import com.example.project.model.User;
import com.example.project.dto.UserRequest;
import com.example.project.dto.UserResponse;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    Optional<UserResponse> getUserById(Long id);

    Optional<UserResponse> getUserByEmail(String email);

    Optional<UserResponse> getUserByUsername(String username);

    void deleteUserById(Long id);

    <S extends UserResponse> S saveUser(S entity);

    void updateUser(UserRequest user);

}
