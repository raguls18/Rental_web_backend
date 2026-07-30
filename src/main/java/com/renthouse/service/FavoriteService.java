package com.renthouse.service;

import com.renthouse.dto.PropertyDTO;
import com.renthouse.model.Favorite;
import com.renthouse.model.Property;
import com.renthouse.model.User;
import com.renthouse.repository.FavoriteRepository;
import com.renthouse.repository.PropertyRepository;
import com.renthouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final PropertyService propertyService;

    public List<PropertyDTO> getMyFavorites(Long userId) {
        return favoriteRepository.findByUserIdOrderBySavedAtDesc(userId)
                .stream()
                .map(fav -> propertyService.mapToDTO(fav.getProperty(), userId))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addFavorite(Long userId, Long propertyId) {
        if (favoriteRepository.existsByUserIdAndPropertyId(userId, propertyId)) {
            return; // Already favorited
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        Favorite favorite = Favorite.builder()
                .user(user)
                .property(property)
                .build();
        favoriteRepository.save(favorite);
    }

    @Transactional
    public void removeFavorite(Long userId, Long propertyId) {
        favoriteRepository.deleteByUserIdAndPropertyId(userId, propertyId);
    }

    public boolean isFavorite(Long userId, Long propertyId) {
        return favoriteRepository.existsByUserIdAndPropertyId(userId, propertyId);
    }
}
