package service.impl;

import connection.api.SingletonConnection;
import dao.api.ProductDao;
import dao.api.ReceiptDao;
import dao.api.ReceiptProductDao;
import dto.ProductDto;
import dto.ReceiptDto;
import entity.Product;
import entity.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.api.ReceiptService;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

/**
 * @author Vladislav Konovalov
 */
@ExtendWith(MockitoExtension.class)
class ReceiptServiceImplTest {
    ReceiptService receiptService;
    @Mock
    SingletonConnection mockSingletonConnection;
    @Mock
    ReceiptDao mockReceiptDao;
    @Mock
    ReceiptProductDao mockReceiptProductDao;
    @Mock
    ProductDao mockProductDao;

    @BeforeEach
    void setUp() {
        receiptService = new ReceiptServiceImpl(
                mockSingletonConnection,
                mockReceiptDao,
                mockReceiptProductDao,
                mockProductDao);
    }

    @Test
    void testAdd_AddedWithNewProduct() {
        String sellerName = "SellerName";
        LocalDateTime dateTime = LocalDateTime.now();
        ReceiptDto receiptDto = new ReceiptDto(sellerName, dateTime);
        String productName = "Name";
        double price = 1.0;
        receiptDto.addProduct(new ProductDto(productName, price), 1.0);
        when(mockReceiptDao.findLast(any()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(new Receipt(1L, sellerName, dateTime)));
        when(mockProductDao.findLast(any()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(new Product(1L, productName, price)));

        receiptService.add(receiptDto);

        verify(mockReceiptDao, times(1)).save(any());
        verify(mockProductDao, times(1)).save(any());
        verify(mockSingletonConnection, times(1)).commit();
    }

    @Test
    void testAdd_AddedWithExistingProduct() {
        String sellerName = "SellerName";
        LocalDateTime dateTime = LocalDateTime.now();
        ReceiptDto receiptDto = new ReceiptDto(sellerName, dateTime);
        String productName = "Name";
        double price = 1.0;
        receiptDto.addProduct(new ProductDto(productName, price), 1.0);
        when(mockReceiptDao.findLast(any()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(new Receipt(1L, sellerName, dateTime)));
        when(mockProductDao.findLast(any())).thenReturn(Optional.of(new Product(1L, productName, price)));

        receiptService.add(receiptDto);

        verify(mockReceiptDao, times(1)).save(any());
        verify(mockProductDao, times(0)).save(any());
        verify(mockSingletonConnection, times(1)).commit();
    }

    @Test
    void testAdd_ReceiptDtoIsNull() {
        String message = "Should not throw an exception";
        assertDoesNotThrow(() -> receiptService.add(null), message);
        verifyNoInteractions(mockReceiptDao);
        verifyNoInteractions(mockReceiptProductDao);
        verifyNoInteractions(mockProductDao);
    }

    @Test
    void testAdd_ReceiptExists() {
        ReceiptDto receiptDto = new ReceiptDto("SellerName", LocalDateTime.now());
        when(mockReceiptDao.findLast(any())).thenReturn(Optional.of(new Receipt("SellerName", LocalDateTime.now())));

        receiptService.add(receiptDto);

        verifyNoInteractions(mockSingletonConnection);
        verifyNoInteractions(mockReceiptProductDao);
        verifyNoInteractions(mockProductDao);
    }

    @Test
    void testAdd_ReceiptSavedButNotFound() {
        ReceiptDto receiptDto = new ReceiptDto("SellerName", LocalDateTime.now());
        when(mockReceiptDao.findLast(any())).thenReturn(Optional.empty());

        String message = "Should not throw an exception";
        assertDoesNotThrow(() -> receiptService.add(receiptDto), message);
        verify(mockReceiptDao, times(1)).save(any());
        verify(mockReceiptDao, times(2)).findLast(any());
        verify(mockSingletonConnection, times(1)).rollback();
    }

    @Test
    void testAdd_ProductSavedButNotFound() {
        String sellerName = "SellerName";
        LocalDateTime dateTime = LocalDateTime.now();
        ReceiptDto receiptDto = new ReceiptDto(sellerName, dateTime);
        receiptDto.addProduct(new ProductDto("Name", 1.0), 1.0);
        when(mockReceiptDao.findLast(any()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(new Receipt(1L, sellerName, dateTime)));
        when(mockProductDao.findLast(any())).thenReturn(Optional.empty());

        String message = "Should not throw an exception";
        assertDoesNotThrow(() -> receiptService.add(receiptDto), message);
        verify(mockProductDao, times(1)).save(any());
        verify(mockProductDao, times(2)).findLast(any());
        verify(mockSingletonConnection, times(1)).rollback();
    }
}