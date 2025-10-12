package com.example.educonnect.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.connections.spi.DatabaseConnectionInfo;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TenantConnectionProvider implements MultiTenantConnectionProvider<String> {
    private final DataSource dataSource;
    @Override
    public Connection getAnyConnection() throws SQLException {
            return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String s) throws SQLException {
        final Connection anyConnection = getAnyConnection();
        anyConnection.setSchema(s);
        return anyConnection;
    }

    @Override
    public void releaseConnection(String s, Connection connection) throws SQLException {
        try {
            connection.setSchema("public");
        } catch (SQLException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e);
        }finally {
            connection.close();
        }
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public DatabaseConnectionInfo getDatabaseConnectionInfo(Dialect dialect) {
        return MultiTenantConnectionProvider.super.getDatabaseConnectionInfo(dialect);
    }

    @Override
    public boolean isUnwrappableAs(@org.checkerframework.checker.nullness.qual.UnknownKeyFor @org.checkerframework.checker.nullness.qual.NonNull @org.checkerframework.checker.initialization.qual.Initialized Class<@org.checkerframework.checker.nullness.qual.UnknownKeyFor @org.checkerframework.checker.nullness.qual.NonNull @org.checkerframework.checker.initialization.qual.Initialized ?> aClass) {
        return false;
    }

    @Override
    public <T> T unwrap(@org.checkerframework.checker.nullness.qual.UnknownKeyFor @org.checkerframework.checker.nullness.qual.NonNull @org.checkerframework.checker.initialization.qual.Initialized Class<T> aClass) {
        return null;
    }
}
