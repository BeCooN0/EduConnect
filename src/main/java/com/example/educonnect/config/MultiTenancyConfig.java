package com.example.educonnect.config;

import com.example.educonnect.security.SchemaMultiTenantConnectionProvider;
import com.example.educonnect.security.TenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MultiTenancyConfig {

    @Bean
    public MultiTenantConnectionProvider multiTenantConnectionProvider(DataSource dataSource) {
        return new SchemaMultiTenantConnectionProvider(dataSource);
    }

    @Bean
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
        return new TenantIdentifierResolver();
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(
            MultiTenantConnectionProvider multiTenantConnectionProvider,
            CurrentTenantIdentifierResolver currentTenantIdentifierResolver
    ) {
        return (hibernateProps) -> {
            hibernateProps.put("hibernate.multiTenancy", "SCHEMA");
            hibernateProps.put("hibernate.multi_tenant_connection_provider", multiTenantConnectionProvider);
            hibernateProps.put("hibernate.tenant_identifier_resolver", currentTenantIdentifierResolver);
        };
    }
}
