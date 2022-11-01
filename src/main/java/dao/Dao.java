package dao;

import entity.AbstractEntity;

/**
 * @author Vladislav Konovalov
 */
public interface Dao<T extends AbstractEntity> {
    void save(T entity);
}
