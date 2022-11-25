package service.api;

import dto.ReceiptDto;

/**
 * The service for working with receipts.
 * Uses receipt DTO as input data.
 *
 * @see ReceiptDto
 *
 * @author Vladislav Konovalov
 */
public interface ReceiptService {
    /**
     * Adds the receipt and all products with their quantity from it to the database.
     * If the receipt or product is already in the database, they will not be added again.
     *
     * @param receiptDto the receipt DTO.
     */
    void add(ReceiptDto receiptDto);
}
