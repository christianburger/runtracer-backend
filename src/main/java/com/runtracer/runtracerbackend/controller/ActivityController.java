package com.runtracer.runtracerbackend.controller;

import com.runtracer.runtracerbackend.dto.ActivityDto;
import com.runtracer.runtracerbackend.mappers.ActivityMapper;
import com.runtracer.runtracerbackend.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    @Autowired
    private ActivityMapper activityMapper;

    @Operation(summary = "Get the version of the application")
    @GetMapping("/api/version")
    public String getVersion() {
        return applicationVersion;
    }

    @Operation(summary = "Get the name of the application")
    @GetMapping("/api/appName")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getAppName() {
        return applicationName;
    }

    @Operation(summary = "Get an activity by its ID")
    @GetMapping("/api/activities/{id}")
    public Mono<ActivityDto> getActivity(@PathVariable Long id) {
        return activityService.findById(id)
                .map(activityMapper::toDto);
    }

    @Operation(summary = "Create a new activity")
    @PostMapping("/api/activities")
    public Mono<ActivityDto> createActivity(@RequestBody ActivityDto activityDto) {
        return activityService.save(activityMapper.toEntity(activityDto))
                .map(activityMapper::toDto);
    }

    @Operation(summary = "Update an existing activity")
    @PutMapping("/api/activities/{id}")
    public Mono<ActivityDto> updateActivity(@PathVariable Long id, @RequestBody ActivityDto activityDto) {
        return activityService.update(id, activityMapper.toEntity(activityDto))
                .map(activityMapper::toDto);
    }

    @Operation(summary = "Get all activities")
    @GetMapping("/api/activities")
    public Flux<ActivityDto> getAllActivities() {
        return activityService.findAll()
                .map(activityMapper::toDto);
    }

    @Operation(summary = "Delete an activity by its ID")
    @DeleteMapping("/api/activities/{id}")
    public Mono<Void> deleteActivity(@PathVariable Long id) {
        return activityService.deleteById(id);
    }

}