package com.runtracer.runtracerbackend.config;


import com.runtracer.runtracerbackend.model.User;
import com.runtracer.runtracerbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Slf4j
public class SecurityConfig {

    private final UserService userService;
    @Value("${application.admin.username}")
    private String adminUsername;

    @Value("${application.admin.password}")
    private String adminPassword;

    @Value("${application.admin.email}")
    private String adminEmail;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public ServerSecurityContextRepository securityContextRepository() {
        log.info("Creating ServerSecurityContextRepository bean");
        return new WebSessionServerSecurityContextRepository();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void insertAdminUser() {
        log.info("Application is ready, inserting admin user");
        User admin = new User();
        admin.setUsername(adminUsername);
        admin.setPassword(this.passwordEncoder().encode(adminPassword));
        admin.setEmail(adminEmail);
        userService.save(admin)
                .subscribe(user -> log.info("Admin user checked"),
                        error -> log.error("Error inserting admin user: {}", error.getMessage()));
    }
}