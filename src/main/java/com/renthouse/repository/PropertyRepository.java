package com.renthouse.repository;

import com.renthouse.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findByStatus(Property.Status status);

    List<Property> findByOwnerIdOrderByCreatedAtDesc(Long ownerId);

    @Query("""
        SELECT p FROM Property p
        WHERE p.status = 'ACTIVE'
          AND (:city     IS NULL OR LOWER(p.city) LIKE LOWER(CONCAT('%', :city, '%')))
          AND (:type     IS NULL OR p.propertyType = :type)
          AND (:minRent  IS NULL OR p.rent >= :minRent)
          AND (:maxRent  IS NULL OR p.rent <= :maxRent)
          AND (:bedrooms IS NULL OR p.bedrooms = :bedrooms)
        ORDER BY p.createdAt DESC
    """)
    List<Property> searchProperties(
            @Param("city")     String city,
            @Param("type")     Property.PropertyType type,
            @Param("minRent")  BigDecimal minRent,
            @Param("maxRent")  BigDecimal maxRent,
            @Param("bedrooms") Integer bedrooms
    );

    long countByStatus(Property.Status status);
}
