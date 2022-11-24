package entity;

import java.time.LocalDateTime;

/**
 * @author Vladislav Konovalov
 */
public final class Receipt extends AbstractEntity {
    private final String sellerName;
    private final LocalDateTime dateTime;

    public Receipt(Long id, String sellerName, LocalDateTime dateTime) {
        super(id);
        this.sellerName = sellerName;
        this.dateTime = dateTime;
    }

    public Receipt(String sellerName, LocalDateTime dateTime) {
        super(null);
        this.sellerName = sellerName;
        this.dateTime = dateTime;
    }

    public String getSellerName() {
        return sellerName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "sellerName='" + sellerName + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
