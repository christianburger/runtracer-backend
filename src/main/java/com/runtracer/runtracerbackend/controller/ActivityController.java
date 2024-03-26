package com.runtracer.runtracerbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;

@RestController
@EnableWebFlux
public class ActivityController {

    @GetMapping("/api/version")
    public String getVersion() {
        return "1.0";
    }
}
