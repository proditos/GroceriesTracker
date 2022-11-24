package service.impl;

import dao.api.ProductDao;
import dao.api.ReceiptDao;
import dao.api.ReceiptProductDao;
import dao.impl.ProductDaoImpl;
import dao.impl.ReceiptDaoImpl;
import dao.impl.ReceiptProductDaoImpl;
import dto.ProductDto;
import dto.ReceiptDto;
import entity.Product;
import entity.Receipt;
import entity.ReceiptProduct;
import exception.DaoException;
import exception.ServiceException;
import mapper.api.Mapper;
import mapper.impl.ProductMapper;
import mapper.impl.ReceiptMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.api.ReceiptService;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
public class ReceiptServiceImpl implements ReceiptService {
    private static final Logger LOGGER = LogManager.getLogger(ReceiptServiceImpl.class);
    private final Mapper<ReceiptDto, Receipt> receiptMapper = new ReceiptMapper();
    private final Mapper<ProductDto, Product> productMapper = new ProductMapper();
    private final DataSource dataSource;

    public ReceiptServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    // TODO: Refactor this horrible method
    public void add(ReceiptDto receiptDto) {
        Connection connection = null;
        try {
            if (receiptDto == null) {
                throw new ServiceException("The receiptDto is null");
            }

            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            ReceiptDao receiptDao = new ReceiptDaoImpl(connection);
            ReceiptProductDao receiptProductDao = new ReceiptProductDaoImpl(connection);
            ProductDao productDao = new ProductDaoImpl(connection);

            Receipt receipt = receiptMapper.toEntity(receiptDto);
            if (receiptDao.findLast(receipt).isPresent()) {
                return;
            }
            receiptDao.save(receipt);
            Optional<Receipt> foundReceipt = receiptDao.findLast(receipt);
            long receiptId;
            if (foundReceipt.isPresent()) {
                receiptId = foundReceipt.get().getId();
            } else {
                throw new ServiceException("The receipt is saved but not found");
            }

            for (Map.Entry<ProductDto, Double> productQuantity : receiptDto.getProductQuantityMap().entrySet()) {
                ProductDto productDto = productQuantity.getKey();
                double quantity = productQuantity.getValue();
                Optional<Product> foundProduct = productDao.findLast(productMapper.toEntity(productDto));
                if (foundProduct.isPresent()) {
                    long productId = foundProduct.get().getId();
                    ReceiptProduct receiptProduct = new ReceiptProduct(receiptId, productId, quantity);
                    receiptProductDao.save(receiptProduct);
                } else {
                    Product product = productMapper.toEntity(productDto);
                    productDao.save(product);
                    foundProduct = productDao.findLast(product);
                    if (foundProduct.isPresent()) {
                        long productId = foundProduct.get().getId();
                        ReceiptProduct receiptProduct = new ReceiptProduct(receiptId, productId, quantity);
                        receiptProductDao.save(receiptProduct);
                    } else {
                        throw new ServiceException("The product is saved but not found");
                    }
                }
            }

            connection.commit();
        } catch (ServiceException | DaoException | SQLException e) {
            try {
                if (connection != null){
                    connection.rollback();
                }
            } catch (SQLException ex) {
                LOGGER.error("An error occurred while trying to rollback database changes", ex);
            }
            LOGGER.error("An error occurred while adding new receipt to the database", e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                LOGGER.error("An error occurred while trying to close the database connection", ex);
            }
        }
    }
}
