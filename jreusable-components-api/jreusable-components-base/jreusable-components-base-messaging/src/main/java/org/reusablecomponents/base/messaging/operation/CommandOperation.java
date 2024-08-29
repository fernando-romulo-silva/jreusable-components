package org.reusablecomponents.base.messaging.operation;

/**
 * Command's operations, relate to <code>EntityCommandFacade</code>.
 */
public enum CommandOperation implements InterfaceOperation {

    SAVE_ENTITY,

    UPDATE_ENTITY,

    DELETE_ENTITY,

    DELETE_BY_ID,

    SAVE_ENTITIES,

    UPDATE_ENTITIES,

    DELETE_ENTITIES,

    DELETE_BY_IDS,
}
