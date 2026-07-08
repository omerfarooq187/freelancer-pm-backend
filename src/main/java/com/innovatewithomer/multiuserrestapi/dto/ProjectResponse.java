package com.innovatewithomer.multiuserrestapi.dto;

import com.innovatewithomer.multiuserrestapi.model.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ProjectResponse {
    private Long id;
    private String title;
    private String description;
    private ProjectStatus status;
    private Double budget;
    private LocalDate deadline;
    private String clientName;
    private Double totalPaid;
    private Double totalPending;
    private LocalDateTime createdAt;
}