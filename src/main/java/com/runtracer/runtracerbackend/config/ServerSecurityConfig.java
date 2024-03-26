package com.runtracer.runtracerbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;

@Configuration
@Profile("mariadb-flyway-dev")
public class ServerSecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, ServerSecurityContextRepository securityContextRepository) {
        return http.authorizeExchange()
                .pathMatchers("/public/**").permitAll()
                .pathMatchers(HttpMethod.POST, "/api/**").authenticated()
                .pathMatchers("/admin/**").hasRole("ADMIN")
                .anyExchange().permitAll()
                .and()
                .httpBasic().and()
                .securityContextRepository(securityContextRepository)
                .csrf().disable()
                .logout().disable()
                .build();
    }
}
