package com.example.featureswitcher.controller;

import com.example.featureswitcher.model.Feature;
import com.example.featureswitcher.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/features")
public class FeatureRestController {

    @Autowired
    private FeatureService featureService;

    @GetMapping
    public List<Feature> getAllFeatures() {
        return featureService.getAllFeatures();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feature> getFeatureById(@PathVariable Long id) {
        return featureService.getFeatureById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Feature> createFeature(@RequestBody Feature feature) {
        Feature created = featureService.createFeature(feature);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feature> updateFeature(@PathVariable Long id, @RequestBody Feature feature) {
        return featureService.updateFeature(id, feature)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/toggle")
    public ResponseEntity<Map<String, Object>> toggleFeature(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        if (featureService.toggleFeature(id)) {
            Feature feature = featureService.getFeatureById(id).orElseThrow();
            response.put("success", true);
            response.put("enabled", feature.isEnabled());
            response.put("message", "Feature toggled successfully");
            return ResponseEntity.ok(response);
        }
        response.put("success", false);
        response.put("message", "Feature not found");
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeature(@PathVariable Long id) {
        if (featureService.deleteFeature(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", featureService.getAllFeatures().size());
        stats.put("enabled", featureService.getEnabledFeaturesCount());
        stats.put("disabled", featureService.getDisabledFeaturesCount());
        return stats;
    }
}
