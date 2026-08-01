package com.example.project.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class UserPatchRequest {
    private String lastName;
    private String firstName;
    private String username;
    @Email(message = "Email should be valid")
    private String email;
    private String password;
}
