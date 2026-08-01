package com.example.project.service;

import org.springframework.stereotype.Service;
import java.util.Optional;

import com.example.project.dto.UserResponse;
import com.example.project.model.User;
import com.example.project.dto.UserPatchRequest;
import com.example.project.dto.UserRequest;
import com.example.project.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserResponse> getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(this::convertToUserResponse);
    }

    @Override
    public Optional<UserResponse> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::convertToUserResponse);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public <S extends UserResponse> S saveUser(S userResponse) {
        User user = new User();
        user.setId(userResponse.getId());
        user.setFirstName(userResponse.getFirstName());
        user.setLastName(userResponse.getLastName());
        user.setUsername(userResponse.getUsername());
        user.setEmail(userResponse.getEmail());
        User savedUser = userRepository.save(user);
        return (S) convertToUserResponse(savedUser); 
    }

    @Override
    public Optional<UserResponse> getUserByUsername(String username) {
       return userRepository.findByUsername(username)
               .map(this::convertToUserResponse);
    }

    @Override
    public void updateUser(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        userRepository.save(user);
    }

    private UserResponse convertToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setLastName(user.getLastName());
        response.setFirstName(user.getFirstName());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        return response;
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setUsername(userRequest.getUsername());
            user.setEmail(userRequest.getEmail());
            User updatedUser = userRepository.save(user);
            return convertToUserResponse(updatedUser);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    @Override
    public UserResponse patchUser(Long id, UserPatchRequest userRequest) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (userRequest.getFirstName() != null) {
                user.setFirstName(userRequest.getFirstName());
            }
            if (userRequest.getLastName() != null) {
                user.setLastName(userRequest.getLastName());
            }
            if (userRequest.getUsername() != null) {
                user.setUsername(userRequest.getUsername());
            }
            if (userRequest.getEmail() != null) {
                user.setEmail(userRequest.getEmail());
            }
            User updatedUser = userRepository.save(user);
            return convertToUserResponse(updatedUser);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

}
