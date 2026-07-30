package com.renthouse.controller;

import com.renthouse.dto.PropertyDTO;
import com.renthouse.model.User;
import com.renthouse.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<List<PropertyDTO>> getMyFavorites(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(favoriteService.getMyFavorites(currentUser.getId()));
    }

    @PostMapping("/{propertyId}")
    public ResponseEntity<Map<String, Object>> addFavorite(
            @PathVariable Long propertyId,
            @AuthenticationPrincipal User currentUser) {
        favoriteService.addFavorite(currentUser.getId(), propertyId);
        return ResponseEntity.ok(Map.of(
            "message", "Added to favorites",
            "isFavorite", true
        ));
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<Map<String, Object>> removeFavorite(
            @PathVariable Long propertyId,
            @AuthenticationPrincipal User currentUser) {
        favoriteService.removeFavorite(currentUser.getId(), propertyId);
        return ResponseEntity.ok(Map.of(
            "message", "Removed from favorites",
            "isFavorite", false
        ));
    }
}
