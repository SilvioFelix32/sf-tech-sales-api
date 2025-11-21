package com.sftech.sales.application.service;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class DatabaseConnectionService {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionService.class);
    
    private final DataSource dataSource;

    public DatabaseConnectionService(DataSource dataSource) {
        this.dataSource = dataSource;
        logger.info("DatabaseConnectionService initialized with DataSource: {}", 
            dataSource.getClass().getSimpleName());
    }

    public Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        logger.debug("Connection obtained from pool - Active: {}, Idle: {}, Total: {}", 
            getActiveConnections(), getIdleConnections(), getTotalConnections());
        return connection;
    }

    public int getActiveConnections() {
        if (dataSource instanceof HikariDataSource) {
            HikariPoolMXBean pool = ((HikariDataSource) dataSource).getHikariPoolMXBean();
            return pool != null ? pool.getActiveConnections() : 0;
        }
        return 0;
    }

    public int getIdleConnections() {
        if (dataSource instanceof HikariDataSource) {
            HikariPoolMXBean pool = ((HikariDataSource) dataSource).getHikariPoolMXBean();
            return pool != null ? pool.getIdleConnections() : 0;
        }
        return 0;
    }

    public int getTotalConnections() {
        if (dataSource instanceof HikariDataSource) {
            HikariPoolMXBean pool = ((HikariDataSource) dataSource).getHikariPoolMXBean();
            return pool != null ? pool.getTotalConnections() : 0;
        }
        return 0;
    }

    public int getMaximumPoolSize() {
        if (dataSource instanceof HikariDataSource) {
            return ((HikariDataSource) dataSource).getMaximumPoolSize();
        }
        return 0;
    }

    public void logConnectionStats() {
        if (dataSource instanceof HikariDataSource) {
            HikariPoolMXBean pool = ((HikariDataSource) dataSource).getHikariPoolMXBean();
            if (pool != null) {
                logger.info("Connection Pool Statistics - " +
                    "Active: {}, Idle: {}, Total: {}, Maximum: {}, Threads Awaiting: {}",
                    pool.getActiveConnections(),
                    pool.getIdleConnections(),
                    pool.getTotalConnections(),
                    getMaximumPoolSize(),
                    pool.getThreadsAwaitingConnection());
            }
        }
    }

    public boolean isConnectionHealthy() {
        try (Connection connection = getConnection()) {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            logger.error("Error checking connection health", e);
            return false;
        }
    }
}

