package com.renthouse.service;

import com.renthouse.dto.UserDTO;
import com.renthouse.model.User;
import com.renthouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return AuthService.mapToUserDTO(user);
    }

    @Transactional
    public UserDTO updateProfile(Long userId, Map<String, Object> body) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (body.containsKey("fullName"))        user.setFullName(body.get("fullName").toString());
        if (body.containsKey("phone"))           user.setPhone(body.get("phone").toString());
        if (body.containsKey("profileImageUrl")) user.setProfileImageUrl(body.get("profileImageUrl").toString());
        if (body.containsKey("password")) {
            String newPass = body.get("password").toString();
            if (!newPass.isBlank()) {
                user.setPassword(passwordEncoder.encode(newPass));
            }
        }

        user = userRepository.save(user);
        return AuthService.mapToUserDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(AuthService::mapToUserDTO)
                .collect(Collectors.toList());
    }
}
