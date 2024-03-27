package com.runtracer.runtracerbackend.controller;

import com.runtracer.runtracerbackend.exceptions.InvalidCredentialsException;
import com.runtracer.runtracerbackend.exceptions.UserNotFoundException;
import com.runtracer.runtracerbackend.repository.ReactiveOAuth2ClientRegistrationRepository;
import com.runtracer.runtracerbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@EnableWebFlux
@RestController
public class LoginController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ReactiveOAuth2ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    public LoginController(UserService userService, PasswordEncoder passwordEncoder, ReactiveOAuth2ClientRegistrationRepository clientRegistrationRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Operation(summary = "Log in a user", responses = {
        @ApiResponse(responseCode = "200", description = "Login successful"),
        @ApiResponse(responseCode = "400", description = "Invalid credentials", content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PostMapping("/login")
    public Mono<String> login(@RequestBody @Parameter(description = "Form with username and password") LoginForm loginForm) {
        log.info("Login attempt for username: {}", loginForm.getUsername());

        return userService.findByUsername(loginForm.getUsername())
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("User not found for username: {}", loginForm.getUsername());
                    throw new UserNotFoundException();
                }))
                .flatMap(user -> {
                    boolean passwordMatches = passwordEncoder.matches(loginForm.getPassword(), user.getPassword());
                    log.info("Password matches for username: {}: {}", loginForm.getUsername(), passwordMatches);

                    if (!passwordMatches) {
                        throw new InvalidCredentialsException();
                    }
                    return Mono.just("Login successful");
                });
    }

    @Setter
    @Getter
    @Schema(description = "Form with username and password")
    public static class LoginForm {
        private String username;
        private String password;
    }
}