package com.innovatewithomer.multiuserrestapi.dto;

import com.innovatewithomer.multiuserrestapi.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private Long id;
    private Double amount;
    private String description;
    private LocalDate date;
    private PaymentStatus status;
    private String projectTitle;
}