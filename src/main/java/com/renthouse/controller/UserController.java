package com.renthouse.controller;

import com.renthouse.dto.UserDTO;
import com.renthouse.model.User;
import com.renthouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMyProfile(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(userService.getById(currentUser.getId()));
    }

    @PutMapping("/me")
    public ResponseEntity<UserDTO> updateMyProfile(
            @RequestBody Map<String, Object> body,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(userService.updateProfile(currentUser.getId(), body));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }
}
