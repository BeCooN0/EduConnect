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
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS + schemaName");
        Flyway flyway = Flyway.configure()
                .locations("db/migration/tenant")
                .dataSource(dataSource)
                .schemas(schemaName)
                .load();
        flyway.migrate();
    }

}
