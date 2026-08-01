package com.example.project.controller;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.service.UserService;

import jakarta.validation.Valid;

import com.example.project.dto.UserResponse;
import com.example.project.dto.UserPatchRequest;
import com.example.project.dto.UserRequest;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "", produces = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE
    })
    public List<ResponseEntity<UserResponse>> getUsers() {
        List<UserResponse> users = userService.getAllUsers().stream()
                .map(user -> new UserResponse(user.getId(), user.getLastName(), user.getFirstName(), user.getUsername(), user.getEmail()))
                .toList();
        return users.stream()
                .map(ResponseEntity::ok)
                .toList();
    }

    @GetMapping(value = "/{id}", produces = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        if(id != null && id < 0) {
            throw new IllegalArgumentException("ID must be a non-negative value.");
        }
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/email/{email}", produces = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/username/{username}", produces = {
        MediaType.APPLICATION_JSON_VALUE,
        MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        if(id != null && id < 0) {
            throw new IllegalArgumentException("ID must be a non-negative value.");
        }
        userService.deleteUserById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<UserResponse> saveUser(@Valid @RequestBody UserRequest request) {
        UserResponse userResponse = new UserResponse();
        userResponse.setLastName(request.getLastName());
        userResponse.setFirstName(request.getFirstName());
        userResponse.setUsername(request.getUsername());
        userResponse.setEmail(request.getEmail());
        UserResponse savedUser = userService.saveUser(userResponse);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        if(id != null && id < 0) {
            throw new IllegalArgumentException("ID must be a non-negative value.");
        }
        UserResponse updatedUser = userService.updateUser(id, userRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<UserResponse> patchUser(@PathVariable Long id, @RequestBody UserPatchRequest userRequest) {
        if(id != null && id < 0) {
            throw new IllegalArgumentException("ID must be a non-negative value.");
        }
        UserResponse updatedUser = userService.patchUser(id, userRequest);
        return ResponseEntity.ok(updatedUser);
    }

   
}
