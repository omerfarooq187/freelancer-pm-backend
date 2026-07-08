package com.innovatewithomer.multiuserrestapi.service;

import com.innovatewithomer.multiuserrestapi.dto.PaymentRequest;
import com.innovatewithomer.multiuserrestapi.dto.PaymentResponse;
import com.innovatewithomer.multiuserrestapi.model.Payment;
import com.innovatewithomer.multiuserrestapi.model.PaymentStatus;
import com.innovatewithomer.multiuserrestapi.model.Project;
import com.innovatewithomer.multiuserrestapi.model.User;
import com.innovatewithomer.multiuserrestapi.repository.PaymentRepository;
import com.innovatewithomer.multiuserrestapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// service/PaymentService.java
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ProjectRepository projectRepository;

    public PaymentResponse create(PaymentRequest request, User user) {
        if (!projectRepository.existsByIdAndClientUserId(request.getProjectId(), user.getId())) {
            throw new RuntimeException("Project not found or access denied");
        }

        Project project = projectRepository.findById(request.getProjectId()).get();

        Payment payment = Payment.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .date(request.getDate())
                .project(project)
                .build();

        return toResponse(paymentRepository.save(payment));
    }

    public PaymentResponse markAsPaid(Long id, User user) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (!payment.getProject().getClient().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        payment.setStatus(PaymentStatus.PAID);
        return toResponse(paymentRepository.save(payment));
    }

    public List<PaymentResponse> getByProject(Long projectId, User user) {
        if (!projectRepository.existsByIdAndClientUserId(projectId, user.getId())) {
            throw new RuntimeException("Access denied");
        }
        return paymentRepository.findByProjectId(projectId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .description(payment.getDescription())
                .date(payment.getDate())
                .status(payment.getStatus())
                .projectTitle(payment.getProject().getTitle())
                .build();
    }
}
