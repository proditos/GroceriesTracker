package com.github.proditos.mapper.api;

import com.github.proditos.dto.AbstractDto;
import com.github.proditos.entity.AbstractEntity;

/**
 * The interface that defines the behavior of the mapper between the DTO and the entity.
 * If the class fields do not map when mapping, then the extra ones will be set to standard values.
 *
 * @param <D> the DTO class.
 * @param <E> the entity class.
 * @author Vladislav Konovalov
 * @see AbstractDto
 * @see AbstractEntity
 */
public interface Mapper<D extends AbstractDto, E extends AbstractEntity> {
    /**
     * The method that maps the DTO to the entity.
     *
     * @param dto the input DTO.
     * @return the resulting entity.
     */
    E toEntity(D dto);
}
