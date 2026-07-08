package com.innovatewithomer.multiuserrestapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientRequest {
    @NotBlank(message = "Client name is required")
    private String name;
    private String email;
    private String phone;
    private String company;
    private String notes;
}