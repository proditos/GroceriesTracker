package service;

import dto.AbstractDto;
import javax.sql.DataSource;

/**
 * @author Vladislav Konovalov
 */
public abstract class AbstractService<D extends AbstractDto> {
    protected final DataSource dataSource;

    protected AbstractService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public abstract void add(D entity);
}
