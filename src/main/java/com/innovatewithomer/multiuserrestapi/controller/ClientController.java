package com.innovatewithomer.multiuserrestapi.controller;

import com.innovatewithomer.multiuserrestapi.dto.ClientRequest;
import com.innovatewithomer.multiuserrestapi.dto.ClientResponse;
import com.innovatewithomer.multiuserrestapi.model.User;
import com.innovatewithomer.multiuserrestapi.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponse> create(
            @Valid @RequestBody ClientRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.create(request, user));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(clientService.getAllForUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(clientService.getById(id, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ClientRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(clientService.update(id, request, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        clientService.delete(id, user);
        return ResponseEntity.noContent().build();
    }
}
