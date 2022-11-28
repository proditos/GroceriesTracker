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
    public void add(ReceiptDto receiptDto) {
        try {
            if (receiptDto == null) {
                throw new TechnicalException("The receiptDto is null");
            }

            Receipt receipt = receiptMapper.toEntity(receiptDto);
            if (receiptDao.findLast(receipt).isPresent()) {
                return;
            }
            long receiptId = saveAndGetId(receipt);

            saveProductsToReceipt(receiptDto.getProductQuantityMap(), receiptId);

            singletonConnection.commit();
        } catch (TechnicalException e) {
            singletonConnection.rollback();
            LOGGER.error("An error occurred while adding new receipt to the database", e);
        }
    }

    private long saveAndGetId(Receipt receipt) {
        receiptDao.save(receipt);
        Optional<Receipt> foundReceipt = receiptDao.findLast(receipt);
        long id;
        if (foundReceipt.isPresent()) {
            id = foundReceipt.get().getId();
        } else {
            throw new TechnicalException("The receipt is saved but not found");
        }
        return id;
    }

    private void saveProductsToReceipt(Map<ProductDto, Double> productQuantityMap, long receiptId) {
        for (Map.Entry<ProductDto, Double> productQuantity : productQuantityMap.entrySet()) {
            Product product = productMapper.toEntity(productQuantity.getKey());
            double quantity = productQuantity.getValue();
            Optional<Product> foundProduct = productDao.findLast(product);
            long productId;
            if (foundProduct.isPresent()) {
                productId = foundProduct.get().getId();
            } else {
                productId = saveProductAndGetId(product);
            }
            ReceiptProduct receiptProduct = new ReceiptProduct(receiptId, productId, quantity);
            receiptProductDao.save(receiptProduct);
        }
    }

    private long saveProductAndGetId(Product product) {
        productDao.save(product);
        Optional<Product> foundProduct = productDao.findLast(product);
        long id;
        if (foundProduct.isPresent()) {
            id = foundProduct.get().getId();
        } else {
            throw new TechnicalException("The product is saved but not found");
        }
        return id;
    }
}
