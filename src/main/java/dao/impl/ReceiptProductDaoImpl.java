package dao.impl;

import dao.api.ReceiptProductDao;
import entity.ReceiptProduct;
import exception.TechnicalException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Vladislav Konovalov
 */
public class ReceiptProductDaoImpl implements ReceiptProductDao {
    private final Connection connection;

    public ReceiptProductDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(ReceiptProduct receiptProduct) {
        if (receiptProduct == null) {
            String message = "An error occurred while saving the receiptProduct, the receiptProduct is null";
            throw new TechnicalException(message);
        }
        String query = "INSERT INTO receipts_products (receipt_id, product_id, quantity) VALUES(?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, receiptProduct.getReceiptId());
            statement.setLong(2, receiptProduct.getProductId());
            statement.setDouble(3, receiptProduct.getQuantity());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new TechnicalException("An error occurred while saving the receiptProduct, no rows affected");
            }
        } catch (SQLException e) {
            String message = "An error occurred while saving the " + receiptProduct;
            throw new TechnicalException(message, e);
        }
    }
}
