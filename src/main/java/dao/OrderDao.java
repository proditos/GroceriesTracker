package dao;

import entity.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
public class OrderDao implements Dao<Order> {
    private final Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Order order) {
        if (order == null) return;
        String query = "INSERT into orders (date_time) VALUES(?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            statement.setString(1, order.getDateTime().format(formatter));
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (statement != null) statement.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public Optional<Order> findWithoutProductsBy(LocalDateTime dateTime) {
        Optional<Order> optional = Optional.empty();
        if (dateTime == null) return optional;
        String query = "SELECT order_id FROM orders WHERE date_time = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            statement.setString(1, dateTime.format(formatter));
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong("order_id");
                optional = Optional.of(new Order(id, dateTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (statement != null) statement.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return optional;
    }
}
