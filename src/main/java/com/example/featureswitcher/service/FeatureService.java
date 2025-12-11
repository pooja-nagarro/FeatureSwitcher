package com.example.featureswitcher.service;

import com.example.featureswitcher.model.Feature;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class FeatureService {
    
    private final ConcurrentHashMap<Long, Feature> features = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public FeatureService() {
        // Initialize with some sample features
        createFeature(new Feature(null, "Dark Mode", "Enable dark theme for the application", true, "UI"));
        createFeature(new Feature(null, "Email Notifications", "Send email notifications to users", false, "Backend"));
        createFeature(new Feature(null, "Advanced Search", "Enable advanced search capabilities", true, "Search"));
        createFeature(new Feature(null, "Export Data", "Allow users to export their data", false, "Data"));
        createFeature(new Feature(null, "Push Notifications", "Enable push notifications", true, "Mobile"));
    }

    public List<Feature> getAllFeatures() {
        return new ArrayList<>(features.values());
    }

    public Optional<Feature> getFeatureById(Long id) {
        return Optional.ofNullable(features.get(id));
    }

    public Feature createFeature(Feature feature) {
        Long id = idCounter.getAndIncrement();
        feature.setId(id);
        features.put(id, feature);
        return feature;
    }

    public Optional<Feature> updateFeature(Long id, Feature updatedFeature) {
        Feature existingFeature = features.get(id);
        if (existingFeature != null) {
            existingFeature.setName(updatedFeature.getName());
            existingFeature.setDescription(updatedFeature.getDescription());
            existingFeature.setEnabled(updatedFeature.isEnabled());
            existingFeature.setCategory(updatedFeature.getCategory());
            return Optional.of(existingFeature);
        }
        return Optional.empty();
    }

    public boolean toggleFeature(Long id) {
        Feature feature = features.get(id);
        if (feature != null) {
            feature.setEnabled(!feature.isEnabled());
            return true;
        }
        return false;
    }

    public boolean deleteFeature(Long id) {
        return features.remove(id) != null;
    }

    public long getEnabledFeaturesCount() {
        return features.values().stream().filter(Feature::isEnabled).count();
    }

    public long getDisabledFeaturesCount() {
        return features.values().stream().filter(f -> !f.isEnabled()).count();
    }

    public List<Feature> getFeaturesByCategory(String category) {
        return features.values().stream()
                .filter(f -> f.getCategory() != null && f.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    public List<Feature> getEnabledFeatures() {
        return features.values().stream()
                .filter(Feature::isEnabled)
                .toList();
    }
}
