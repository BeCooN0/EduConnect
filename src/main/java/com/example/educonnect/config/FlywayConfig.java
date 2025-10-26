package com.example.educonnect.config;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityConfig;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class FlywayConfig {
    private final DataSource dataSource;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure()
                .locations("db/migration/public")
                .dataSource(dataSource)
                .schemas("public")
                .baselineOnMigrate(true)
                .load();
    }
}
