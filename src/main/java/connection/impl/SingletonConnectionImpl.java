package connection.impl;

import connection.api.SingletonConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Vladislav Konovalov
 */
public class SingletonConnectionImpl implements SingletonConnection {
    private static final Logger LOGGER = LogManager.getLogger(SingletonConnectionImpl.class);
    private final DataSource dataSource;
    private Connection connection;

    SingletonConnectionImpl(DataSource dataSource) {
        assert dataSource != null : "Data source is null";
        this.dataSource = dataSource;
    }

    @Override
    public Connection getInstance() {
        lazyInit();
        return connection;
    }

    @Override
    public void close() {
        lazyInit();
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("An error occurred while trying to close the connection to the database", e);
        }
    }

    @Override
    public void commit() {
        lazyInit();
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("An error occurred while trying to commit a transaction to the database", e);
        }
    }

    @Override
    public void rollback() {
        lazyInit();
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("An error occurred while trying to rollback a transaction to the database", e);
        }
    }

    private void lazyInit() {
        if (connection == null) {
            try {
                connection = dataSource.getConnection();
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("An error occurred while trying to connect to the database", e);
            }
        }
    }
}
