package com.innovatewithomer.multiuserrestapi.controller;

import com.innovatewithomer.multiuserrestapi.dto.ProjectRequest;
import com.innovatewithomer.multiuserrestapi.dto.ProjectResponse;
import com.innovatewithomer.multiuserrestapi.model.ProjectStatus;
import com.innovatewithomer.multiuserrestapi.model.User;
import com.innovatewithomer.multiuserrestapi.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> create(
            @Valid @RequestBody ProjectRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectService.create(request, user));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(projectService.getAllForUser(user));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ProjectResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam ProjectStatus status,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(projectService.updateStatus(id, status, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        projectService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}
