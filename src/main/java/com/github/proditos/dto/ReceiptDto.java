package com.github.proditos.dto;

import com.github.proditos.annotation.ExcludeFromJacocoGeneratedReport;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * POJO for data transfer between logic layers.
 *
 * @author Vladislav Konovalov
 */
public final class ReceiptDto extends AbstractDto {
    private final String sellerName;
    private final LocalDateTime dateTime;
    private final Map<ProductDto, Double> productQuantityMap = new HashMap<>();

    public ReceiptDto(String sellerName, LocalDateTime dateTime) {
        this.sellerName = sellerName;
        this.dateTime = dateTime;
    }

    /**
     * Adds the product to the receipt.
     * If the product is already in the receipt,
     * it will not be added again, but its quantity increases.
     *
     * @param product  the product to be added.
     * @param quantity product quantity.
     */
    public void addProduct(ProductDto product, double quantity) {
        if (productQuantityMap.containsKey(product)) {
            double newQuantity = productQuantityMap.get(product) + quantity;
            productQuantityMap.put(product, newQuantity);
        } else {
            productQuantityMap.put(product, quantity);
        }
    }

    @ExcludeFromJacocoGeneratedReport
    public String getSellerName() {
        return sellerName;
    }

    @ExcludeFromJacocoGeneratedReport
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @ExcludeFromJacocoGeneratedReport
    public Map<ProductDto, Double> getProductQuantityMap() {
        return Collections.unmodifiableMap(productQuantityMap);
    }
}
