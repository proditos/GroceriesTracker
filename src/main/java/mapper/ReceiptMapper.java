package mapper;

import dto.ReceiptDto;
import entity.Receipt;

/**
 * @author Vladislav Konovalov
 */
public class ReceiptMapper implements Mapper<ReceiptDto, Receipt> {
    @Override
    public Receipt toEntity(ReceiptDto dto) {
        if (dto == null) {
            return null;
        }
        return new Receipt(dto.getSellerName(), dto.getDateTime());
    }
}
