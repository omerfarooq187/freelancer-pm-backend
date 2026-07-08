package com.innovatewithomer.multiuserrestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ClientResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String company;
    private String notes;
    private int totalProjects;
    private LocalDateTime createdAt;
}
