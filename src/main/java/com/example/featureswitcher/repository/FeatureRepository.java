package com.example.featureswitcher.repository;

import com.example.featureswitcher.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    List<Feature> findByEnabled(boolean enabled);
    List<Feature> findByCategoryIgnoreCase(String category);
    List<Feature> findByNameContainingIgnoreCase(String name);
}
