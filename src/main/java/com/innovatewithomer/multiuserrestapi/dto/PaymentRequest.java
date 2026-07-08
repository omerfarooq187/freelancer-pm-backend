package com.innovatewithomer.multiuserrestapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentRequest {
    @NotNull(message = "Amount is required")
    private Double amount;
    private String description;
    private LocalDate date;
    private Long projectId;
}
