package com.innovatewithomer.multiuserrestapi.service;

import com.innovatewithomer.multiuserrestapi.dto.ProposalRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

// service/GeminiService.java
@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final WebClient webClient;

    public GeminiService() {
        this.webClient = WebClient.builder()
                .codecs(config -> config
                        .defaultCodecs()
                        .maxInMemorySize(2 * 1024 * 1024))
                .build();
    }

    public String generateProposal(ProposalRequest request) {
        String prompt = buildPrompt(request);

        // Build Gemini request body
        Map<String, Object> part = Map.of("text", prompt);
        Map<String, Object> content = Map.of("parts", List.of(part));
        Map<String, Object> body = Map.of("contents", List.of(content));

        // Call Gemini API
        Map response = webClient.post()
                .uri(apiUrl + "?key=" + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return extractText(response);
    }

    private String buildPrompt(ProposalRequest req) {
        return String.format("""
            You are a professional freelancer writing a project proposal.
            Write a concise, professional project proposal with these details:
            
            Freelancer Name: %s
            Client Name: %s
            Project Type: %s
            Project Description: %s
            Budget: %s
            Deadline: %s
            
            Structure the proposal with these sections:
            1. Introduction
            2. Project Understanding
            3. Proposed Approach
            4. Timeline & Deliverables
            5. Investment (budget)
            6. Closing
            
            Keep it professional, confident, and under 400 words.
            """,
                req.getFreelancerName() != null ? req.getFreelancerName() : "Your Freelancer",
                req.getClientName(),
                req.getProjectType(),
                req.getProjectDescription(),
                req.getBudget() != null ? "$" + req.getBudget() : "To be discussed",
                req.getDeadline() != null ? req.getDeadline() : "To be discussed"
        );
    }

    @SuppressWarnings("unchecked")
    private String extractText(Map response) {
        try {
            List<Map> candidates = (List<Map>) response.get("candidates");
            Map candidate = candidates.get(0);
            Map content = (Map) candidate.get("content");
            List<Map> parts = (List<Map>) content.get("parts");
            return (String) parts.get(0).get("text");
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Gemini response");
        }
    }
}
