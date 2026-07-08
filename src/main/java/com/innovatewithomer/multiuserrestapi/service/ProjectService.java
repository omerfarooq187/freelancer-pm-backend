package com.innovatewithomer.multiuserrestapi.service;

import com.innovatewithomer.multiuserrestapi.dto.ProjectRequest;
import com.innovatewithomer.multiuserrestapi.dto.ProjectResponse;
import com.innovatewithomer.multiuserrestapi.model.Client;
import com.innovatewithomer.multiuserrestapi.model.Project;
import com.innovatewithomer.multiuserrestapi.model.ProjectStatus;
import com.innovatewithomer.multiuserrestapi.model.User;
import com.innovatewithomer.multiuserrestapi.repository.ClientRepository;
import com.innovatewithomer.multiuserrestapi.repository.PaymentRepository;
import com.innovatewithomer.multiuserrestapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// service/ProjectService.java
@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ClientRepository clientRepository;
    private final PaymentRepository paymentRepository;

    public ProjectResponse create(ProjectRequest request, User user) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (!client.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        Project project = Project.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .budget(request.getBudget())
                .deadline(request.getDeadline())
                .client(client)
                .build();

        return toResponse(projectRepository.save(project));
    }

    public List<ProjectResponse> getAllForUser(User user) {
        return projectRepository.findByClientUserId(user.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProjectResponse updateStatus(Long id, ProjectStatus status, User user) {
        if (!projectRepository.existsByIdAndClientUserId(id, user.getId())) {
            throw new RuntimeException("Project not found or access denied");
        }

        Project project = projectRepository.findById(id).get();
        project.setStatus(status);
        return toResponse(projectRepository.save(project));
    }

    public void delete(Long id, User user) {
        if (!projectRepository.existsByIdAndClientUserId(id, user.getId())) {
            throw new RuntimeException("Project not found or access denied");
        }
        projectRepository.deleteById(id);
    }

    private ProjectResponse toResponse(Project project) {
        Double paid = paymentRepository.sumPaidByProjectId(project.getId());
        Double pending = paymentRepository.sumPendingByProjectId(project.getId());

        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .status(project.getStatus())
                .budget(project.getBudget())
                .deadline(project.getDeadline())
                .clientName(project.getClient().getName())
                .totalPaid(paid != null ? paid : 0.0)
                .totalPending(pending != null ? pending : 0.0)
                .createdAt(project.getCreatedAt())
                .build();
    }
}