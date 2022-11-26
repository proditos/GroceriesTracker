package connection.impl;

import connection.api.SingletonConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Vladislav Konovalov
 */
@ExtendWith(MockitoExtension.class)
class SingletonConnectionImplTest {
    SingletonConnection singletonConnection;
    @Mock
    DataSource mockDataSource;
    @Mock
    Connection mockConnection;

    @BeforeEach
    void setUp() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        singletonConnection = new SingletonConnectionImpl(mockDataSource);
    }

    @Test
    void testGetInstance_LazyInit() throws SQLException {
        Connection actual = singletonConnection.getInstance();

        String message = "Should not return null";
        assertNotNull(actual, message);
        verify(mockDataSource, times(1)).getConnection();
        verify(mockConnection, times(1)).setAutoCommit(false);
        verify(mockConnection, times(0)).close();
    }

    @Test
    void testGetInstance_ReturnConnection() {
        Connection expected = singletonConnection.getInstance();

        Connection actual = singletonConnection.getInstance();

        String message = "The connection should be the same";
        assertSame(expected, actual, message);
    }

    @Test
    void testGetInstance_LazyInitSQLException() throws SQLException {
        when(mockDataSource.getConnection()).thenThrow(SQLException.class);

        Connection actual = singletonConnection.getInstance();

        String message = "Should return null if SQLException occurred";
        assertNull(actual, message);
    }

    @Test
    void testClose() throws SQLException {
        singletonConnection.close();

        verify(mockConnection, times(1)).close();
    }

    @Test
    void testClose_SQLException() throws SQLException {
        doThrow(SQLException.class).when(mockConnection).close();

        String message = "Should not throw an exception";
        assertDoesNotThrow(() -> singletonConnection.close(), message);
        verify(mockConnection, times(1)).close();
    }

    @Test
    void testCommit() throws SQLException {
        singletonConnection.commit();

        verify(mockConnection, times(1)).commit();
    }

    @Test
    void testCommit_SQLException() throws SQLException {
        doThrow(SQLException.class).when(mockConnection).commit();

        String message = "Should not throw an exception";
        assertDoesNotThrow(() -> singletonConnection.commit(), message);
        verify(mockConnection, times(1)).commit();
    }

    @Test
    void testRollback() throws SQLException {
        singletonConnection.rollback();

        verify(mockConnection, times(1)).rollback();
    }

    @Test
    void testRollback_SQLException() throws SQLException {
        doThrow(SQLException.class).when(mockConnection).rollback();

        String message = "Should not throw an exception";
        assertDoesNotThrow(() -> singletonConnection.rollback(), message);
        verify(mockConnection, times(1)).rollback();
    }
}