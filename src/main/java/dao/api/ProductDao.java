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
     * Saves the product to the database and returns the database generated id.
     *
     * @param product the product to save.
     * @return the database generated id.
     * @throws DaoException if the product is null or any errors occurred while saving the product.
     */
    long save(Product product);

    /**
     * Searches for the product by the specified parameters.
     * If several are found, returns the last one added.
     *
     * @param name the product name.
     * @param price the product price.
     * @return {@code Optional.empty()} if the product name is null or the product was not found.
     * In all other cases, returns {@code Optional.of(Product)}.
     * @throws DaoException if there are any errors when searching for the product.
     */
    Optional<Product> findLastBy(String name, double price);
}
