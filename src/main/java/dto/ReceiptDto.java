package dto;

import annotation.ExcludeFromJacocoGeneratedReport;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vladislav Konovalov
 */
public class ReceiptDto extends AbstractDto {
    private final String sellerName;
    private final LocalDateTime dateTime;
    private final Map<ProductDto, Double> productQuantityMap = new HashMap<>();

    @ExcludeFromJacocoGeneratedReport
    public ReceiptDto(String sellerName, LocalDateTime dateTime) {
        this.sellerName = sellerName;
        this.dateTime = dateTime;
    }

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
