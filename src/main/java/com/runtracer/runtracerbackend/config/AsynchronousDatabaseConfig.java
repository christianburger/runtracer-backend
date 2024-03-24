package com.runtracer.runtracerbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
@Profile("asynchronous")
public class AsynchronousDatabaseConfig implements DatabaseConfigStrategy {

    @Bean
    public DatabaseClient databaseClient() {
        // TODO: Implement asynchronous database client
        return null;
    }
}