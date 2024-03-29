package com.github.proditos.connection.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;

/**
 * @author Vladislav Konovalov
 */
public class MariaDbSingletonConnection extends SingletonConnectionImpl {
    private static final Logger LOGGER = LogManager.getLogger(MariaDbSingletonConnection.class);
    private static final MariaDbDataSource DATA_SOURCE = new MariaDbDataSource();
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/groceries";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "147896";

    static {
        try {
            DATA_SOURCE.setUrl(DB_URL);
            DATA_SOURCE.setUser(DB_USERNAME);
            DATA_SOURCE.setPassword(DB_PASSWORD);
        } catch (SQLException e) {
            LOGGER.error("An error occurred while trying to configure the MariaDB database", e);
        }
    }

    public MariaDbSingletonConnection() {
        super(DATA_SOURCE);
    }
}
