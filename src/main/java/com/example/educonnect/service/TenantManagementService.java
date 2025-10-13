package com.example.educonnect.service;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@RequiredArgsConstructor
public class TenantManagementService {
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public void createTenantSchema(String schemaName) {
        String validatedName = validateSchemaName(schemaName);
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS " + validatedName);
        Flyway flyway = Flyway.configure()
                .locations("db/migration/tenant")
                .dataSource(dataSource)
                .schemas(validatedName)
                .load();
        flyway.migrate();
    }
    private String validateSchemaName(String schemaName){
        String sanitized = schemaName.toLowerCase().replaceAll("\\s", "")
                .replaceAll("[^a-z0-9_]", "");
        if (sanitized.isEmpty() || !sanitized.equals(schemaName.trim())){
            throw new IllegalArgumentException("Invalid characters in schema name");
        }
        return sanitized;
    }
}
