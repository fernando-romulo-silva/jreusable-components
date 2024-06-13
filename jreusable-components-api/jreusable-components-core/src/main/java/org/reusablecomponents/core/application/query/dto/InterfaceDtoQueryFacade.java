package org.reusablecomponents.core.application.query.dto;

import java.util.Map;

/**
 * @param <DTO>
 * @param <Id>
 */
public interface InterfaceDtoQueryFacade<Id, OneResult, MultipleResult> {
    
    /**
     * @param directives
     * @return
     */
    MultipleResult findAll(final Map<String, String[]> directives);

    /**
     * @param id
     * @return
     */
    OneResult findById(final Id id);
}
