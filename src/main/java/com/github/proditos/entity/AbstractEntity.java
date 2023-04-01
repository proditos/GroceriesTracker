package com.github.proditos.entity;

import com.github.proditos.mapper.api.Mapper;

/**
 * The abstract class designed to define inheritance.
 * This is necessary for the correct operation of the {@code Mapper}.
 * Also in this class, boilerplate code is written for the ID field.
 * All entities should inherit it and be immutable.
 *
 * @author Vladislav Konovalov
 * @see Mapper
 */
public abstract class AbstractEntity {
    /**
     * ID of the entity from the database.
     * For entities that are not in the database, ID can be null.
     */
    private final Long id;

    /**
     * The constructor created to be called in a descendant class
     * that initializes a value to the ID field.
     *
     * @param id ID of the entity from the database.
     */
    protected AbstractEntity(Long id) {
        this.id = id;
    }

    /**
     * Simple getter that returns ID of the entity from the database.
     *
     * @return ID of the entity from the database.
     */
    public Long getId() {
        return id;
    }
}
