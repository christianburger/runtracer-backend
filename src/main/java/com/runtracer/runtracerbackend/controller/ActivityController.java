package com.runtracer.runtracerbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityController {

    @GetMapping("/api/version")
    public String getVersion() {
        return "1.0"; // or whatever version you want to return
    }
}
