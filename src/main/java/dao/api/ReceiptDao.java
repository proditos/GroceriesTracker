package dao.api;

import entity.Receipt;
import exception.DaoException;
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
     * Saves the receipt to the database.
     *
     * @param receipt the receipt to save.
     * @throws DaoException if the receipt is null or any errors occurred while saving the receipt.
     */
    void save(Receipt receipt);

    /**
     * Searches for the receipt by the all its fields except id.
     * If several are found, returns the last one added.
     *
     * @param receipt the receipt to find.
     * @return {@code Optional.empty()} if the receipt was not found.
     * In all other cases, returns {@code Optional.of(Receipt)}.
     * @throws DaoException if the receipt is null or any errors occurred while searching for the receipt.
     */
    Optional<Receipt> findLast(Receipt receipt);
}
