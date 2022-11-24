package dao.api;

import entity.Product;
import exception.DaoException;
import java.util.Optional;

/**
 * The interface that allows the user to interact with the data in the products table in the database.
 * Interactions occur with the help of the entity class {@code Product}.
 *
 * @see Product
 *
 * @author Vladislav Konovalov
 */
public interface ProductDao {
    /**
     * Saves the product to the database.
     *
     * @param product the product to save.
     * @throws DaoException if the product is null or any errors occurred while saving the product.
     */
    void save(Product product);

    /**
     * Searches for the product by the all its fields except id.
     * If several are found, returns the last one added.
     *
     * @param product the product to find.
     * @return {@code Optional.empty()} if the product was not found.
     * In all other cases, returns {@code Optional.of(Product)}.
     * @throws DaoException if the product is null or any errors occurred while searching for the product.
     */
    Optional<Product> findLast(Product product);
}
