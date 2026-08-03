package com.example.project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class OtpVerifyRequest {
    @NotBlank
    private String mfaToken;

    @NotBlank
    private String code;

}
