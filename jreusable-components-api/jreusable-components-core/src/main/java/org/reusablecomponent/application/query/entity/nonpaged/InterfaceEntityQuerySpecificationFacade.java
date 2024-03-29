package org.reusablecomponent.application.query.entity.nonpaged;

import java.util.Map;

import org.reusablecomponent.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponent.domain.AbstractEntity;

/**
 * @param <Entity>
 * @param <Id>
 * @param <Specification>
 */
public interface InterfaceEntityQuerySpecificationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, CountResult, ExistsResult, Specification> 
	extends InterfaceEntityBaseFacade<Entity, Id> {

    /**
     * @param specification
     * @param directives
     * @return
     */
    MultiplePagedResult findBy(final Specification specification, final Map<String, String[]> directives);
    
    /**
     * @param specification
     * @return
     */
    OneResult findBy(final Specification specification);
    
    /**
     * @param specification
     * @return
     */
    ExistsResult existsBy(final Specification specification);
    
    /**
     * @param specification
     * @return
     */
    CountResult count(final Specification specification);
    
}
