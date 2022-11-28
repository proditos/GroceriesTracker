package connection.api;

import java.sql.Connection;

/**
 * The interface that is used to wrap over the database {@code Connection}.
 * The class implementing it must be a Singleton.
 * Has a standard implementation {@code SingletonConnectionImpl}.
 *
 * @see Connection
 *
 * @author Vladislav Konovalov
 */
public interface SingletonConnection {
    /**
     * If the connection is not established, it establishes the connection and then returns it.
     * If the connection is established, it is returned.
     *
     * @return null if any errors occurred.
     * In all other cases, returns the singleton connection.
     */
    Connection getInstance();

    /**
     * Closes the connection.
     */
    void close();

    /**
     * Commits a transaction.
     */
    void commit();

    /**
     * Rolls back a transaction.
     */
    void rollback();
}
