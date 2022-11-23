package dao.impl;

import dao.api.ProductDao;
import entity.Product;
import exception.DaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author Vladislav Konovalov
 */
@ExtendWith(MockitoExtension.class)
class ProductDaoImplTest {
    ProductDao productDao;
    @Mock
    Connection mockConnection;
    @Mock
    PreparedStatement mockPreparedStatement;
    @Mock
    ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        productDao = new ProductDaoImpl(mockConnection);
    }

    @Test
    void testFindLastBy_ProductFound() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);

        Optional<Product> actual = productDao.findLastBy("Name", 1.0);

        verify(mockConnection, times(1)).prepareStatement(contains("?"));
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getLong("product_id");
        verify(mockPreparedStatement, times(1)).close();
        verify(mockResultSet, times(1)).close();
        String message = "Should return the product if it's found";
        assertTrue(actual.isPresent(), message);
    }

    @Test
    void testFindLastBy_ProductNotFound() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Optional<Product> actual = productDao.findLastBy("Name", 1.0);

        verify(mockConnection, times(1)).prepareStatement(contains("?"));
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getLong(anyString());
        verify(mockPreparedStatement, times(1)).close();
        verify(mockResultSet, times(1)).close();
        String message = "Should return Optional.empty() if the product not found";
        assertEquals(Optional.empty(), actual, message);
    }

    @Test
    void testFindLastBy_NameIsNull() {
        Optional<Product> actual = productDao.findLastBy(null, 1.0);

        verifyNoInteractions(mockConnection);
        verifyNoInteractions(mockPreparedStatement);
        verifyNoInteractions(mockResultSet);
        String message = "Should return Optional.empty() if the product name is null";
        assertEquals(Optional.empty(), actual, message);
    }

    @Test
    void testFindLastBy_DaoException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);

        String message = "Should throw DaoException if SQLException occurred";
        assertThrows(DaoException.class, () -> productDao.findLastBy("Name", 1.0), message);
    }
}
