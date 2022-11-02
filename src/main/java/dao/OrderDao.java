package dao;

import entity.Order;
import exception.DaoException;
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
public class OrderDao extends AbstractDao<Order> {
    public OrderDao(Connection connection) {
        super(connection);
    }

    @Override
    public void save(Order order) {
        if (order == null) return;
        String query = "INSERT into orders (date_time) VALUES(?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            statement.setString(1, order.getDateTime().format(formatter));
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0)
                throw new SQLException("An error occurred while saving an order, no rows affected");
        } catch (SQLException e) {
            throw new DaoException("An error occurred while saving an order", e);
        }
    }

    public Optional<Order> findWithoutProductsBy(LocalDateTime dateTime) {
        Optional<Order> optional = Optional.empty();
        if (dateTime == null) return optional;
        String query = "SELECT order_id FROM orders WHERE date_time = ? ORDER BY order_id DESC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            statement.setString(1, dateTime.format(formatter));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("order_id");
                    optional = Optional.of(new Order(id, dateTime));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("An error occurred while getting an order by datetime='" + dateTime + "'", e);
        }
        return optional;
    }
}
