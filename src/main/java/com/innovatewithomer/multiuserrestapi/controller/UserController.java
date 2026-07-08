package com.innovatewithomer.multiuserrestapi.controller;

import com.innovatewithomer.multiuserrestapi.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// controller/UserController.java
@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok("Welcome " + user.getFullName() + " | Role: " + user.getRole());
    }

    @GetMapping("/admin/dashboard")
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok("Admin dashboard — restricted access");
    }
}

