package dao.api;

import entity.ReceiptProduct;
import exception.TechnicalException;

/**
 * The interface that allows the user to interact with the data in the receipts_products linking table in the database.
 * Interactions occur with the help of the entity class {@code ReceiptProduct}.
 *
 * @see ReceiptProduct
 *
 * @author Vladislav Konovalov
 */
public interface ReceiptProductDao {
    /**
     * Saves the entry linking the product to the receipt to the database.
     *
     * @param receiptProduct the entry linking the product to the receipt to save.
     * @throws TechnicalException if the receiptProduct is null or any errors occurred while saving the entry.
     */
    void save(ReceiptProduct receiptProduct);
}
