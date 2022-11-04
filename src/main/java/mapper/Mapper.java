package mapper;

import dto.AbstractDto;
import entity.AbstractEntity;

/**
 * @author Vladislav Konovalov
 */
public interface Mapper<D extends AbstractDto, E extends AbstractEntity> {
    E toEntity(D dto);
}
