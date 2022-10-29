package entity;

/**
 * @author Vladislav Konovalov
 */
public class OrderProduct {
    private Long orderId;
    private Long productId;
    private double quantity;

    public OrderProduct(Long orderId, Long productId, double quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public double getQuantity() {
        return quantity;
    }
}
