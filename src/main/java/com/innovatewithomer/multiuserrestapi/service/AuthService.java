package com.innovatewithomer.multiuserrestapi.service;

import com.innovatewithomer.multiuserrestapi.dto.AuthResponse;
import com.innovatewithomer.multiuserrestapi.dto.LoginRequest;
import com.innovatewithomer.multiuserrestapi.dto.RegisterRequest;
import com.innovatewithomer.multiuserrestapi.exception.UserAlreadyExistsException;
import com.innovatewithomer.multiuserrestapi.model.Role;
import com.innovatewithomer.multiuserrestapi.model.User;
import com.innovatewithomer.multiuserrestapi.repository.UserRepository;
import com.innovatewithomer.multiuserrestapi.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// service/AuthService.java
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token, user.getEmail(), user.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token, user.getEmail(), user.getRole().name());
    }
}