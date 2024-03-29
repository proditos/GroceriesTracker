package com.github.proditos.dao.impl;

import com.github.proditos.dao.api.ProductDao;
import com.github.proditos.exception.TechnicalException;
import com.github.proditos.entity.Product;

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
    public void save(Product product) {
        if (product == null) {
            throw new TechnicalException("An error occurred while saving the product, the product is null");
        }
        String query = "INSERT INTO products (name, price) VALUES(?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            int affectedRows = statement.executeUpdate();
            assert affectedRows == 1 : "The number of affected rows is not equal to 1";
        } catch (SQLException e) {
            String message = "An error occurred while saving the " + product;
            throw new TechnicalException(message, e);
        }
    }

    @Override
    public Optional<Product> findLast(Product product) {
        Optional<Product> optional = Optional.empty();
        if (product == null) {
            throw new TechnicalException("An error occurred while searching for the product, the product is null");
        }
        String query = "SELECT product_id FROM products WHERE name=? AND price=? ORDER BY product_id DESC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("product_id");
                    optional = Optional.of(new Product(id, product.getName(), product.getPrice()));
                }
            }
        } catch (SQLException e) {
            String message = "An error occurred while searching for the " + product;
            throw new TechnicalException(message, e);
        }
        return optional;
    }
}
