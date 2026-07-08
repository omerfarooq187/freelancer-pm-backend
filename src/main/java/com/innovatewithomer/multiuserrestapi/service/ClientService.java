package com.innovatewithomer.multiuserrestapi.service;

import com.innovatewithomer.multiuserrestapi.dto.ClientRequest;
import com.innovatewithomer.multiuserrestapi.dto.ClientResponse;
import com.innovatewithomer.multiuserrestapi.model.Client;
import com.innovatewithomer.multiuserrestapi.model.User;
import com.innovatewithomer.multiuserrestapi.repository.ClientRepository;
import com.innovatewithomer.multiuserrestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// service/ClientService.java
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public ClientResponse create(ClientRequest request, User user) {
        Client client = Client.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .company(request.getCompany())
                .notes(request.getNotes())
                .user(user)
                .build();

        Client saved = clientRepository.save(client);
        return toResponse(saved);
    }

    public List<ClientResponse> getAllForUser(User user) {
        return clientRepository.findByUserId(user.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ClientResponse getById(Long id, User user) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (!client.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }
        return toResponse(client);
    }

    public ClientResponse update(Long id, ClientRequest request, User user) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (!client.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        client.setName(request.getName());
        client.setEmail(request.getEmail());
        client.setPhone(request.getPhone());
        client.setCompany(request.getCompany());
        client.setNotes(request.getNotes());

        return toResponse(clientRepository.save(client));
    }

    public void delete(Long id, User user) {
        if (!clientRepository.existsByIdAndUserId(id, user.getId())) {
            throw new RuntimeException("Client not found or access denied");
        }
        clientRepository.deleteById(id);
    }

    private ClientResponse toResponse(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .company(client.getCompany())
                .notes(client.getNotes())
                .totalProjects(client.getProjects().size())
                .createdAt(client.getCreatedAt())
                .build();
    }
}
