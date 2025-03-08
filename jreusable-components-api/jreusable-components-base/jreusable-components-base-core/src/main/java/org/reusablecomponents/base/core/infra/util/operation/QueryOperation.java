package org.reusablecomponents.base.core.infra.util.operation;

/**
 * Query's operations, relate to <code>InterfaceEntityQueryFacade</code>,
 * <code>InterfaceEntityQuerySpecificationFacade</code>,
 * <code>InterfaceEntityQueryPaginationFacade</code>, and
 * <code>InterfaceEntityQueryPaginationSpecificationFacade</code>.
 */
public enum QueryOperation implements InterfaceOperationType {

    FIND_ALL_ENTITIES,

    FIND_ENTITIES_BY_SPECIFICATION,

    FIND_ALL_ENTITIES_PAGEABLE,

    FIND_ENTITIES_BY_SPECIFICATION_PAGEABLE,

    FIND_ENTITY_BY_SPECIFICATION,

    FIND_ENTITY_SORTED,

    FIND_ENTITY_BY_SPECIFICATION_SORTED,

    FIND_ENTITY_BY_ID,

    EXISTS_BY_ID,

    EXISTS_ALL,

    EXISTS_BY_SPECIFICATION,

    COUNT_ALL,

    COUNT_BY_SPECIFICATION,
}
