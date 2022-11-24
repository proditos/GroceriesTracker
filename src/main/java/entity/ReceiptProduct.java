package entity;

/**
 * @author Vladislav Konovalov
 */
public final class ReceiptProduct extends AbstractEntity {
    private final long receiptId;
    private final long productId;
    private final double quantity;

    public ReceiptProduct(long receiptId, long productId, double quantity) {
        super(null);
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

    @Override
    public String toString() {
        return "ReceiptProduct{" +
                "receiptId=" + receiptId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
