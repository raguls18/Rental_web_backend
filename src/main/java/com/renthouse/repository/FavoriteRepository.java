package com.renthouse.repository;

import com.renthouse.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserIdOrderBySavedAtDesc(Long userId);
    boolean existsByUserIdAndPropertyId(Long userId, Long propertyId);
    void deleteByUserIdAndPropertyId(Long userId, Long propertyId);
    long countByPropertyId(Long propertyId);
}
