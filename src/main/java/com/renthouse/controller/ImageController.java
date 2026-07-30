package com.renthouse.controller;

import com.renthouse.dto.PropertyDTO;
import com.renthouse.model.PropertyImage;
import com.renthouse.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("propertyId") Long propertyId,
            @RequestParam(value = "isPrimary", defaultValue = "false") boolean isPrimary) {
        try {
            log.info("Upload request: propertyId={}, filename={}, contentType={}, size={}",
                    propertyId, file.getOriginalFilename(), file.getContentType(), file.getSize());
            PropertyImage image = cloudinaryService.uploadImage(file, propertyId, isPrimary);
            log.info("Upload success: imageUrl={}", image.getImageUrl());
            return ResponseEntity.ok(Map.of(
                "id", image.getId(),
                "imageUrl", image.getImageUrl(),
                "cloudinaryPublicId", image.getCloudinaryPublicId(),
                "isPrimary", image.getIsPrimary()
            ));
        } catch (IOException e) {
            log.error("Upload IO error: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to upload image: " + e.getMessage()));
        } catch (Exception e) {
            log.error("Upload unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteImage(
            @PathVariable Long id) {
        try {
            cloudinaryService.deleteImage(id);
            return ResponseEntity.ok(Map.of("message", "Image deleted successfully"));
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Failed to delete image: " + e.getMessage()));
        }
    }
}
