package dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vladislav Konovalov
 */
public class ReceiptDto extends AbstractDto {
    private final String sellerName;
    private final LocalDateTime dateTime;
    private final Map<ProductDto, Double> productQuantityMap = new HashMap<>();

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
}
