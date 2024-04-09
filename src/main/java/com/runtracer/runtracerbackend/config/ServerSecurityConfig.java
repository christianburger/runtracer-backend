package com.runtracer.runtracerbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;

@Configuration
@Profile({"mariadb-flyway-dev",  "postgresql-flyway-dev"})
public class ServerSecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, ServerSecurityContextRepository securityContextRepository) {
        return http.authorizeExchange()
                .pathMatchers("/public/**", "/login").permitAll() // Allow unauthenticated access to /login
                .pathMatchers("/**").authenticated()
                .pathMatchers(HttpMethod.POST, "/api/**").authenticated()
                .pathMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyExchange().permitAll()
                .and()
                .formLogin()
                .and()
                .httpBasic().and()
                .securityContextRepository(securityContextRepository)
                .csrf().disable()
                .logout().disable()
                .build();
    }
}
