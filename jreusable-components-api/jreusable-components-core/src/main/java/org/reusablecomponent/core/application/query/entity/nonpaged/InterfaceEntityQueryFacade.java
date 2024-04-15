package org.reusablecomponent.core.application.query.entity.nonpaged;

import static java.util.Objects.isNull;

import java.util.Map;

import org.reusablecomponent.core.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <MultipleResult>
 * @param <CountResult>
 * @param <ExistsResult>
 */
public interface InterfaceEntityQueryFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult, CountResult, ExistsResult> //
	extends InterfaceEntityBaseFacade<Entity, Id> {
    
    /**
     * @param id
     * @return
     */
    OneResult findBy(final Id id);
    
    /**
     * @param directives
     * @return
     */
    MultipleResult findAll(final Map<String, String[]> directives);

    /**
     * @param id
     * @return
     */
    ExistsResult existsBy(final Id id);
    
    /**
     *
     * @return
     */
    CountResult count();
    
    /**
     * @param multipleResult
     * @return
     */
    default String getMultipleResultEventData(final MultipleResult multipleResult) {
	
	if (isNull(multipleResult)) {
	    return "Result size 0";
	}
	
	return multipleResult.toString();
    }
}