package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Vladislav Konovalov
 */
public class Order {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private LocalDateTime deliveryDateTime;
    @JsonIgnore
    private final Map<Product, Double> products = new HashMap<>();

    public Order() {}

    public Order(Long id, LocalDateTime deliveryDateTime) {
        this.id = id;
        this.deliveryDateTime = deliveryDateTime;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public Map<Product, Double> getProducts() {
        return products;
    }

    @JsonProperty("Document")
    private void unpackNested(Map<String, Object> document) {
        String dateTimeStr = (String) document.get("DateTime");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        deliveryDateTime = LocalDateTime.parse(dateTimeStr, formatter);

        List<Map<String, Object>> items = (List<Map<String, Object>>) document.get("Items");
        items.forEach(productMap -> {
            String name = (String) productMap.get("Name");
            double quantity = (double) productMap.get("Quantity");
            double price = ((int) productMap.get("Price")) / 100.0;
            boolean pricePerKg = ((int) productMap.get("ProductUnitOfMeasure")) == 11;
            products.put(new Product(null, name, price, pricePerKg), quantity);
        });
    }
}
