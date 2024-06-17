package org.reusablecomponents.core.infra.messaging.event;

import org.reusablecomponents.core.infra.messaging.InterfaceOperationEvent;

public enum CommonEvent implements InterfaceOperationEvent {

    SAVE_ENTITY,
    UPDATE_ENTITY,
    DELETE_ENTITY,
    DELETE_BY_ID,
    
    SAVE_ENTITIES,
    UPDATE_ENTITIES,
    DELETE_ENTITIES,
    DELETE_BY_IDS,
    
    FIND_ALL_ENTITIES,
    FIND_ENTITIES_BY_SPECIFICATION,
    FIND_ALL_ENTITIES_PAGEABLE,
    FIND_ENTITIES_BY_SPECIFICATION_PAGEABLE,
    
    FIND_ENTITY_BY_SPECIFICATION,
    FIND_ENTITY_BY_ID,
    
    EXISTS_BY_ID,
    EXISTS_ALL,
    EXISTS_BY_SPECIFICATION,
    
    COUNT_ALL,
    COUNT_BY_SPECIFICATION,
}
