package com.innovatewithomer.multiuserrestapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank
    private String password;
}