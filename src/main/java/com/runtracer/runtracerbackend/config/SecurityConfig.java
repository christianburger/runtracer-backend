package com.runtracer.runtracerbackend.config;


import com.runtracer.runtracerbackend.model.Role;
import com.runtracer.runtracerbackend.model.User;
import com.runtracer.runtracerbackend.model.UserRole;
import com.runtracer.runtracerbackend.service.RoleService;
import com.runtracer.runtracerbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Slf4j
public class SecurityConfig {

    private static PasswordEncoder passwordEncoderInstance;
    private final UserService userService;
    private final RoleService roleService;
    @Value("${application.admin.username}")
    private String adminUsername;
    @Value("${application.admin.password}")
    private String adminPassword;
    @Value("${application.admin.email}")
    private String adminEmail;

    public SecurityConfig(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    public static PasswordEncoder getPasswordEncoder() {
        if (passwordEncoderInstance == null) {
            passwordEncoderInstance = new BCryptPasswordEncoder();
        }
        return passwordEncoderInstance;
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
        admin.setEnabled(true); // Set enabled to true
        admin.setAccountNonExpired(true); // Set accountNonExpired to true
        admin.setAccountNonLocked(true); // Set accountNonLocked to true
        admin.setCredentialsNonExpired(true); // Set credentialsNonExpired to true

        log.info("Admin user details: {}", admin);

        userService.save(admin)
                .doOnNext(user -> log.info("Saved user: {}", user))
                .doOnError(e -> log.error("Error occurred while saving user: ", e))
                .flatMap(user -> {
                    Role adminRole = new Role();
                    adminRole.setName(Role.RoleType.ROLE_ADMIN);
                    log.info("Admin role details: {}", adminRole);

                    return roleService.save(adminRole)
                            .doOnNext(savedRole -> log.info("Saved role: {}", savedRole))
                            .doOnError(e -> log.error("Error occurred while saving role: ", e))
                            .flatMap(savedRole -> {
                                UserRole userRole = new UserRole();
                                userRole.setUserId(user.getUserId());
                                userRole.setRoleId(savedRole.getRoleId());
                                log.info("User role details: {}", userRole);

                                return userService.saveUserRole(userRole)
                                        .doOnNext(savedUserRole -> log.info("Saved user role: {}", savedUserRole))
                                        .doOnError(e -> log.error("Error occurred while saving user role: ", e));
                            });
                })
                .doOnSuccess(user -> log.info("Admin user created successfully"))
                .onErrorResume(DataIntegrityViolationException.class, ex -> {
                    if (ex.getMessage().contains("duplicate key value")) {
                        log.info("Admin user already exists");
                        return Mono.empty();
                    } else {
                        log.error("An error occurred while creating the admin user", ex);
                        return Mono.empty();
                    }
                })
                .subscribe();
    }
}