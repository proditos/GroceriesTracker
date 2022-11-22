package mapper.impl;

import dto.ReceiptDto;
import entity.Receipt;
import mapper.api.Mapper;

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
