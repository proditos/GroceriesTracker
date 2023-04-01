package com.github.proditos.mapper.impl;

import com.github.proditos.dto.ProductDto;
import com.github.proditos.entity.Product;
import com.github.proditos.mapper.api.Mapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vladislav Konovalov
 */
class ProductMapperTest {
    final Mapper<ProductDto, Product> productMapper = new ProductMapper();

    @Test
    void testToEntity() {
        ProductDto productDto = new ProductDto("Product", 0.1);

        Product product = productMapper.toEntity(productDto);

        String notNullMessage = "The entity should not be null";
        assertNotNull(product, notNullMessage);
        String nameMessage = "The entity should have the same name";
        assertEquals(productDto.getName(), product.getName(), nameMessage);
        String priceMessage = "The entity should have the same price";
        assertEquals(productDto.getPrice(), product.getPrice(), priceMessage);
    }

    @Test
    void testToEntity_Null() {
        Product product = productMapper.toEntity(null);

        String message = "Should return null when DTO is null";
        assertNull(product, message);
    }
}
