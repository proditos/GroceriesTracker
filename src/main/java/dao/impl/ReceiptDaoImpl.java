package dao.impl;

import dao.api.ReceiptDao;
import entity.Receipt;
import exception.TechnicalException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
public class ReceiptDaoImpl implements ReceiptDao {
    private static final DateTimeFormatter DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Connection connection;

    public ReceiptDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Receipt receipt) {
        if (receipt == null) {
            throw new TechnicalException("An error occurred while saving the receipt, the receipt is null");
        }
        String query = "INSERT into receipts (seller_name, date_time) VALUES(?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, receipt.getSellerName());
            if (receipt.getDateTime() == null) {
                statement.setString(2, null);
            } else {
                statement.setString(2, receipt.getDateTime().format(DATE_TIME_PATTERN));
            }
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new TechnicalException("An error occurred while saving the receipt, no rows affected");
            }
        } catch (SQLException e) {
            String message = "An error occurred while saving the " + receipt;
            throw new TechnicalException(message, e);
        }
    }

    @Override
    public Optional<Receipt> findLast(Receipt receipt) {
        Optional<Receipt> optional = Optional.empty();
        if (receipt == null) {
            throw new TechnicalException("An error occurred while searching for the receipt, the receipt is null");
        }
        String query = "SELECT receipt_id FROM receipts WHERE seller_name=? AND date_time=? ORDER BY receipt_id DESC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, receipt.getSellerName());
            if (receipt.getDateTime() == null) {
                statement.setString(2, null);
            } else {
                statement.setString(2, receipt.getDateTime().format(DATE_TIME_PATTERN));
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("receipt_id");
                    optional = Optional.of(new Receipt(id, receipt.getSellerName(), receipt.getDateTime()));
                }
            }
        } catch (SQLException e) {
            String message = "An error occurred while searching for the " + receipt;
            throw new TechnicalException(message, e);
        }
        return optional;
    }
}
