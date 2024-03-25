package com.runtracer.runtracerbackend.database;

import io.r2dbc.spi.R2dbcException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.util.StreamUtils;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class SqlScriptExecutor implements Runnable {

    private final String scriptName;
    private final DatabaseClient databaseClient;
    private final String databaseUsername;
    private final String databasePassword;

public SqlScriptExecutor(String scriptName, DatabaseClient databaseClient, String databaseUsername, String databasePassword) {
    this.scriptName = scriptName;
    this.databaseClient = databaseClient;
    this.databaseUsername = databaseUsername;
    this.databasePassword = databasePassword;

    log.info("Initializing SqlScriptExecutor with ScriptName: {}, DatabaseClient: {}, Username: {}, Password: {}", scriptName, databaseClient, databaseUsername, databasePassword);

    testDatabaseConnection();
}

private void testDatabaseConnection() {
    log.debug("Testing database connection...");
    try {
        Mono<Map<String, Object>> resultMono = databaseClient.sql("SELECT 1").fetch().one();
        Integer result = resultMono.map(r -> r != null && r.get(0) != null ? (Integer) r.get(0) : 0).block();
        if (result != null && result == 1) {
            log.debug("Database connection test successful.");
        } else {
            log.warn("Database connection test failed. Result: {}", result);
        }
    } catch (DataAccessException e) {
        log.error("Data access error while testing database connection", e);
    } catch (R2dbcException e) {
        log.error("R2DBC error while testing database connection", e);
    } catch (Exception e) {
        log.error("Unexpected error while testing database connection", e);
    }
}

    @Override
    public void run() {
        log.debug("Executing SQL script: {}", scriptName);
        try {
            ClassPathResource resource = new ClassPathResource("sql/" + scriptName);
            String sql = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

            log.debug("SQL script content: {}", sql); // Log the content of the SQL script

            sql = sql.replace("${DATABASE_USERNAME}", databaseUsername).replace("${DATABASE_PASSWORD}", databasePassword);
            Mono<Long> rowsUpdatedMono = databaseClient.sql(sql).fetch().rowsUpdated();
            Long rowsUpdated = rowsUpdatedMono.block();

            if (rowsUpdated != null) {
                log.info("SQL script '{}' executed successfully. Rows updated: {}", scriptName, rowsUpdated);
            } else {
                log.warn("No rows updated after executing SQL script: {}", scriptName);
            }
        } catch (IOException e) {
            log.error("Failed to read SQL script: {}", scriptName, e);
            throw new RuntimeException("Failed to read SQL script: " + scriptName, e);
        } catch (Exception e) {
            log.error("Error executing SQL script: {}", scriptName, e);
            throw new RuntimeException("Error executing SQL script: " + scriptName, e);
        }
    }
}