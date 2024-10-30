package org.reusablecomponents.base.core.infra.util.operation;

/**
 * Command's operations, relate to <code>EntityCommandFacade</code>.
 */
public enum CommandOperation implements InterfaceOperation {

    /**
     * Save an entity operation
     */
    SAVE_ENTITY,

    /**
     * Update an entity operation
     */
    UPDATE_ENTITY,

    /**
     * Delete an entity operation
     */
    DELETE_ENTITY,

    /**
     * Delete an entity by id operation
     */
    DELETE_BY_ID,

    /**
     * Save entities operation
     */
    SAVE_ENTITIES,

    /**
     * Update entities operation
     */
    UPDATE_ENTITIES,

    /**
     * Delete entities operation
     */
    DELETE_ENTITIES,

    /**
     * Delete entities by id operation
     */
    DELETE_BY_IDS,
}
