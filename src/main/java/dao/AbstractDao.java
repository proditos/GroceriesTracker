package dao;

import annotation.ExcludeFromJacocoGeneratedReport;
import entity.AbstractEntity;
import java.sql.Connection;

/**
 * @author Vladislav Konovalov
 */
public abstract class AbstractDao<E extends AbstractEntity> {
    protected final Connection connection;

    @ExcludeFromJacocoGeneratedReport
    protected AbstractDao(Connection connection) {
        this.connection = connection;
    }

    public abstract long save(E entity);
}
