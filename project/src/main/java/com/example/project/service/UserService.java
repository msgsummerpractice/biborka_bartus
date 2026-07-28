package com.example.project.service;

import com.example.project.model.User;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByEmail(String email);

    void deleteUserById(Long id);

    <S extends User> S saveUser(S entity);

}
