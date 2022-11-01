package dao;

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
public class ProductDao implements Dao<Product> {
    private final Connection connection;

    public ProductDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Product product) {
        if (product == null) return;
        String query = "INSERT INTO products (name, price, price_per_kg) VALUES(?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.isPricePerKg() ? 1 : 0);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException("An error occurred while saving a product", e);
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (statement != null) statement.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public Optional<Product> findBy(String name, double price, boolean pricePerKg) {
        Optional<Product> optional = Optional.empty();
        if (name == null) return optional;
        String query = "SELECT product_id FROM products WHERE name = ? AND price = ? AND price_per_kg = ? ORDER BY product_id DESC";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setDouble(2, price);
            statement.setInt(3, pricePerKg ? 1 : 0);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong("product_id");
                optional = Optional.of(new Product(id, name, price, pricePerKg));
            }
        } catch (SQLException e) {
            String message = "An error occurred while getting a product by name='" + name + "', " +
                    "price='" + price + "' and price_per_kg='" + pricePerKg + "'";
            throw new DaoException(message, e);
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (statement != null) statement.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return optional;
    }
}
