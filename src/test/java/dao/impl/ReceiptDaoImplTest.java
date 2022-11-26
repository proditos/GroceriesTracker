package dao.impl;

import dao.api.ReceiptDao;
import entity.Receipt;
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
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;

/**
 * @author Vladislav Konovalov
 */
@ExtendWith(MockitoExtension.class)
class ReceiptDaoImplTest {
    ReceiptDao receiptDao;
    @Mock
    Connection mockConnection;
    @Mock
    PreparedStatement mockPreparedStatement;
    @Mock
    ResultSet mockResultSet;

    @BeforeEach
    void setUp() {
        receiptDao = new ReceiptDaoImpl(mockConnection);
    }

    @Test
    void testFindLast_ReceiptFound() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);

        Optional<Receipt> actual = receiptDao.findLast(new Receipt("SellerName", LocalDateTime.now()));

        String message = "Should return the receipt if it's found";
        assertTrue(actual.isPresent(), message);
        verify(mockConnection, times(1)).prepareStatement(contains("?"));
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getLong("receipt_id");
        verify(mockPreparedStatement, times(1)).close();
        verify(mockResultSet, times(1)).close();
    }

    @Test
    void testFindLast_ReceiptNotFound() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        Optional<Receipt> actual = receiptDao.findLast(new Receipt("SellerName", LocalDateTime.now()));

        String message = "Should return Optional.empty() if the receipt is not found";
        assertEquals(Optional.empty(), actual, message);
        verify(mockConnection, times(1)).prepareStatement(contains("?"));
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(0)).getLong(anyString());
        verify(mockPreparedStatement, times(1)).close();
        verify(mockResultSet, times(1)).close();
    }

    @Test
    void testFindLast_ReceiptIsNull() {
        String message = "Should throw a TechnicalException if the receipt is null";
        assertThrows(TechnicalException.class, () -> receiptDao.findLast(null), message);
    }

    @Test
    void testFindLast_SQLException() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);
        Receipt receipt = new Receipt("SellerName", LocalDateTime.now());

        String message = "Should throw a TechnicalException if SQLException occurred";
        assertThrows(TechnicalException.class, () -> receiptDao.findLast(receipt), message);
    }

    @Test
    void testSave_ReceiptIsSaved() throws SQLException {
        Receipt receipt = new Receipt("SellerName", LocalDateTime.now());
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        receiptDao.save(receipt);

        verify(mockConnection, times(1)).prepareStatement(contains("?"));
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).close();
    }

    @Test
    void testSave_ReceiptIsNotSaved() throws SQLException {
        Receipt receipt = new Receipt("SellerName", LocalDateTime.now());
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        String message = "Should throw TechnicalException if the receipt is not saved";
        assertThrows(TechnicalException.class, () -> receiptDao.save(receipt), message);
        verify(mockConnection, times(1)).prepareStatement(contains("?"));
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).close();
    }

    @Test
    void testSave_ReceiptIsNull() {
        String message = "Should throw a TechnicalException if the receipt is null";
        assertThrows(TechnicalException.class, () -> receiptDao.save(null), message);
    }

    @Test
    void testSave_SQLException() throws SQLException {
        Receipt receipt = new Receipt("SellerName", LocalDateTime.now());
        when(mockConnection.prepareStatement(anyString())).thenThrow(SQLException.class);

        String message = "Should throw a TechnicalException if SQLException occurred";
        assertThrows(TechnicalException.class, () -> receiptDao.save(receipt), message);
    }
}