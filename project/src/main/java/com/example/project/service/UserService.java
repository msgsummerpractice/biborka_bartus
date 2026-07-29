package com.example.project.service;
import com.example.project.dto.UserRequest;
import com.example.project.dto.UserResponse;
import com.example.project.model.User;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    UserResponse getUserById(Long id);

    User getUserByEmail(String email);

    void deleteUserById(Long id);

    <S extends User> S saveUser(S entity);

    UserResponse createUser(UserRequest request);

}
