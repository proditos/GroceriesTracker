package com.github.proditos.service.api;

import com.github.proditos.dto.ReceiptDto;

/**
 * The service for working with receipts.
 * Uses receipt DTO as input data.
 *
 * @author Vladislav Konovalov
 * @see ReceiptDto
 */
public interface ReceiptService {
    /**
     * Adds the receipt and all products with their quantity from it to the database.
     * If the receipt or product is already in the database, they will not be added again.
     *
     * @param receiptDto the receipt DTO (not null).
     */
    void add(ReceiptDto receiptDto);
}
