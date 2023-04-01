package com.github.proditos.mapper.impl;

import com.github.proditos.dto.ProductDto;
import com.github.proditos.entity.Product;
import com.github.proditos.mapper.api.Mapper;

/**
 * @author Vladislav Konovalov
 */
public class ProductMapper implements Mapper<ProductDto, Product> {
    @Override
    public Product toEntity(ProductDto dto) {
        if (dto == null) {
            return null;
        }
        return new Product(dto.getName(), dto.getPrice());
    }
}
