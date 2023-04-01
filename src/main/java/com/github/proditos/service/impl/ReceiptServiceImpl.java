package com.github.proditos.service.impl;

import com.github.proditos.mapper.impl.ReceiptMapper;
import com.github.proditos.connection.api.SingletonConnection;
import com.github.proditos.dao.api.ProductDao;
import com.github.proditos.dao.api.ReceiptDao;
import com.github.proditos.dao.api.ReceiptProductDao;
import com.github.proditos.dto.ProductDto;
import com.github.proditos.dto.ReceiptDto;
import com.github.proditos.entity.Product;
import com.github.proditos.entity.Receipt;
import com.github.proditos.entity.ReceiptProduct;
import com.github.proditos.exception.TechnicalException;
import com.github.proditos.mapper.api.Mapper;
import com.github.proditos.mapper.impl.ProductMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.github.proditos.service.api.ReceiptService;

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

    public ReceiptServiceImpl(SingletonConnection singletonConnection,
                              ReceiptDao receiptDao,
                              ReceiptProductDao receiptProductDao,
                              ProductDao productDao) {
        this.singletonConnection = singletonConnection;
        this.receiptDao = receiptDao;
        this.receiptProductDao = receiptProductDao;
        this.productDao = productDao;
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
        assert receipt != null : "The receipt is null";
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
        assert productQuantityMap != null : "The productQuantityMap is null";
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
        assert product != null : "The product is null";
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
