package dao;

import entity.Order;
import javax.sql.DataSource;
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
    private final DataSource dataSource;

    public OrderDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Order order) {
        if (order == null) return;
        String query = "INSERT into orders (delivery_datetime) VALUES(?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            statement.setString(1, order.getDeliveryDateTime().format(formatter));
            statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Order> findBy(LocalDateTime deliveryDateTime) {
        Optional<Order> optional = Optional.empty();
        String query = "SELECT order_id FROM orders WHERE delivery_datetime = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            statement.setString(1, deliveryDateTime.format(formatter));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("order_id");
                optional = Optional.of(new Order(id, deliveryDateTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optional;
    }
}
