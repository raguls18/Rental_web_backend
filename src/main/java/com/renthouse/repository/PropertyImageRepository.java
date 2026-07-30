package com.renthouse.repository;

import com.renthouse.model.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {
    List<PropertyImage> findByPropertyId(Long propertyId);
    Optional<PropertyImage> findByPropertyIdAndIsPrimaryTrue(Long propertyId);
}
