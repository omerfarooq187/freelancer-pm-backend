package com.innovatewithomer.multiuserrestapi.controller;

import com.innovatewithomer.multiuserrestapi.dto.ProposalRequest;
import com.innovatewithomer.multiuserrestapi.dto.ProposalResponse;
import com.innovatewithomer.multiuserrestapi.model.User;
import com.innovatewithomer.multiuserrestapi.service.GeminiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final GeminiService geminiService;

    @PostMapping("/proposal")
    public ResponseEntity<ProposalResponse> generateProposal(
            @Valid @RequestBody ProposalRequest request
    ) {

        String proposal = geminiService.generateProposal(request);
        return ResponseEntity.ok(new ProposalResponse(proposal));
    }

    // Free tier gets one sample so they see the value
    @PostMapping("/proposal/preview")
    public ResponseEntity<ProposalResponse> previewProposal(
            @Valid @RequestBody ProposalRequest request) {

        // Limit prompt for preview
        request.setProjectDescription(
                request.getProjectDescription().length() > 100
                        ? request.getProjectDescription().substring(0, 100)
                        : request.getProjectDescription()
        );

        String proposal = geminiService.generateProposal(request);

        // Return only first paragraph as teaser
        String preview = proposal.split("\n\n")[0] +
                "\n\n[Upgrade to Pro to see the full proposal...]";

        return ResponseEntity.ok(new ProposalResponse(preview));
    }
}