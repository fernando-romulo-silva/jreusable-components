package org.reusablecomponent.core.application.query.entity.nonpaged;

import org.reusablecomponent.core.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

/**
 * @param <Entity>
 * @param <Id>
 * @param <Specification>
 */
public interface InterfaceEntityQuerySpecificationFacade<Entity extends AbstractEntity<Id>, Id, // basic
			// results
			OneResult, // One result type
			MultiplePagedResult, // multiple result type
			CountResult, // count result type
			ExistsResult, // exists result type
			// specification
			Specification> // query specification (parameters, filters, orders, etc)
	extends InterfaceEntityBaseFacade<Entity, Id> {

    /**
     * @param specification
     * @param directives
     * @return
     */
    MultiplePagedResult findBy(final Specification specification, final Object... directives);
    
    /**
     * @param specification
     * @return
     */
    OneResult findOneBy(final Specification specification, final Object... directives);
    
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
