package dao;

import entity.ReceiptProduct;
import exception.DaoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Vladislav Konovalov
 */
public class ReceiptProductDao extends AbstractDao<ReceiptProduct> {
    public ReceiptProductDao(Connection connection) {
        super(connection);
    }

    @Override
    public void save(ReceiptProduct receiptProduct) {
        if (receiptProduct == null) return;
        String query = "INSERT INTO receipts_products (receipt_id, product_id, quantity) VALUES(?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, receiptProduct.getReceiptId());
            statement.setLong(2, receiptProduct.getProductId());
            statement.setDouble(3, receiptProduct.getQuantity());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0)
                throw new SQLException("An error occurred while saving the receipt_product, no rows affected");
        } catch (SQLException e) {
            throw new DaoException("An error occurred while saving the receipt_product", e);
        }
    }
}
