package org.reusablecomponents.base.core.application.query.entity.paged;

import static org.reusablecomponents.base.messaging.operation.QueryOperation.FIND_ENTITIES_BY_SPECIFICATION_PAGEABLE;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.FIND_ENTITY_BY_SPECIFICATION_SORTED;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import org.apache.commons.lang3.function.TriFunction;
import org.reusablecomponents.base.core.application.base.EntiyBaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

/**
 * Interface responsible for establishing contracts to retrieve objects, common
 * operations to all projects.
 */
public non-sealed class EntityQueryPaginationSpecificationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification>
		extends EntiyBaseFacade<Entity, Id>
		implements
		InterfaceEntityQueryPaginationSpecificationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntityQueryPaginationSpecificationFacade.class);

	protected final TriFunction<Specification, Pageable, Object[], MultiplePagedResult> findBySpecificationFunction;

	protected final TriFunction<Specification, Sort, Object[], OneResult> findOneByFunctionWithOrder;

	/**
	 * Default constructor
	 * 
	 * @param builder Object in charge to construct this one
	 */
	protected EntityQueryPaginationSpecificationFacade(
			@NotNull final EntityQueryPaginationSpecificationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification> builder) {
		super(builder);

		this.findBySpecificationFunction = builder.findBySpecificationFunction;
		this.findOneByFunctionWithOrder = builder.findOneByFunctionWithOrder;
	}

	// ---------------------------------------------------------------------------

	/**
	 * 
	 * @param pageable
	 * 
	 * @param specification
	 * 
	 * @return
	 */
	protected Entry<Pageable, Specification> preFindBy(final Pageable pageable, final Specification specification) {
		return new SimpleEntry<>(pageable, specification);
	}

	/**
	 * 
	 * @param multiplePagedResult
	 * 
	 * @return
	 */
	protected MultiplePagedResult posFindBy(final MultiplePagedResult multiplePagedResult) {
		return multiplePagedResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MultiplePagedResult findBy(
			@Nullable final Pageable pageable,
			@Nullable final Specification specification,
			@NotNull final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Finding by '{}' & '{}', session '{}'", pageable, specification, session);

		final var finalEntry = preFindBy(pageable, specification);

		final var finalPageable = finalEntry.getKey();
		final var finalSpecification = finalEntry.getValue();

		final MultiplePagedResult multiplePagedResult;

		try {
			multiplePagedResult = findBySpecificationFunction.apply(finalSpecification, finalPageable, directives);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					FIND_ENTITIES_BY_SPECIFICATION_PAGEABLE,
					getEntityClazz(),
					directives);
		}

		final var finalMultiplePagedResult = posFindBy(multiplePagedResult);

		LOGGER.debug("Found by '{}', session '{}'", finalPageable, session);

		return finalMultiplePagedResult;
	}

	// ---------------------------------------------------------------------------

	/**
	 * 
	 * @param specification
	 * 
	 * @param sort
	 * 
	 * @return
	 */
	protected Entry<Specification, Sort> preFindOneBy(final Specification specification, final Sort sort) {
		return new SimpleEntry<>(specification, sort);
	}

	/**
	 * 
	 * @param oneResult
	 * 
	 * @return
	 */
	protected OneResult posFindOneBy(final OneResult oneResult) {
		return oneResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OneResult findOneBy(
			@Nullable final Specification specification,
			@Nullable final Sort sort,
			final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Finding one by '{}' & '{}', session '{}'", specification, sort, session);

		final var finalEntry = preFindOneBy(specification, sort);
		final var finalSpecification = finalEntry.getKey();
		final var finalSort = finalEntry.getValue();

		final OneResult oneResult;

		try {
			oneResult = findOneByFunctionWithOrder.apply(finalSpecification, finalSort, null);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					FIND_ENTITY_BY_SPECIFICATION_SORTED,
					getEntityClazz(),
					directives);
		}

		final var finalOneResult = posFindOneBy(oneResult);

		LOGGER.debug("Found first by '{}' & '{}', session '{}'", finalSpecification, sort, session);

		return finalOneResult;
	}
}
