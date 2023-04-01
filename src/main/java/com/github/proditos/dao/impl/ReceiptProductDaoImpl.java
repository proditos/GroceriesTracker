package com.github.proditos.dao.impl;

import com.github.proditos.dao.api.ReceiptProductDao;
import com.github.proditos.exception.TechnicalException;
import com.github.proditos.entity.ReceiptProduct;

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
            assert affectedRows == 1 : "The number of affected rows is not equal to 1";
        } catch (SQLException e) {
            String message = "An error occurred while saving the " + receiptProduct;
            throw new TechnicalException(message, e);
        }
    }
}
