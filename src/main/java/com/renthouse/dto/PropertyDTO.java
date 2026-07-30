package com.renthouse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDTO {
    private Long id;
    private String title;
    private String description;
    private String address;
    private String city;
    private String district;
    private String state;
    private String pincode;
    private BigDecimal rent;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer areaSqft;
    private String propertyType;
    private String furnishing;
    private java.util.List<String> amenities;
    private String status;
    private UserDTO owner;
    private List<ImageDTO> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isFavorite; // populated per-user

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageDTO {
        private Long id;
        private String imageUrl;
        private String cloudinaryPublicId;
        private Boolean isPrimary;
    }
}
