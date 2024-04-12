package com.runtracer.runtracerbackend.config;

import com.runtracer.runtracerbackend.events.DatabaseConfiguredEvent;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;

@Configuration
@Slf4j
@ActiveProfiles("test")
public class FlywayTestConfiguration {

/*
    @Value("${spring.flyway.url}")
    private String flywayUrl;

    @Value("${spring.flyway.username}")
    private String username;

    @Value("${spring.flyway.password}")
    private String password;

    @Value("${spring.application.username}")
    private String adminUser;

    @Value("${application.email}")
    private String email;

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.message}")
    private String serverMessage;

    @Value("${spring.flyway.url}")
    private String flywayUrl;

    @Value("${spring.flyway.username}")
    private String username;

    @Value("${spring.flyway.password}")
    private String password;
     */

    private String flywayUrl = "jdbc:postgresql://localhost:5432/runtracer-test";
    private String username = "postgres";
    private String password = "password";

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Bean
    public DataSource dataSource() {
        log.info("Value of flywayUrl: {}", flywayUrl);
        log.info("Value of : {}", username);
        log.info("Value of : {}", password);
        log.info("Creating DataSource with URL: {}, username: {}, password: {}", flywayUrl, username, password);
        PGSimpleDataSource dataSource = DataSourceBuilder.create().type(PGSimpleDataSource.class).url(flywayUrl).username(username).password(password).build();
        log.info("DataSource created: {}", dataSource);
        eventPublisher.publishEvent(new DatabaseConfiguredEvent(this));
        log.info("DatabaseConfiguredEvent published");
        testConnection(dataSource);
        return dataSource;
    }

    @Bean
    public Flyway flyway(DataSource dataSource) {
        log.info("Configuring Flyway with DataSource: {}", dataSource);
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();

        log.info("Flyway configured: {}", flyway);
        log.info("Flyway configured: Info: {}", flyway.info());
        flyway.info();
        flyway.migrate();
        flyway.getConfiguration();
        return flyway;
    }

    public void testConnection(DataSource dataSource) {
        log.info("Testing connection with DataSource: {}", dataSource);
        try (Connection connection = dataSource.getConnection()) {
            if (connection != null && !connection.isClosed()) {
                log.info("Successfully connected to the database. Connection details:");
                log.info("URL: " + connection.getMetaData().getURL());
                log.info("Username: " + connection.getMetaData().getUserName());
            }
        } catch (SQLException e) {
            log.error("Failed to establish a connection to the database", e);
        }
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        log.info("Entering connectionFactory() method");

        ConnectionFactoryOptions options = configureConnectionFactoryOptions();

        log.info("Creating ConnectionFactory with options: {}", options);
        ConnectionFactory connectionFactory = ConnectionFactories.get(options);
        log.info("ConnectionFactory created: {}", connectionFactory);

        ConnectionPoolConfiguration poolConfiguration = ConnectionPoolConfiguration.builder(connectionFactory)
                .maxIdleTime(Duration.ofMinutes(30)) // Set max idle time for connections
                .initialSize(5) // Set initial size of the pool
                .maxSize(20) // Set max size of the pool
                .build();

        log.info("Creating ConnectionPool with poolConfiguration: {}", poolConfiguration);
        ConnectionFactory pool = new ConnectionPool(poolConfiguration);
        log.info("ConnectionPool created: {}", pool);

        log.info("Exiting connectionFactory() method");
        return pool;
    }

    private ConnectionFactoryOptions configureConnectionFactoryOptions() {
        log.info("Entering configureConnectionFactoryOptions() method");

        String r2dbc_url = "r2dbc:postgresql://localhost:5432/runtracer-test";
        ConnectionFactoryOptions options = ConnectionFactoryOptions.parse(r2dbc_url)
                .mutate()
                .option(ConnectionFactoryOptions.USER, username)
                .option(ConnectionFactoryOptions.PASSWORD, password)
                .option(Option.valueOf("SSL"), false)
                .build();

        log.info("R2DBC URL: {}", r2dbc_url);
        log.info("Username: {}", username);
        log.info("Password: {}", password);
        log.info("SSL: {}", false);
        log.info("Options: {}", options);

        // Print all parameters to check for extra quotes
        log.info("Printing all parameters:");
        log.info("R2DBC URL: {}", options.getRequiredValue(ConnectionFactoryOptions.HOST));
        log.info("Username: {}", options.getRequiredValue(ConnectionFactoryOptions.USER));
        log.info("Password: {}", options.getRequiredValue(ConnectionFactoryOptions.PASSWORD));
        log.info("SSL: {}", options.getRequiredValue(Option.valueOf("SSL")));

        log.info("Exiting configureConnectionFactoryOptions() method");
        return options;
    }

    @Bean
    public DatabaseClient databaseClient(ConnectionFactory connectionFactory) {
        log.info("Creating DatabaseClient with ConnectionFactory: {}", connectionFactory);
        DatabaseClient databaseClient = DatabaseClient.create(connectionFactory);
        log.info("DatabaseClient created: {}", databaseClient);
        return databaseClient;
    }

    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory) {
        log.info("Creating R2dbcEntityTemplate with ConnectionFactory: {}", connectionFactory);
        R2dbcEntityTemplate r2dbcEntityTemplate = new R2dbcEntityTemplate(connectionFactory);
        log.info("R2dbcEntityTemplate created: {}", r2dbcEntityTemplate);
        return r2dbcEntityTemplate;
    }
}