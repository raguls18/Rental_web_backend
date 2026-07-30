package com.renthouse.controller;

import com.renthouse.dto.PropertyDTO;
import com.renthouse.model.User;
import com.renthouse.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @GetMapping
    public ResponseEntity<List<PropertyDTO>> getAllProperties(
            @AuthenticationPrincipal User currentUser) {
        Long userId = currentUser != null ? currentUser.getId() : null;
        return ResponseEntity.ok(propertyService.getAllActive(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyDTO> getProperty(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        Long userId = currentUser != null ? currentUser.getId() : null;
        return ResponseEntity.ok(propertyService.getById(id, userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PropertyDTO>> search(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) BigDecimal minRent,
            @RequestParam(required = false) BigDecimal maxRent,
            @RequestParam(required = false) Integer bedrooms,
            @AuthenticationPrincipal User currentUser) {
        Long userId = currentUser != null ? currentUser.getId() : null;
        return ResponseEntity.ok(propertyService.search(city, type, minRent, maxRent, bedrooms, userId));
    }

    @GetMapping("/owner/me")
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<List<PropertyDTO>> getMyProperties(
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(propertyService.getOwnerProperties(currentUser.getId()));
    }

    @PostMapping
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<PropertyDTO> createProperty(
            @RequestBody Map<String, Object> body,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(propertyService.create(body, currentUser.getId()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<PropertyDTO> updateProperty(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(propertyService.update(id, body, currentUser.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProperty(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        String role = currentUser.getUserType().name();
        propertyService.delete(id, currentUser.getId(), role);
        return ResponseEntity.ok(Map.of("message", "Property deleted successfully"));
    }
}
