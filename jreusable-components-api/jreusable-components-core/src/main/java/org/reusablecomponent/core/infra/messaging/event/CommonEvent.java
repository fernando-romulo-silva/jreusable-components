package org.reusablecomponent.core.infra.messaging.event;

public enum CommonEvent implements InterfaceOperationEvent {

    SAVE_ITEM,
    UPDATE_ITEM,
    DELETE_ITEM,
    DELETE_ID,
    
    SAVE_LIST,
    UPDATE_LIST,
    DELETE_LIST,
    DELETE_IDS,
    
    FIND_ALL,
    FIND_BY_SPECIFICATION,
    FIND_ALL_PAGEABLE,
    FIND_BY_SPECIFICATION_PAGEABLE,
    
    FIND_ONE_BY_SPECIFICATION,

    FIND_BY_ID,
    FIND_UNIQUE,
    
    EXISTS_BY_ID,
    EXISTS_BY_SPECIFICATION,
    
    COUNT,
    COUNT_BY_SPECIFICATION,
    
}
