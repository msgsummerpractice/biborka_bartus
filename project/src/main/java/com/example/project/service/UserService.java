package com.example.project.service;

import com.example.project.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByUsername(String username);

    void deleteUserById(Long id);

    <S extends User> S saveUser(S entity);

}
