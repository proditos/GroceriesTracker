package dao;

import entity.Product;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
public class ProductDao {
    private final DataSource dataSource;

    public ProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Product product) {
        if (product == null) return;
        String query = "INSERT INTO products (name, price, price_per_kg) VALUES(?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.isPricePerKg() ? 1 : 0);
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Product> findBy(String name, double price, boolean pricePerKg) {
        Optional<Product> optional = Optional.empty();
        String query = "SELECT product_id FROM products WHERE name = ? AND price = ? AND price_per_kg = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setDouble(2, price);
            statement.setInt(3, pricePerKg ? 1 : 0);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("product_id");
                optional = Optional.of(new Product(id, name, price, pricePerKg));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optional;
    }
}
