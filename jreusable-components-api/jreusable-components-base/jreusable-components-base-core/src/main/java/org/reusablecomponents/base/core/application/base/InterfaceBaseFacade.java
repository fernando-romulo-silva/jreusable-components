package org.reusablecomponents.base.core.application.base;

import org.reusablecomponents.base.core.application.command.entity.InterfaceCommandFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.InterfaceQueryFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.InterfaceQuerySpecificationFacade;
import org.reusablecomponents.base.core.application.query.entity.paged.InterfaceQueryPaginationFacade;
import org.reusablecomponents.base.core.application.query.entity.paged.InterfaceQueryPaginationSpecificationFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotNull;

/**
 * Base facade interface for all interfaces.
 * 
 * @param <Entity> The facade entity type
 * @param <Id>     The facade entity id type
 */
public sealed interface InterfaceBaseFacade<Entity extends AbstractEntity<Id>, Id>
		permits InterfaceCommandFacade, InterfaceQueryFacade, InterfaceQuerySpecificationFacade,
		InterfaceQueryPaginationFacade, InterfaceQueryPaginationSpecificationFacade,
		BaseFacade {

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