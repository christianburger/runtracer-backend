package com.runtracer.runtracerbackend.config;

import com.runtracer.runtracerbackend.events.DatabaseConfiguredEvent;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.core.DatabaseClient;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@Slf4j
@Profile("postgresql-flyway-dev")
public class SynchronousDatabaseConfigPostgresql {

    @Value("${spring.r2dbc.url}")
    private String r2dbc_url;

    @Value("${spring.flyway.url}")
    private String flywayUrl;

    @Value("${spring.flyway.username}")
    private String username;

    @Value("${spring.flyway.password}")
    private String password;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Bean
    public DataSource dataSource() {
        log.info("DataSource datasource: serverPort: {}", serverPort);
        log.info("Creating DataSource with URL: {}, username: {}, password: {}", flywayUrl, username, password);
        PGSimpleDataSource dataSource = DataSourceBuilder.create().type(PGSimpleDataSource.class).url(flywayUrl).username(username).password(password).build();

        log.info("DataSource created: {}", dataSource);

        // Publish DatabaseConfiguredEvent after DataSource is created
        eventPublisher.publishEvent(new DatabaseConfiguredEvent(this));
        log.info("DatabaseConfiguredEvent published");

        // Test and print connection details
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

    private void testConnection(DataSource dataSource) {
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

/*    @Bean
    public ConnectionFactory connectionFactory() {

        ConnectionFactoryOptions options = ConnectionFactoryOptions.parse(r2dbc_url).mutate().option(ConnectionFactoryOptions.USER, username).option(ConnectionFactoryOptions.PASSWORD, password).option(Option.valueOf("SSL"), false).build();

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
        ConnectionFactory connectionFactory = ConnectionFactories.get(options);
        log.info("Connection Factory: {}", connectionFactory);

        return connectionFactory;
    }*/

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