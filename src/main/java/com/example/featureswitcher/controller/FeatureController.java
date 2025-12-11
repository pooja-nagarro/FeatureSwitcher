package com.example.featureswitcher.controller;

import com.example.featureswitcher.model.Feature;
import com.example.featureswitcher.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/features")
public class FeatureController {

    @Autowired
    private FeatureService featureService;

    @GetMapping
    public String listFeatures(Model model) {
        model.addAttribute("features", featureService.getAllFeatures());
        model.addAttribute("enabledCount", featureService.getEnabledFeaturesCount());
        model.addAttribute("disabledCount", featureService.getDisabledFeaturesCount());
        model.addAttribute("totalCount", featureService.getAllFeatures().size());
        return "features/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("feature", new Feature());
        return "features/form";
    }

    @PostMapping
    public String createFeature(@ModelAttribute Feature feature, RedirectAttributes redirectAttributes) {
        featureService.createFeature(feature);
        redirectAttributes.addFlashAttribute("message", "Feature created successfully!");
        redirectAttributes.addFlashAttribute("messageType", "success");
        return "redirect:/features";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return featureService.getFeatureById(id)
                .map(feature -> {
                    model.addAttribute("feature", feature);
                    return "features/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("message", "Feature not found!");
                    redirectAttributes.addFlashAttribute("messageType", "error");
                    return "redirect:/features";
                });
    }

    @PostMapping("/{id}")
    public String updateFeature(@PathVariable Long id, @ModelAttribute Feature feature, 
                                RedirectAttributes redirectAttributes) {
        featureService.updateFeature(id, feature)
                .ifPresentOrElse(
                        updatedFeature -> {
                            redirectAttributes.addFlashAttribute("message", "Feature updated successfully!");
                            redirectAttributes.addFlashAttribute("messageType", "success");
                        },
                        () -> {
                            redirectAttributes.addFlashAttribute("message", "Feature not found!");
                            redirectAttributes.addFlashAttribute("messageType", "error");
                        }
                );
        return "redirect:/features";
    }

    @PostMapping("/{id}/toggle")
    public String toggleFeature(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (featureService.toggleFeature(id)) {
            redirectAttributes.addFlashAttribute("message", "Feature toggled successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Feature not found!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/features";
    }

    @PostMapping("/{id}/delete")
    public String deleteFeature(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (featureService.deleteFeature(id)) {
            redirectAttributes.addFlashAttribute("message", "Feature deleted successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Feature not found!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/features";
    }
}
