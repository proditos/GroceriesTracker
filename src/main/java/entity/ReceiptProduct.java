package entity;

/**
 * @author Vladislav Konovalov
 */
public class ReceiptProduct extends AbstractEntity {
    private final long receiptId;
    private final long productId;
    private final double quantity;

    public ReceiptProduct(Long id, long receiptId, long productId, double quantity) {
        super(id);
        this.receiptId = receiptId;
        this.productId = productId;
        this.quantity = quantity;
    }

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
}
