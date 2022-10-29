package dao;

/**
 * @author Vladislav Konovalov
 */
public interface Dao<T> {
    void save(T t);
}
