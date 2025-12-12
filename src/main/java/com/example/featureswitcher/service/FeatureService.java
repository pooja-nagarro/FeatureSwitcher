package com.example.featureswitcher.service;

import com.example.featureswitcher.model.Feature;
import com.example.featureswitcher.repository.FeatureRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FeatureService {
    
    private final FeatureRepository featureRepository;

    @Autowired
    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    @PostConstruct
    public void init() {
        // Initialize with some sample features if database is empty
        if (featureRepository.count() == 0) {
            createFeature(new Feature(null, "Dark Mode", "Enable dark theme for the application", true, "UI"));
            createFeature(new Feature(null, "Email Notifications", "Send email notifications to users", false, "Backend"));
            createFeature(new Feature(null, "Advanced Search", "Enable advanced search capabilities", true, "Search"));
            createFeature(new Feature(null, "Export Data", "Allow users to export their data", false, "Data"));
            createFeature(new Feature(null, "Push Notifications", "Enable push notifications", true, "Mobile"));
        }
    }

    public List<Feature> getAllFeatures() {
        return featureRepository.findAll();
    }

    public Optional<Feature> getFeatureById(Long id) {
        return featureRepository.findById(id);
    }

    public Feature createFeature(Feature feature) {
        return featureRepository.save(feature);
    }

    public Optional<Feature> updateFeature(Long id, Feature updatedFeature) {
        return featureRepository.findById(id).map(existingFeature -> {
            existingFeature.setName(updatedFeature.getName());
            existingFeature.setDescription(updatedFeature.getDescription());
            existingFeature.setEnabled(updatedFeature.isEnabled());
            existingFeature.setCategory(updatedFeature.getCategory());
            return featureRepository.save(existingFeature);
        });
    }

    public boolean toggleFeature(Long id) {
        Optional<Feature> featureOpt = featureRepository.findById(id);
        if (featureOpt.isPresent()) {
            Feature feature = featureOpt.get();
            feature.setEnabled(!feature.isEnabled());
            featureRepository.save(feature);
            return true;
        }
        return false;
    }

    public boolean deleteFeature(Long id) {
        if (featureRepository.existsById(id)) {
            featureRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public long getEnabledFeaturesCount() {
        return featureRepository.findByEnabled(true).size();
    }

    public long getDisabledFeaturesCount() {
        return featureRepository.findByEnabled(false).size();
    }

    public List<Feature> getFeaturesByCategory(String category) {
        return featureRepository.findByCategoryIgnoreCase(category);
    }

    public List<Feature> getEnabledFeatures() {
        return featureRepository.findByEnabled(true);
    }
}
