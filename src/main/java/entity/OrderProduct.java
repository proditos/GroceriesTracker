package entity;

/**
 * @author Vladislav Konovalov
 */
public class OrderProduct extends AbstractEntity {
    private final Long orderId;
    private final Long productId;
    private final double quantity;

    public OrderProduct(Long id, Long orderId, Long productId, double quantity) {
        super(id);
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
