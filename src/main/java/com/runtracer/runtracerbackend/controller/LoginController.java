package com.runtracer.runtracerbackend.controller;

import com.runtracer.runtracerbackend.service.UserService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoginController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public Mono<String> login(@RequestBody LoginForm loginForm) {
        log.info("Login attempt for username: {}", loginForm.getUsername());

        return userService.findByUsername(loginForm.getUsername())
                .flatMap(user -> {
                    if (user == null) {
                        log.error("User not found for username: {}", loginForm.getUsername());
                        return Mono.error(new RuntimeException("User not found"));
                    }

                    // Compare encoded passwords
                    boolean passwordMatches = passwordEncoder.matches(loginForm.getPassword(), user.getPassword());
                    log.info("Password matches for username: {}: {}", loginForm.getUsername(), passwordMatches);

                    return passwordMatches ? Mono.just("Login successful") : Mono.error(new RuntimeException("Invalid credentials"));
                });
    }

    @Setter
    @Getter
    static class LoginForm {
        // Getters and setters
        private String username;
        private String password;
    }
}
