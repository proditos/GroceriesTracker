package dao;

import entity.OrderProduct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Vladislav Konovalov
 */
public class OrderProductDao {
    private final DataSource dataSource;

    public OrderProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(OrderProduct orderProduct) {
        String query = "INSERT INTO orders_products (order_id, product_id, quantity) VALUES(?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderProduct.getOrderId());
            statement.setLong(2, orderProduct.getProductId());
            statement.setDouble(3, orderProduct.getQuantity());
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
