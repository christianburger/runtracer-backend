package com.runtracer.runtracerbackend.controller;

import com.runtracer.runtracerbackend.model.activity.Activity;
import com.runtracer.runtracerbackend.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Tag(name = "ActivityController", description = "Operations pertaining to activities in Runtracer")
@RestController
@EnableWebFlux
public class ActivityController {

    @Value("${application.name}")
    private String applicationName;

    @Value("${application.version}")
    private String applicationVersion;

    @Autowired
    private ActivityService activityService;

    @Operation(summary = "Get the version of the application")
    @GetMapping("/api/version")
    public String getVersion() {
        return applicationVersion;
    }

    @Operation(summary = "Get the name of the application")
    @GetMapping("/api/appName")
    public String getAppName() {
        return applicationName;
    }

    @Operation(summary = "Get an activity by its ID")
    @GetMapping("/{id}")
    public Mono<Activity> getActivity(@PathVariable UUID id) {
        return activityService.findById(id);
    }
}