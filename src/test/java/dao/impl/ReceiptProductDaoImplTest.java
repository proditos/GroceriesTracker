package dao.impl;

import dao.api.ReceiptProductDao;
import entity.ReceiptProduct;
import exception.TechnicalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

/**
 * @author Vladislav Konovalov
 */
@ExtendWith(MockitoExtension.class)
class ReceiptProductDaoImplTest {
    ReceiptProductDao receiptProductDao;
    @Mock
    Connection mockConnection;
    @Mock
    PreparedStatement mockPreparedStatement;

    @BeforeEach
    void setUp() {
        receiptProductDao = new ReceiptProductDaoImpl(mockConnection);
    }

    @Test
    void testSave_ReceiptProductIsSaved() throws SQLException {
        ReceiptProduct receiptProduct = new ReceiptProduct(1L, 1L, 1.0);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        receiptProductDao.save(receiptProduct);

        verify(mockConnection, times(1)).prepareStatement(contains("?"));
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).close();
    }

    @Test
    void testSave_ReceiptProductIsNotSaved() throws SQLException {
        ReceiptProduct receiptProduct = new ReceiptProduct(1L, 1L, 1.0);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        String message = "Should throw TechnicalException if the receiptProduct is not saved";
        assertThrows(TechnicalException.class, () -> receiptProductDao.save(receiptProduct), message);
        verify(mockConnection, times(1)).prepareStatement(contains("?"));
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).close();
    }

    @Test
    void testSave_ReceiptProductIsNull() {
        String message = "Should throw a TechnicalException if the receiptProduct is null";
        assertThrows(TechnicalException.class, () -> receiptProductDao.save(null), message);
    }

    @Test
    void testSave_SQLException() throws SQLException {
        ReceiptProduct receiptProduct = new ReceiptProduct(1L, 1L, 1.0);
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);

        String message = "Should throw a TechnicalException if SQLException occurred";
        assertThrows(TechnicalException.class, () -> receiptProductDao.save(receiptProduct), message);
    }
}