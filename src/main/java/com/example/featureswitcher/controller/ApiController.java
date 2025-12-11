package com.example.featureswitcher.controller;

import com.example.featureswitcher.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private FeatureService featureService;

    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("application", "Feature Switcher");
        status.put("status", "running");
        status.put("timestamp", LocalDateTime.now().toString());
        status.put("version", "1.0.0");
        status.put("javaVersion", System.getProperty("java.version"));
        status.put("totalFeatures", featureService.getAllFeatures().size());
        status.put("enabledFeatures", featureService.getEnabledFeaturesCount());
        return status;
    }

    @GetMapping("/hello")
    public Map<String, String> hello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from Feature Switcher Application!");
        return response;
    }

}
