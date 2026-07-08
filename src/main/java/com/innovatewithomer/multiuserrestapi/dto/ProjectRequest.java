package com.innovatewithomer.multiuserrestapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectRequest {
    @NotBlank(message = "Project title is required")
    private String title;
    private String description;
    private Double budget;
    private LocalDate deadline;
    private Long clientId;
}
