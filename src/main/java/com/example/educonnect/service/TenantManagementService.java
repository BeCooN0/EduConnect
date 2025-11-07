package com.example.educonnect.service;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
public class TenantManagementService {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public void createTenantSchema(String schemaName) {
        String validatedName = sanitizeSchemaName(schemaName);

        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS " + validatedName);

        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .schemas(validatedName)
                .locations("classpath:db/migration/tenant")
                .baselineOnMigrate(true)
                .load();

        flyway.migrate();
        System.out.println("Tenant schema created and migrated: " + validatedName);
    }

    private String sanitizeSchemaName(String schemaName) {
        if (schemaName == null || schemaName.isBlank()) {
            throw new IllegalArgumentException("Schema name cannot be empty");
        }

        String sanitized = schemaName.trim().toLowerCase().replaceAll("[^a-z0-9_]", "_");

        if (!sanitized.matches("^[a-z_][a-z0-9_]*$")) {
            sanitized = "tenant_" + Math.abs(sanitized.hashCode());
        }

        return sanitized;
    }
}
