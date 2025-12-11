package com.example.featureswitcher.controller;

import com.example.featureswitcher.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private FeatureService featureService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Feature Switcher");
        model.addAttribute("message", "Welcome to Feature Switcher Application");
        model.addAttribute("totalFeatures", featureService.getAllFeatures().size());
        model.addAttribute("enabledFeatures", featureService.getEnabledFeaturesCount());
        model.addAttribute("disabledFeatures", featureService.getDisabledFeaturesCount());
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About");
        model.addAttribute("description", "Feature Switcher is a Spring Boot web application for managing feature toggles.");
        return "about";
    }

}
