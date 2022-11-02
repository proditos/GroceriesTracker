package entity;

/**
 * @author Vladislav Konovalov
 */
public class ReceiptProduct extends AbstractEntity {
    private final Long receiptId;
    private final Long productId;
    private final double quantity;

    public ReceiptProduct(Long id, Long receiptId, Long productId, double quantity) {
        super(id);
        this.receiptId = receiptId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public Long getProductId() {
        return productId;
    }

    public double getQuantity() {
        return quantity;
    }
}
