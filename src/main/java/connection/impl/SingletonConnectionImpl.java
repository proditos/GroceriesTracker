package connection.impl;

import connection.api.SingletonConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Standard implementation required for unit tests.
 * To define your connection, inherit this class and
 * explicitly specify the data source in the constructor.
 *
 * @author Vladislav Konovalov
 */
class SingletonConnectionImpl implements SingletonConnection {
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
                connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            } catch (SQLException e) {
                LOGGER.error("An error occurred while trying to connect to the database", e);
            }
        }
    }
}
