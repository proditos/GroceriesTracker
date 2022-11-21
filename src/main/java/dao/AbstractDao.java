package dao;

import annotation.ExcludeFromJacocoGeneratedReport;
import entity.AbstractEntity;
import exception.DaoException;
import java.sql.Connection;

/**
 * This class is the parent for DAO inheritance.
 * It contains a configured database {@link #connection}.
 * Subclasses of this class <b>must be immutable</b>.
 * The {@code autocommit} property <b>must be set</b> to {@code false}.
 *
 * <p>Methods of this class can throw a {@link DaoException} if the entity is null or
 * errors occurred when saving the entity to the database.</p>
 *
 * @author Vladislav Konovalov
 */
public abstract class AbstractDao<E extends AbstractEntity> {
    /**
     * The configured database connection.
     */
    protected final Connection connection;

    /**
     * Constructs a DAO with the configured database connection.
     *
     * @param connection the configured database connection.
     */
    @ExcludeFromJacocoGeneratedReport
    protected AbstractDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Saves the entity to the database using the {@link #connection}.
     *
     * <p>The implementation of this method must take into account that
     * the {@code autocommit} property is set to {@code false}.</p>
     *
     * @param entity the entity to save.
     * @return the database generated id.
     * @throws exception.DaoException if the entity is null or some errors occurred while saving the entity.
     */
    public abstract long save(E entity);
}
