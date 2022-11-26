package dao.impl;

import dao.api.ProductDao;
import entity.Product;
import exception.TechnicalException;
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
    void testFindLast_ProductFound() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);

        Optional<Product> actual = productDao.findLast(new Product("Name", 1.0));

        String message = "Should return the product if it's found";
        assertTrue(actual.isPresent(), message);
        verify(mockConnection, times(1)).prepareStatement(contains("?"));
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getLong("product_id");
        verify(mockPreparedStatement, times(1)).close();
        verify(mockResultSet, times(1)).close();
    }

    @Test
    void testFindLast_ProductNotFound() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Optional<Product> actual = productDao.findLast(new Product("Name", 1.0));

        String message = "Should return Optional.empty() if the product is not found";
        assertEquals(Optional.empty(), actual, message);
        verify(mockConnection, times(1)).prepareStatement(contains("?"));
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getLong(anyString());
        verify(mockPreparedStatement, times(1)).close();
        verify(mockResultSet, times(1)).close();
    }

    @Test
    void testFindLast_ProductIsNull() {
        String message = "Should throw a TechnicalException if the product is null";
        assertThrows(TechnicalException.class, () -> productDao.findLast(null), message);
    }

    @Test
    void testFindLast_SQLException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);
        Product product = new Product("Name", 1.0);

        String message = "Should throw a TechnicalException if SQLException occurred";
        assertThrows(TechnicalException.class, () -> productDao.findLast(product), message);
    }

    @Test
    void testSave_ProductIsSaved() throws SQLException {
        Product product = new Product("Name", 1.0);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        productDao.save(product);

        verify(mockConnection, times(1)).prepareStatement(contains("?"));
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).close();
    }

    @Test
    void testSave_ProductIsNotSaved() throws SQLException {
        Product product = new Product("Name", 1.0);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        String message = "Should throw TechnicalException if the product is not saved";
        assertThrows(TechnicalException.class, () -> productDao.save(product), message);
        verify(mockConnection, times(1)).prepareStatement(contains("?"));
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).close();
    }

    @Test
    void testSave_ProductIsNull() {
        String message = "Should throw a TechnicalException if the product is null";
        assertThrows(TechnicalException.class, () -> productDao.save(null), message);
    }

    @Test
    void testSave_SQLException() throws SQLException {
        Product product = new Product("Name", 1.0);
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);

        String message = "Should throw a TechnicalException if SQLException occurred";
        assertThrows(TechnicalException.class, () -> productDao.save(product), message);
    }
}
