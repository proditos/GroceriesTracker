package dao.impl;

import dao.api.ReceiptProductDao;
import entity.ReceiptProduct;
import exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
public class ReceiptProductDaoImpl implements ReceiptProductDao {
    private final Connection connection;

    public ReceiptProductDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long save(ReceiptProduct receiptProduct) {
        if (receiptProduct == null) {
            throw new DaoException("An error occurred while saving the receiptProduct, receiptProduct is null");
        }
        String query = "INSERT INTO receipts_products (receipt_id, product_id, quantity) VALUES(?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, receiptProduct.getReceiptId());
            statement.setLong(2, receiptProduct.getProductId());
            statement.setDouble(3, receiptProduct.getQuantity());
            int affectedRows = statement.executeUpdate();
            Optional<ReceiptProduct> added = findLastBy(
                    receiptProduct.getReceiptId(),
                    receiptProduct.getProductId(),
                    receiptProduct.getQuantity()
            );
            if (affectedRows == 0 || !added.isPresent()) {
                throw new DaoException("An error occurred while saving the receiptProduct, no rows affected");
            } else {
                return added.get().getId();
            }
        } catch (SQLException e) {
            throw new DaoException("An error occurred while saving the receiptProduct", e);
        }
    }

    @Override
    public Optional<ReceiptProduct> findLastBy(long receiptId, long productId, double quantity) {
        Optional<ReceiptProduct> optional = Optional.empty();
        String query = "SELECT receipt_product_id FROM receipts_products " +
                "WHERE receipt_id=? AND product_id=? AND quantity=? ORDER BY receipt_product_id DESC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, receiptId);
            statement.setLong(2, productId);
            statement.setDouble(3, quantity);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("receipt_product_id");
                    optional = Optional.of(new ReceiptProduct(id, receiptId, productId, quantity));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("An error occurred while receiving a receiptProduct by receiptId='" + receiptId +
                    "', productId='" + productId + "' and quantity='" + quantity + "'", e);
        }
        return optional;
    }
}
