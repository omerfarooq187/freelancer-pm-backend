package com.innovatewithomer.multiuserrestapi.controller;

import com.innovatewithomer.multiuserrestapi.dto.PaymentRequest;
import com.innovatewithomer.multiuserrestapi.dto.PaymentResponse;
import com.innovatewithomer.multiuserrestapi.model.User;
import com.innovatewithomer.multiuserrestapi.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controller/PaymentController.java
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> create(
            @Valid @RequestBody PaymentRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.create(request, user));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<PaymentResponse>> getByProject(
            @PathVariable Long projectId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(paymentService.getByProject(projectId, user));
    }

    @PatchMapping("/{id}/paid")
    public ResponseEntity<PaymentResponse> markAsPaid(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(paymentService.markAsPaid(id, user));
    }
}
