package dao;

import entity.Receipt;
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
public class ReceiptDao extends AbstractDao<Receipt> {
    public ReceiptDao(Connection connection) {
        super(connection);
    }

    @Override
    public void save(Receipt receipt) {
        if (receipt == null) return;
        String query = "INSERT into receipts (seller_name, date_time) VALUES(?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            statement.setString(1, receipt.getSellerName());
            statement.setString(2, receipt.getDateTime().format(formatter));
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0)
                throw new SQLException("An error occurred while saving the receipt, no rows affected");
        } catch (SQLException e) {
            throw new DaoException("An error occurred while saving the receipt", e);
        }
    }

    public Optional<Receipt> findWithoutProductsBy(String sellerName, LocalDateTime dateTime) {
        Optional<Receipt> optional = Optional.empty();
        if (dateTime == null) return optional;
        String query = "SELECT receipt_id FROM receipts WHERE seller_name=? AND date_time=? ORDER BY receipt_id DESC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            statement.setString(1, sellerName);
            statement.setString(2, dateTime.format(formatter));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("receipt_id");
                    optional = Optional.of(new Receipt(id, sellerName, dateTime));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("An error occurred while receiving a receipt by seller_name='" + sellerName +
                    "' and datetime='" + dateTime + "'", e);
        }
        return optional;
    }
}
