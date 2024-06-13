package org.reusablecomponents.core.application.query.entity.nonpaged;

import org.reusablecomponents.core.application.base.InterfaceEntityBaseFacade;
import org.reusablecomponents.core.domain.AbstractEntity;

/**
 * @param <Entity>
 * @param <Id>
 * @param <Specification>
 */
public non-sealed interface InterfaceEntityQuerySpecificationFacade<Entity extends AbstractEntity<Id>, Id, // basic
			OneResult, // One result type
			MultipleResult, // multiple result type
			CountResult, // count result type
			ExistsResult, // exists result type
			Specification> // query specification (parameters, filters, orders, etc)
	extends InterfaceEntityBaseFacade<Entity, Id> {

    /**
     * @param specification
     * @param directives
     * @return
     */
    MultipleResult findBy(final Specification specification, final Object... directives);
    
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
