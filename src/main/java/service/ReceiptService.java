package service;

import dao.ProductDao;
import dao.ReceiptDao;
import dao.ReceiptProductDao;
import dto.ProductDto;
import dto.ReceiptDto;
import entity.Product;
import entity.Receipt;
import entity.ReceiptProduct;
import exception.DaoException;
import exception.ServiceException;
import mapper.Mapper;
import mapper.ProductMapper;
import mapper.ReceiptMapper;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
public class ReceiptService extends AbstractService<ReceiptDto> {
    private final Mapper<ReceiptDto, Receipt> receiptMapper = new ReceiptMapper();
    private final Mapper<ProductDto, Product> productMapper = new ProductMapper();

    public ReceiptService(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void add(ReceiptDto receiptDto) {
        if (receiptDto == null) {
            throw new ServiceException("The receiptDto is null");
        }
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            ReceiptDao receiptDao = new ReceiptDao(connection);
            ReceiptProductDao receiptProductDao = new ReceiptProductDao(connection);
            ProductDao productDao = new ProductDao(connection);

            String sellerName = receiptDto.getSellerName();
            LocalDateTime dateTime = receiptDto.getDateTime();
            if (receiptDao.findLastBy(sellerName, dateTime).isPresent()) {
                return;
            }
            long receiptId = receiptDao.save(receiptMapper.toEntity(receiptDto));

            for (Map.Entry<ProductDto, Double> productQuantity : receiptDto.getProductQuantityMap().entrySet()) {
                ProductDto productDto = productQuantity.getKey();
                double quantity = productQuantity.getValue();
                Optional<Product> foundProduct = productDao.findLastBy(productDto.getName(), productDto.getPrice());
                if (foundProduct.isPresent()) {
                    long productId = foundProduct.get().getId();
                    ReceiptProduct receiptProduct = new ReceiptProduct(receiptId, productId, quantity);
                    receiptProductDao.save(receiptProduct);
                } else {
                    long productId = productDao.save(productMapper.toEntity(productDto));
                    ReceiptProduct receiptProduct = new ReceiptProduct(receiptId, productId, quantity);
                    receiptProductDao.save(receiptProduct);
                }
            }

            connection.commit();
        } catch (ServiceException | DaoException | SQLException e) {
            try {
                if (connection != null){
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
