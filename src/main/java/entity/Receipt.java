package entity;

import java.time.LocalDateTime;

/**
 * @author Vladislav Konovalov
 */
public class Receipt extends AbstractEntity {
    private final String sellerName;
    private final LocalDateTime dateTime;

    public Receipt(Long id, String sellerName, LocalDateTime dateTime) {
        super(id);
        this.sellerName = sellerName;
        this.dateTime = dateTime;
    }

    public String getSellerName() {
        return sellerName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
