package com.github.proditos.mapper.impl;

import com.github.proditos.dto.ReceiptDto;
import com.github.proditos.entity.Receipt;
import com.github.proditos.mapper.api.Mapper;

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
