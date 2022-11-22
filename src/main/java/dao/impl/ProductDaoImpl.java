package dao.impl;

import dao.api.ProductDao;
import entity.Product;
import exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
public class ProductDaoImpl implements ProductDao {
    private final Connection connection;

    public ProductDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long save(Product product) {
        if (product == null) {
            throw new DaoException("An error occurred while saving a product, product is null");
        }
        String query = "INSERT INTO products (name, price) VALUES(?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            int affectedRows = statement.executeUpdate();
            Optional<Product> added = findLastBy(product.getName(), product.getPrice());
            if (affectedRows == 0 || !added.isPresent()) {
                throw new DaoException("An error occurred while saving a product, no rows affected");
            } else {
                return added.get().getId();
            }
        } catch (SQLException e) {
            throw new DaoException("An error occurred while saving a product", e);
        }
    }

    @Override
    public Optional<Product> findLastBy(String name, double price) {
        Optional<Product> optional = Optional.empty();
        if (name == null) {
            return optional;
        }
        String query = "SELECT product_id FROM products WHERE name=? AND price=? ORDER BY product_id DESC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setDouble(2, price);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("product_id");
                    optional = Optional.of(new Product(id, name, price));
                }
            }
        } catch (SQLException e) {
            String message = "An error occurred while receiving a product by name='" + name +
                    "' and price='" + price + "'";
            throw new DaoException(message, e);
        }
        return optional;
    }
}
