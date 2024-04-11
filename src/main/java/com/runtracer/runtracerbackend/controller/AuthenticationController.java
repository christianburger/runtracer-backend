package com.runtracer.runtracerbackend.controller;

import com.runtracer.runtracerbackend.dto.UserDto;
import com.runtracer.runtracerbackend.exceptions.InvalidCredentialsException;
import com.runtracer.runtracerbackend.exceptions.UserNotFoundException;
import com.runtracer.runtracerbackend.repository.ReactiveOAuth2ClientRegistrationRepository;
import com.runtracer.runtracerbackend.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Mono;

@Tag(name = "AuthenticationController", description = "Operations pertaining to authentication in Runtracer")
@Slf4j
@Component
@EnableWebFlux
@RestController
@RequestMapping("/api")
public class AuthenticationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(UserService userService, PasswordEncoder passwordEncoder, ReactiveOAuth2ClientRegistrationRepository clientRegistrationRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/login")
    public Mono<String> login(@RequestBody @Parameter(description = "Form with username and password") LoginForm loginForm) {
        log.info("Login attempt for username: {}", loginForm.getUsername());

        UserDto userDto = new UserDto();
        userDto.setUsername(loginForm.getUsername());
        userDto.setPassword(loginForm.getPassword());

        log.info("Created UserDto with username: {} and password: {}", userDto.getUsername(), userDto.getPassword());

        return userService.authenticateFromDto(userDto)
                .map(user -> {
                    log.info("Login successful for username: {}", loginForm.getUsername());
                    return "Login successful";
                })
                .doOnEach(signal -> {
                    if (signal.hasError()) {
                        log.error("Error occurred during authentication: ", signal.getThrowable());
                    } else if (signal.isOnComplete()) {
                        log.info("Authentication completed without error");
                    }
                })
                .onErrorResume(InvalidCredentialsException.class, e -> {
                    log.error("Invalid credentials for username: {}", loginForm.getUsername());
                    throw new InvalidCredentialsException();
                })
                .onErrorResume(UsernameNotFoundException.class, e -> {
                    log.error("User not found for username: {}", loginForm.getUsername());
                    throw new UserNotFoundException();
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