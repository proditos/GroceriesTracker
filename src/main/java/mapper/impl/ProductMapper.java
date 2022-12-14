package mapper.impl;

import dto.ProductDto;
import entity.Product;
import mapper.api.Mapper;

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
