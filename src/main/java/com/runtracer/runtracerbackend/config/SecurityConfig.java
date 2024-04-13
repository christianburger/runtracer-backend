package com.runtracer.runtracerbackend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    private static PasswordEncoder passwordEncoderInstance;

    public SecurityConfig() {
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
}