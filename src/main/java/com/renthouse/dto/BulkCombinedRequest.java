package com.renthouse.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class BulkCombinedRequest {
    // Owner details
    private String ownerName;
    private String ownerEmail;
    private String ownerPhone;
    private String ownerPassword;
    private String ownerImageUrl;

    // Property details
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
    private String amenities;
    private List<String> propertyImageUrls;
}
