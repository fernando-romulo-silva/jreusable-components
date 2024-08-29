package org.reusablecomponents.base.core.application.base;

import org.reusablecomponents.base.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.InterfaceEntityQuerySpecificationFacade;
import org.reusablecomponents.base.core.application.query.entity.paged.InterfaceEntityQueryPaginationFacade;
import org.reusablecomponents.base.core.application.query.entity.paged.InterfaceEntityQueryPaginationSpecificationFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotNull;

/**
 * Base facade interface for all interfaces.
 * 
 * @param <Entity> The facade entity type
 * @param <Id>     The facade entity id type
 */
public sealed interface InterfaceEntityBaseFacade<Entity extends AbstractEntity<Id>, Id>
		permits InterfaceEntityCommandFacade, InterfaceEntityQueryFacade, InterfaceEntityQuerySpecificationFacade,
		InterfaceEntityQueryPaginationFacade, InterfaceEntityQueryPaginationSpecificationFacade,
		EntiyBaseFacade {

	static final String NULL_POINTER_EXCEPTION_MSG = "{exception.nullPointerException}";

	/**
	 * Return the id class type.
	 * 
	 * @return A <code>Class</code> object.
	 */
	@NotNull
	Class<Id> getIdClazz();

	/**
	 * Return the entity class type.
	 * 
	 * @return A <code>Class</code> object.
	 */
	@NotNull
	Class<Entity> getEntityClazz();
}