package com.renthouse.service;

import com.renthouse.dto.*;
import com.renthouse.model.User;
import com.renthouse.repository.UserRepository;
import com.renthouse.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User.UserType userType;
        try {
            userType = User.UserType.valueOf(request.getUserType().toUpperCase());
        } catch (IllegalArgumentException e) {
            userType = User.UserType.SEEKER;
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .userType(userType)
                .isVerified(false)
                .build();

        user = userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getId(), user.getUserType().name());

        return AuthResponse.builder()
                .token(token)
                .user(mapToUserDTO(user))
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail(), user.getId(), user.getUserType().name());

        return AuthResponse.builder()
                .token(token)
                .user(mapToUserDTO(user))
                .build();
    }

    public static UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .userType(user.getUserType().name())
                .isVerified(user.getIsVerified())
                .profileImageUrl(user.getProfileImageUrl())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
