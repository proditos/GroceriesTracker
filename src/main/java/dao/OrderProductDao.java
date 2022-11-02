package dao;

import entity.OrderProduct;
import exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Vladislav Konovalov
 */
public class OrderProductDao extends AbstractDao<OrderProduct> {
    public OrderProductDao(Connection connection) {
        super(connection);
    }

    @Override
    public void save(OrderProduct orderProduct) {
        if (orderProduct == null) return;
        String query = "INSERT INTO orders_products (order_id, product_id, quantity) VALUES(?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderProduct.getOrderId());
            statement.setLong(2, orderProduct.getProductId());
            statement.setDouble(3, orderProduct.getQuantity());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0)
                throw new SQLException("An error occurred while saving an order_product, no rows affected");
        } catch (SQLException e) {
            throw new DaoException("An error occurred while saving an order_product", e);
        }
    }
}
