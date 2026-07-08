package com.innovatewithomer.multiuserrestapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProposalRequest {
    @NotBlank(message = "Client name is required")
    private String clientName;

    @NotBlank(message = "Project type is required")
    private String projectType;

    @NotBlank(message = "Project description is required")
    private String projectDescription;

    private Double budget;
    private String deadline;
    private String freelancerName;
}
