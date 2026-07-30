package com.renthouse.controller;

import com.renthouse.dto.PropertyDTO;
import com.renthouse.dto.UserDTO;
import com.renthouse.model.Property;
import com.renthouse.repository.PropertyRepository;
import com.renthouse.repository.PropertyImageRepository;
import com.renthouse.repository.UserRepository;
import com.renthouse.service.PropertyService;
import com.renthouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.renthouse.dto.RegisterRequest;
import com.renthouse.dto.BulkPropertyRequest;
import com.renthouse.dto.BulkCombinedRequest;
import com.renthouse.model.User;
import com.renthouse.model.Property;
import com.renthouse.model.PropertyImage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final PropertyService propertyService;
    private final PropertyRepository propertyRepository;
    private final PropertyImageRepository propertyImageRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/properties")
    public ResponseEntity<List<PropertyDTO>> getAllProperties() {
        List<PropertyDTO> all = propertyRepository.findAll().stream()
                .map(p -> propertyService.mapToDTO(p, null))
                .collect(Collectors.toList());
        return ResponseEntity.ok(all);
    }

    @PutMapping("/users/{id}/status")
    public ResponseEntity<Map<String, String>> toggleUserStatus(@PathVariable Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsVerified(!user.getIsVerified());
        userRepository.save(user);
        return ResponseEntity.ok(Map.of(
            "message", "User status updated",
            "isVerified", user.getIsVerified().toString()
        ));
    }

    @DeleteMapping("/properties/{id}")
    public ResponseEntity<Map<String, String>> forceDeleteProperty(@PathVariable Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        propertyRepository.delete(property);
        return ResponseEntity.ok(Map.of("message", "Property deleted by admin"));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        long totalUsers = userRepository.count();
        long totalProperties = propertyRepository.count();
        long activeProperties = propertyRepository.countByStatus(Property.Status.ACTIVE);

        return ResponseEntity.ok(Map.of(
            "totalUsers", totalUsers,
            "totalProperties", totalProperties,
            "activeProperties", activeProperties
        ));
    }

    @PostMapping("/users/bulk-owners")
    public ResponseEntity<Map<String, Object>> addMultipleOwners(@RequestBody List<RegisterRequest> requests) {
        int addedCount = 0;
        int failedCount = 0;

        for (RegisterRequest request : requests) {
            try {
                if (userRepository.existsByEmail(request.getEmail())) {
                    failedCount++;
                    continue;
                }
                User user = User.builder()
                        .fullName(request.getFullName())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .phone(request.getPhone())
                        .userType(User.UserType.OWNER)
                        .isVerified(true)
                        .profileImageUrl(request.getProfileImageUrl())
                        .build();
                userRepository.save(user);
                addedCount++;
            } catch (Exception e) {
                failedCount++;
            }
        }
        return ResponseEntity.ok(Map.of(
            "message", "Bulk upload completed",
            "addedCount", addedCount,
            "failedCount", failedCount
        ));
    }

    @PostMapping("/properties/bulk")
    public ResponseEntity<Map<String, Object>> addMultipleProperties(@RequestBody List<BulkPropertyRequest> requests) {
        int addedCount = 0;
        int failedCount = 0;

        for (BulkPropertyRequest req : requests) {
            try {
                User owner = userRepository.findByEmail(req.getOwnerEmail()).orElse(null);
                if (owner == null) {
                    failedCount++;
                    continue;
                }

                Property property = Property.builder()
                        .title(req.getTitle())
                        .description(req.getDescription())
                        .address(req.getAddress())
                        .city(req.getCity())
                        .state(req.getState())
                        .pincode(req.getPincode())
                        .rent(req.getRent())
                        .bedrooms(req.getBedrooms() != null ? req.getBedrooms() : 0)
                        .bathrooms(req.getBathrooms() != null ? req.getBathrooms() : 0)
                        .areaSqft(req.getAreaSqft() != null ? req.getAreaSqft() : 0)
                        .propertyType(safeParsePropertyType(req.getPropertyType()))
                        .furnishing(safeParseFurnishing(req.getFurnishing()))
                        .amenities(convertToJsonArray(req.getAmenities()))
                        .status(Property.Status.ACTIVE)
                        .owner(owner)
                        .build();

                property = propertyRepository.save(property);

                if (req.getImageUrls() != null && !req.getImageUrls().isEmpty()) {
                    boolean isFirst = true;
                    for (String url : req.getImageUrls()) {
                        PropertyImage img = PropertyImage.builder()
                                .property(property)
                                .imageUrl(url)
                                .isPrimary(isFirst)
                                .build();
                        propertyImageRepository.save(img);
                        isFirst = false;
                    }
                }
                addedCount++;
            } catch (Exception e) {
                failedCount++;
            }
        }

        return ResponseEntity.ok(Map.of(
            "addedCount", addedCount,
            "failedCount", failedCount
        ));
    }

    @PostMapping("/bulk-combined")
    public ResponseEntity<Map<String, Object>> addCombinedData(@RequestBody List<BulkCombinedRequest> requests) {
        int addedCount = 0;
        int failedCount = 0;
        int newOwnersCount = 0;

        for (BulkCombinedRequest req : requests) {
            try {
                if (req.getOwnerEmail() == null || req.getOwnerEmail().isBlank() || 
                    req.getTitle() == null || req.getTitle().isBlank()) {
                    failedCount++;
                    continue;
                }

                User owner = userRepository.findByEmail(req.getOwnerEmail()).orElse(null);
                
                if (owner == null) {
                    if (req.getOwnerName() == null || req.getOwnerName().isBlank()) {
                        failedCount++;
                        continue;
                    }
                    
                    String rawPassword = req.getOwnerPassword() != null && !req.getOwnerPassword().isBlank() 
                            ? req.getOwnerPassword() : "owner123";
                    
                    owner = User.builder()
                            .fullName(req.getOwnerName())
                            .email(req.getOwnerEmail())
                            .phone(req.getOwnerPhone())
                            .password(passwordEncoder.encode(rawPassword))
                            .userType(User.UserType.OWNER)
                            .isVerified(true)
                            .profileImageUrl(req.getOwnerImageUrl())
                            .build();
                    owner = userRepository.save(owner);
                    newOwnersCount++;
                }

                Property property = Property.builder()
                        .title(req.getTitle())
                        .description(req.getDescription())
                        .address(req.getAddress())
                        .city(req.getCity())
                        .district(req.getDistrict())
                        .state(req.getState())
                        .pincode(req.getPincode())
                        .rent(req.getRent())
                        .bedrooms(req.getBedrooms() != null ? req.getBedrooms() : 0)
                        .bathrooms(req.getBathrooms() != null ? req.getBathrooms() : 0)
                        .areaSqft(req.getAreaSqft() != null ? req.getAreaSqft() : 0)
                        .propertyType(safeParsePropertyType(req.getPropertyType()))
                        .furnishing(safeParseFurnishing(req.getFurnishing()))
                        .amenities(convertToJsonArray(req.getAmenities()))
                        .status(Property.Status.ACTIVE)
                        .owner(owner)
                        .build();

                property = propertyRepository.save(property);

                if (req.getPropertyImageUrls() != null && !req.getPropertyImageUrls().isEmpty()) {
                    boolean isFirst = true;
                    for (String url : req.getPropertyImageUrls()) {
                        PropertyImage img = PropertyImage.builder()
                                .property(property)
                                .imageUrl(url)
                                .isPrimary(isFirst)
                                .build();
                        propertyImageRepository.save(img);
                        isFirst = false;
                    }
                }
                addedCount++;
            } catch (Exception e) {
                failedCount++;
            }
        }

        return ResponseEntity.ok(Map.of(
            "propertiesAdded", addedCount,
            "newOwnersCreated", newOwnersCount,
            "failedCount", failedCount
        ));
    }

    private Property.PropertyType safeParsePropertyType(String typeStr) {
        if (typeStr == null || typeStr.isBlank()) return Property.PropertyType.HOUSE;
        String type = typeStr.toUpperCase().replace(" ", "_");
        try {
            return Property.PropertyType.valueOf(type);
        } catch (IllegalArgumentException e) {
            if (type.contains("HOUSE") || type.contains("INDIVIDUAL")) return Property.PropertyType.HOUSE;
            if (type.contains("VILLA")) return Property.PropertyType.VILLA;
            if (type.contains("APARTMENT") || type.contains("FLAT")) return Property.PropertyType.APARTMENT;
            if (type.contains("PG")) return Property.PropertyType.PG;
            if (type.contains("STUDIO")) return Property.PropertyType.STUDIO;
            return Property.PropertyType.HOUSE;
        }
    }

    private Property.Furnishing safeParseFurnishing(String furnStr) {
        if (furnStr == null || furnStr.isBlank()) return Property.Furnishing.UNFURNISHED;
        String furn = furnStr.toUpperCase().replace(" ", "_");
        try {
            return Property.Furnishing.valueOf(furn);
        } catch (IllegalArgumentException e) {
            if (furn.contains("SEMI")) return Property.Furnishing.SEMI_FURNISHED;
            if (furn.contains("UNFURNISHED")) return Property.Furnishing.UNFURNISHED;
            if (furn.contains("FURNISHED")) return Property.Furnishing.FURNISHED;
            return Property.Furnishing.UNFURNISHED;
        }
    }

    private java.util.List<String> convertToJsonArray(String amenities) {
        if (amenities == null || amenities.isBlank()) return new java.util.ArrayList<>();
        if (amenities.trim().startsWith("[")) {
            // Very simple extraction if they actually sent a JSON string
            String cleaned = amenities.trim().replaceAll("[\\[\\]\\\"']", "");
            return java.util.Arrays.asList(cleaned.split(","));
        }
        
        try {
            String[] items = amenities.split(",");
            java.util.List<String> list = new java.util.ArrayList<>();
            for (String item : items) {
                if (!item.trim().isEmpty()) {
                    list.add(item.trim());
                }
            }
            return list;
        } catch (Exception e) {
            return new java.util.ArrayList<>();
        }
    }
}
