package mapper.impl;

import dto.ReceiptDto;
import entity.Receipt;
import mapper.api.Mapper;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vladislav Konovalov
 */
class ReceiptMapperTest {
    private final Mapper<ReceiptDto, Receipt> receiptMapper = new ReceiptMapper();

    @Test
    void testToEntity() {
        ReceiptDto receiptDto = new ReceiptDto("Seller name", LocalDateTime.now());

        Receipt receipt = receiptMapper.toEntity(receiptDto);

        String notNullMessage = "The entity should not be null";
        assertNotNull(receipt, notNullMessage);
        String sellerNameMessage = "The entity should have the same seller name";
        assertEquals(receiptDto.getSellerName(), receipt.getSellerName(), sellerNameMessage);
        String dateTimeMessage = "The entity should have the same date and time";
        assertEquals(receiptDto.getDateTime(), receipt.getDateTime(), dateTimeMessage);
    }

    @Test
    void testToEntity_Null() {
        Receipt receipt = receiptMapper.toEntity(null);

        String message = "Should return null when DTO is null";
        assertNull(receipt, message);
    }
}