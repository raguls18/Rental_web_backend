package com.renthouse.service;

import com.renthouse.model.Property;
import com.renthouse.model.PropertyImage;
import com.renthouse.repository.PropertyImageRepository;
import com.renthouse.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryService {

    private final PropertyImageRepository imageRepository;
    private final PropertyRepository propertyRepository;

    @Value("${upload.dir:uploads}")
    private String uploadDirPath;

    private Path getUploadDir() throws IOException {
        Path dir = Paths.get(uploadDirPath).toAbsolutePath();
        Files.createDirectories(dir);
        return dir;
    }

    @Transactional
    public PropertyImage uploadImage(MultipartFile file, Long propertyId, boolean isPrimary) throws IOException {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        // Create upload directory if it doesn't exist
        Path uploadDir = getUploadDir();

        // Generate unique filename preserving extension
        String originalName = file.getOriginalFilename();
        String ext = (originalName != null && originalName.contains("."))
                ? originalName.substring(originalName.lastIndexOf('.'))
                : ".jpg";
        String uniqueName = UUID.randomUUID().toString() + ext;

        // Save file to disk
        Path filePath = uploadDir.resolve(uniqueName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        log.info("Image saved locally: {}", filePath);

        // Build public URL (served by Spring Boot static handler)
        String imageUrl = "http://localhost:8080/uploads/" + uniqueName;

        // If isPrimary, unset all other primary flags for this property
        if (isPrimary) {
            imageRepository.findByPropertyId(propertyId).forEach(img -> {
                img.setIsPrimary(false);
                imageRepository.save(img);
            });
        }

        PropertyImage image = PropertyImage.builder()
                .property(property)
                .imageUrl(imageUrl)
                .cloudinaryPublicId(uniqueName)   // reuse field to store filename
                .isPrimary(isPrimary || imageRepository.findByPropertyId(propertyId).isEmpty())
                .build();

        return imageRepository.save(image);
    }

    @Transactional
    public void deleteImage(Long imageId) throws IOException {
        PropertyImage image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        // Delete file from disk
        try {
            Path filePath = getUploadDir().resolve(image.getCloudinaryPublicId());
            Files.deleteIfExists(filePath);
            log.info("Deleted local image: {}", filePath);
        } catch (Exception e) {
            log.warn("Could not delete local file for image {}: {}", imageId, e.getMessage());
        }

        imageRepository.delete(image);
    }
}
