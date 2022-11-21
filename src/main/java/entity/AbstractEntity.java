package entity;

/**
 * @author Vladislav Konovalov
 */
public abstract class AbstractEntity {
    private final Long id;

    protected AbstractEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
