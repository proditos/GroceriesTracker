package dao.api;

import entity.Receipt;
import exception.DaoException;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * The interface that allows the user to interact with the data in the receipts table in the database.
 * Interactions occur with the help of the entity class {@code Receipt}.
 *
 * @see Receipt
 *
 * @author Vladislav Konovalov
 */
public interface ReceiptDao {
    /**
     * Saves the receipt to the database and returns the database generated id.
     *
     * @param receipt the receipt to save.
     * @return the database generated id.
     * @throws DaoException if the receipt is null or any errors occurred while saving the receipt.
     */
    long save(Receipt receipt);

    /**
     * Searches for the receipt by the specified parameters.
     * If several are found, returns the last one added.
     *
     * @param sellerName the seller's name on the receipt.
     * @param dateTime date and time on the receipt.
     * @return {@code Optional.empty()} if the seller's name or datetime is null, or the receipt was not found.
     * In all other cases, returns {@code Optional.of(Receipt)}.
     * @throws DaoException if there are any errors when searching for the receipt.
     */
    Optional<Receipt> findLastBy(String sellerName, LocalDateTime dateTime);
}
