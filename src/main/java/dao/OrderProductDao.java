package dao;

import entity.OrderProduct;
import exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vladislav Konovalov
 */
public class OrderProductDao implements Dao<OrderProduct> {
    private final Connection connection;

    public OrderProductDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(OrderProduct orderProduct) {
        if (orderProduct == null) return;
        String query = "INSERT INTO orders_products (order_id, product_id, quantity) VALUES(?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setLong(1, orderProduct.getOrderId());
            statement.setLong(2, orderProduct.getProductId());
            statement.setDouble(3, orderProduct.getQuantity());
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException("An error occurred while saving an order_product", e);
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (statement != null) statement.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
