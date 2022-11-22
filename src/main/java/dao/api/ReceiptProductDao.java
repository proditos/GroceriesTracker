package dao.api;

import entity.ReceiptProduct;
import exception.DaoException;
import java.util.Optional;

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
     * Saves the entry linking the product to the receipt to the database and returns the database generated id.
     *
     * @param receiptProduct the entry linking the product to the receipt.
     * @return the database generated id.
     * @throws DaoException if the receiptProduct is null or any errors occurred while saving the entry.
     */
    long save(ReceiptProduct receiptProduct);

    /**
     * Searches for the entry linking the product to the receipt by the specified parameters.
     * If several are found, returns the last one added.
     *
     * @param receiptId the receipt id.
     * @param productId the product id.
     * @param quantity the product quantity.
     * @return {@code Optional.empty()} if the entry was not found.
     * In all other cases, returns {@code Optional.of(ReceiptProduct)}.
     * @throws DaoException if there are any errors when searching for the receipt.
     */
    Optional<ReceiptProduct> findLastBy(long receiptId, long productId, double quantity);
}
