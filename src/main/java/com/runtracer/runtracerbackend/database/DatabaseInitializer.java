package com.runtracer.runtracerbackend.database;

import com.runtracer.runtracerbackend.events.DatabaseConfiguredEvent;
import io.r2dbc.spi.R2dbcException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@Profile("asynchronous")
@DependsOn("databaseConfig")
public class DatabaseInitializer {

    @Autowired
    private final DatabaseClient databaseClient;
    private final Map<String, String> scriptDependencies;
    private final Map<String, ScriptResult> scriptResults;

    @Value("${ADMIN_USERNAME}")
    private String adminUsername;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Value("${DATABASE_USERNAME}")
    private String databaseUsername;

    @Value("${DATABASE_PASSWORD}")
    private String databasePassword;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public DatabaseInitializer(DatabaseClient databaseClient) {
        log.info("DatabaseInitializer: testing connection...");
        log.info("DatabaseInitializer after test...");
        this.databaseClient = databaseClient;
        this.scriptDependencies = new HashMap<>();
        this.scriptResults = new HashMap<>();
        // Define script dependencies
        scriptDependencies.put("create_users_table.sql", "use_database.sql");
        scriptDependencies.put("create_roles_table.sql", "use_database.sql");
        scriptDependencies.put("insert_admin_user.sql", "create_users_table.sql");
        scriptDependencies.put("insert_admin_role.sql", "create_roles_table.sql");
        scriptDependencies.put("create_user_roles_table.sql", "insert_admin_role.sql");
        scriptDependencies.put("associate_admin_user_role.sql", "create_user_roles_table.sql");
        log.info("Script dependencies: {}", scriptDependencies); // Log the contents of the scriptDependencies map
    }

    @EventListener
    public void onDatabaseConfiguredEvent(DatabaseConfiguredEvent event) {
        log.info("DatabaseConfiguredEvent received, initializing database...");
        testDatabaseConnection();
        log.info("Script dependencies at the time of event: {}", scriptDependencies); // Log the contents of the scriptDependencies map

        for (String script : scriptDependencies.keySet()) {
            log.debug("Processing script: {}", script); // Log the name of the script
            SqlScriptExecutor sqlScriptExecutor =
                    new SqlScriptExecutor(script,   // Pass the script name
                            databaseClient,         // Pass the DatabaseClient instance
                            databaseUsername,       // Pass the database username
                            databasePassword);      // Pass the database password
            sqlScriptExecutor.run();
        }
    }

    public void testDatabaseConnection() {
        log.info("Testing database connection...");
        try {
            // Check if the connection to the database server is successful
            Mono<Map<String, Object>> resultMono = databaseClient.sql("SELECT 1").fetch().one();
            Integer result = resultMono.map(r -> r != null && r.get(0) != null ? (Integer) r.get(0) : 0).block();
            if (result != null && result == 1) {
                log.info("Database connection test successful.");
            } else {
                log.warn("Database connection test failed. Result: {}", result);
                return;
            }
            // Check if the correct database is selected
            resultMono = databaseClient.sql("SELECT DATABASE()").fetch().one();
            String databaseName = resultMono.map(r -> r != null && r.get("DATABASE()") != null ? (String) r.get("DATABASE()") : "").block();
            if (databaseName != null && !databaseName.isEmpty()) {
                log.info("Selected database: {}", databaseName);
            } else {
                log.warn("No database selected.");
                return;
            }
            // Check if the tables in the database exist
            resultMono = databaseClient.sql("SHOW TABLES").fetch().one();
            Map<String, Object> tables = resultMono.block();
            if (tables != null && !tables.isEmpty()) {
                log.info("Tables in the database: {}", tables.keySet());
            } else {
                log.warn("No tables found in the database.");
            }
        } catch (R2dbcException e) {
            log.error("R2DBC error while testing database connection", e);
        } catch (Exception e) {
            log.error("Unexpected error while testing database connection", e);
        }
    }

    enum ScriptResult {
        SUCCESS, NO_ROWS_UPDATED, FAILED_TO_READ_SCRIPT, ERROR
    }
}