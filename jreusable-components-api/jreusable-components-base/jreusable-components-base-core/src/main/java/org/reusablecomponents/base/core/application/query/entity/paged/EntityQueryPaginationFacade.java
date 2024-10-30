package org.reusablecomponents.base.core.application.query.entity.paged;

import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ALL_ENTITIES_PAGEABLE;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITY_SORTED;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.reusablecomponents.base.core.application.base.EntiyBaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

/**
 * The default <code>InterfaceEntityQueryPaginationFacade</code>'s
 * implementation.
 */
public non-sealed class EntityQueryPaginationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort>
		extends EntiyBaseFacade<Entity, Id>
		implements InterfaceEntityQueryPaginationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntityQueryPaginationFacade.class);

	protected final BiFunction<Pageable, Object[], MultiplePagedResult> findAllFunction;

	protected final Function<Sort, OneResult> findFirstFunction;

	/**
	 * Default constructor
	 * 
	 * @param builder Object in charge to construct this one
	 */
	protected EntityQueryPaginationFacade(
			@NotNull final EntityQueryPaginationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> builder) {

		super(builder);

		this.findAllFunction = builder.findAllFunction;
		this.findFirstFunction = builder.findFirstFunction;
	}

	/**
	 * Method used to change directives object before use it (findAll method).
	 * 
	 * @param pageable   The object to be changed
	 * 
	 * @param directives The object to be changed
	 * 
	 * @return A new {@code Pageable} object
	 */
	protected Pageable preFindAll(final Pageable pageable, @NotNull final Object... directives) {
		return pageable;
	}

	/**
	 * Method used to change multiplePagedResult object after use it (findAll
	 * method).
	 * 
	 * @param multiplePagedResult The object to be changed
	 * 
	 * @return A new {@code MultiplePagedResult} object
	 */
	protected MultiplePagedResult posFindAll(final MultiplePagedResult multiplePagedResult) {
		return multiplePagedResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SafeVarargs
	public final MultiplePagedResult findAll(@Nullable final Pageable pageable, @NotNull final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Finding all by '{}', session '{}'", pageable, session);

		final var finalPageable = preFindAll(pageable, directives);

		final MultiplePagedResult multiplePagedResult;

		try {
			multiplePagedResult = findAllFunction.apply(finalPageable, directives);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					FIND_ALL_ENTITIES_PAGEABLE,
					getEntityClazz(),
					finalPageable,
					directives);
		}

		final var finalMultiplePagedResult = posFindAll(multiplePagedResult);

		LOGGER.debug("Found all by '{}', session '{}'", finalPageable, session);

		return finalMultiplePagedResult;
	}

	// ---------------------------------------------------------------------------

	/**
	 * 
	 * @param sort
	 * 
	 * @param directives
	 * 
	 * @return
	 */
	protected Sort preFindFirst(final Sort sort, final Object... directives) {
		return sort;
	}

	/**
	 * 
	 * @param oneResult
	 * 
	 * @return
	 */
	protected OneResult posFindFirst(final OneResult oneResult) {
		return oneResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OneResult findFirst(@Nullable final Sort sort, final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Finding first by '{}', session '{}'", sort, session);

		final var finalSort = preFindFirst(sort, directives);

		final OneResult oneResult;

		try {
			oneResult = findFirstFunction.apply(finalSort);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					FIND_ENTITY_SORTED,
					getEntityClazz(),
					sort,
					directives);
		}

		final var finalOneResult = posFindFirst(oneResult);

		LOGGER.debug("Found first by '{}', session '{}'", finalSort, session);

		return finalOneResult;
	}
}
