package org.reusablecomponents.base.core.application.base;

import org.reusablecomponents.base.core.application.command.entity.EntityCommandFacade;
import org.reusablecomponents.base.core.application.empty.SimpleEntiyBaseFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.EntityQueryFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.EntityQuerySpecificationFacade;
import org.reusablecomponents.base.core.application.query.entity.paged.EntityQueryPaginationFacade;
import org.reusablecomponents.base.core.application.query.entity.paged.EntityQueryPaginationSpecificationFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.TypeToken;

import jakarta.validation.constraints.NotNull;

/**
 * The <code>InterfaceEntityBaseFacade</code> common implementation using
 * event-driven architecture.
 * 
 * @param <Entity> The facade entity type
 * @param <Id>     The facade entity id type
 */
public sealed class EntiyBaseFacade<Entity extends AbstractEntity<Id>, Id>
		implements InterfaceEntityBaseFacade<Entity, Id>
		permits SimpleEntiyBaseFacade, EntityCommandFacade, EntityQueryFacade,
		EntityQuerySpecificationFacade, EntityQueryPaginationFacade, EntityQueryPaginationSpecificationFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntiyBaseFacade.class);

	// -------

	protected final InterfaceSecurityService securityService;

	protected final InterfaceI18nService i18nService;

	protected final InterfaceExceptionAdapterService exceptionAdapterService;

	protected final Class<Entity> entityClazz;

	protected final Class<Id> idClazz;

	// ------

	/**
	 * Default constructor
	 * 
	 * @param builder Object attribute constructor.
	 */
	protected EntiyBaseFacade(final EntiyBaseFacadeBuilder builder) {
		super();

		this.entityClazz = retrieveEntityClazz();
		this.idClazz = retrieveIdClazz();

		this.i18nService = builder.i18nService;
		this.securityService = builder.securityService;
		this.exceptionAdapterService = builder.exceptionAdapterService;
	}

	// ------

	/**
	 * Capture the entity class type
	 * 
	 * @return A <code>Class<Entity></code> object
	 */
	@SuppressWarnings("unchecked")
	private final Class<Entity> retrieveEntityClazz() {

		final var entityTypeToken = new TypeToken<Entity>(getClass()) {
			private static final long serialVersionUID = 1L;
		};

		final var rawType = (Class<Entity>) entityTypeToken.getRawType();

		LOGGER.debug("Class type '{}'", rawType);

		return rawType;
	}

	/**
	 * Capture the entity id class type
	 * 
	 * @return A <code>Class<Id></code> object
	 */
	@SuppressWarnings("unchecked")
	private final Class<Id> retrieveIdClazz() {

		final var idTypeToken = new TypeToken<Id>(getClass()) {
			private static final long serialVersionUID = 1L;
		};

		final var rawType = (Class<Id>) idTypeToken.getRawType();

		LOGGER.debug("Class type '{}'", rawType);

		return rawType;
	}

	// ------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Class<Id> getIdClazz() {
		return idClazz;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Class<Entity> getEntityClazz() {
		return entityClazz;
	}

	@NotNull
	public final InterfaceI18nService getI18nService() {
		return i18nService;
	}

	@NotNull
	public final InterfaceSecurityService getSecurityService() {
		return securityService;
	}

	@NotNull
	public final InterfaceExceptionAdapterService getExceptionTranslatorService() {
		return exceptionAdapterService;
	}
}
