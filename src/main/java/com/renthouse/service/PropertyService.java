package com.renthouse.service;

import com.renthouse.dto.PropertyDTO;
import com.renthouse.model.*;
import com.renthouse.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;

    public List<PropertyDTO> getAllActive(Long currentUserId) {
        return propertyRepository.findByStatus(Property.Status.ACTIVE)
                .stream()
                .map(p -> mapToDTO(p, currentUserId))
                .collect(Collectors.toList());
    }

    public PropertyDTO getById(Long id, Long currentUserId) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        return mapToDTO(property, currentUserId);
    }

    public List<PropertyDTO> search(String city, String type, BigDecimal minRent,
                                    BigDecimal maxRent, Integer bedrooms, Long currentUserId) {
        Property.PropertyType propertyType = null;
        if (type != null && !type.isBlank()) {
            try { propertyType = Property.PropertyType.valueOf(type.toUpperCase()); }
            catch (IllegalArgumentException ignored) {}
        }
        String cityParam = (city != null && !city.isBlank()) ? city : null;

        return propertyRepository.searchProperties(cityParam, propertyType, minRent, maxRent, bedrooms)
                .stream()
                .map(p -> mapToDTO(p, currentUserId))
                .collect(Collectors.toList());
    }

    public List<PropertyDTO> getOwnerProperties(Long ownerId) {
        return propertyRepository.findByOwnerIdOrderByCreatedAtDesc(ownerId)
                .stream()
                .map(p -> mapToDTO(p, null))
                .collect(Collectors.toList());
    }

    @Transactional
    public PropertyDTO create(Map<String, Object> body, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Property property = buildPropertyFromMap(body, owner);
        property = propertyRepository.save(property);
        return mapToDTO(property, null);
    }

    @Transactional
    public PropertyDTO update(Long id, Map<String, Object> body, Long ownerId) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        if (!property.getOwner().getId().equals(ownerId)) {
            throw new RuntimeException("Unauthorized: not the owner");
        }

        applyUpdates(property, body);
        property = propertyRepository.save(property);
        return mapToDTO(property, null);
    }

    @Transactional
    public void delete(Long id, Long userId, String role) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        boolean isOwner = property.getOwner().getId().equals(userId);
        boolean isAdmin = "ADMIN".equals(role);

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("Unauthorized");
        }
        propertyRepository.delete(property);
    }

    private Property buildPropertyFromMap(Map<String, Object> body, User owner) {
        return Property.builder()
                .title(getString(body, "title"))
                .description(getString(body, "description"))
                .address(getString(body, "address"))
                .city(getString(body, "city"))
                .district(getString(body, "district"))
                .state(getString(body, "state"))
                .pincode(getString(body, "pincode"))
                .rent(getBigDecimal(body, "rent"))
                .bedrooms(getInt(body, "bedrooms"))
                .bathrooms(getInt(body, "bathrooms"))
                .areaSqft(getInt(body, "areaSqft"))
                .propertyType(getEnum(body, "propertyType", Property.PropertyType.class))
                .furnishing(getEnum(body, "furnishing", Property.Furnishing.class))
                .amenities(getList(body, "amenities"))
                .status(Property.Status.ACTIVE)
                .owner(owner)
                .build();
    }

    private void applyUpdates(Property property, Map<String, Object> body) {
        if (body.containsKey("title"))        property.setTitle(getString(body, "title"));
        if (body.containsKey("description"))  property.setDescription(getString(body, "description"));
        if (body.containsKey("address"))      property.setAddress(getString(body, "address"));
        if (body.containsKey("city"))         property.setCity(getString(body, "city"));
        if (body.containsKey("district"))     property.setDistrict(getString(body, "district"));
        if (body.containsKey("state"))        property.setState(getString(body, "state"));
        if (body.containsKey("pincode"))      property.setPincode(getString(body, "pincode"));
        if (body.containsKey("rent"))         property.setRent(getBigDecimal(body, "rent"));
        if (body.containsKey("bedrooms"))     property.setBedrooms(getInt(body, "bedrooms"));
        if (body.containsKey("bathrooms"))    property.setBathrooms(getInt(body, "bathrooms"));
        if (body.containsKey("areaSqft"))     property.setAreaSqft(getInt(body, "areaSqft"));
        if (body.containsKey("amenities"))    property.setAmenities(getList(body, "amenities"));
        if (body.containsKey("status"))       property.setStatus(Property.Status.valueOf(getString(body, "status")));
        if (body.containsKey("propertyType")) property.setPropertyType(Property.PropertyType.valueOf(getString(body, "propertyType")));
        if (body.containsKey("furnishing"))   property.setFurnishing(Property.Furnishing.valueOf(getString(body, "furnishing")));
    }

    public PropertyDTO mapToDTO(Property p, Long currentUserId) {
        boolean isFavorite = currentUserId != null &&
                favoriteRepository.existsByUserIdAndPropertyId(currentUserId, p.getId());

        List<PropertyDTO.ImageDTO> images = p.getImages().stream()
                .map(img -> PropertyDTO.ImageDTO.builder()
                        .id(img.getId())
                        .imageUrl(img.getImageUrl())
                        .cloudinaryPublicId(img.getCloudinaryPublicId())
                        .isPrimary(img.getIsPrimary())
                        .build())
                .collect(Collectors.toList());

        return PropertyDTO.builder()
                .id(p.getId())
                .title(p.getTitle())
                .description(p.getDescription())
                .address(p.getAddress())
                .city(p.getCity())
                .district(p.getDistrict())
                .state(p.getState())
                .pincode(p.getPincode())
                .rent(p.getRent())
                .bedrooms(p.getBedrooms())
                .bathrooms(p.getBathrooms())
                .areaSqft(p.getAreaSqft())
                .propertyType(p.getPropertyType() != null ? p.getPropertyType().name() : null)
                .furnishing(p.getFurnishing() != null ? p.getFurnishing().name() : null)
                .amenities(p.getAmenities())
                .status(p.getStatus() != null ? p.getStatus().name() : null)
                .owner(AuthService.mapToUserDTO(p.getOwner()))
                .images(images)
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .isFavorite(isFavorite)
                .build();
    }

    // ---- Helpers ----
    private String getString(Map<String, Object> map, String key) {
        Object val = map.get(key);
        return val != null ? val.toString() : null;
    }

    private Integer getInt(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val == null) return null;
        if (val instanceof Integer) return (Integer) val;
        try {
            return Integer.parseInt(val.toString());
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private java.util.List<String> getList(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val instanceof java.util.List) {
            return (java.util.List<String>) val;
        }
        return new java.util.ArrayList<>();
    }

    private java.math.BigDecimal getBigDecimal(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val == null) return null;
        if (val instanceof java.math.BigDecimal) return (java.math.BigDecimal) val;
        try {
            return new java.math.BigDecimal(val.toString());
        } catch (Exception e) {
            return null;
        }
    }

    private <T extends Enum<T>> T getEnum(Map<String, Object> map, String key, Class<T> clazz) {
        String val = getString(map, key);
        if (val == null) return null;
        try { return Enum.valueOf(clazz, val.toUpperCase()); }
        catch (IllegalArgumentException e) { return null; }
    }
}
