package service.impl;

import connection.api.SingletonConnection;
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
import exception.TechnicalException;
import mapper.api.Mapper;
import mapper.impl.ProductMapper;
import mapper.impl.ReceiptMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.api.ReceiptService;
import java.util.Map;
import java.util.Optional;

/**
 * @author Vladislav Konovalov
 */
public class ReceiptServiceImpl implements ReceiptService {
    private static final Logger LOGGER = LogManager.getLogger(ReceiptServiceImpl.class);
    private final Mapper<ReceiptDto, Receipt> receiptMapper = new ReceiptMapper();
    private final Mapper<ProductDto, Product> productMapper = new ProductMapper();
    private final SingletonConnection singletonConnection;
    private final ReceiptDao receiptDao;
    private final ReceiptProductDao receiptProductDao;
    private final ProductDao productDao;

    public ReceiptServiceImpl(SingletonConnection singletonConnection) {
        this.singletonConnection = singletonConnection;
        receiptDao = new ReceiptDaoImpl(singletonConnection.getInstance());
        receiptProductDao = new ReceiptProductDaoImpl(singletonConnection.getInstance());
        productDao = new ProductDaoImpl(singletonConnection.getInstance());
    }

    @Override
    // TODO: Refactor this horrible method
    public void add(ReceiptDto receiptDto) {
        try {
            if (receiptDto == null) {
                throw new TechnicalException("The receiptDto is null");
            }

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
                throw new TechnicalException("The receipt is saved but not found");
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
                        throw new TechnicalException("The product is saved but not found");
                    }
                }
            }

            singletonConnection.commit();
        } catch (TechnicalException e) {
            singletonConnection.rollback();
            LOGGER.error("An error occurred while adding new receipt to the database", e);
        }
    }
}
