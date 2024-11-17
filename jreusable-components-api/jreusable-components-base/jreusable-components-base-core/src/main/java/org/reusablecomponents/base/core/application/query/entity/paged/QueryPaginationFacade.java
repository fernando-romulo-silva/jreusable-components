package org.reusablecomponents.base.core.application.query.entity.paged;

import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ALL_ENTITIES_PAGEABLE;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITY_SORTED;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

/**
 * The default <code>InterfaceEntityQueryPaginationFacade</code>'s
 * implementation.
 */
public non-sealed class QueryPaginationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort>
		extends BaseFacade<Entity, Id>
		implements InterfaceQueryPaginationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryPaginationFacade.class);

	protected final BiFunction<Pageable, Object[], MultiplePagedResult> findAllFunction;

	protected final Function<Sort, OneResult> findFirstFunction;

	/**
	 * Default constructor
	 * 
	 * @param builder Object in charge to construct this one
	 */
	protected QueryPaginationFacade(
			@NotNull final QueryPaginationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> builder) {

		super(builder);

		this.findAllFunction = builder.findAllFunction;
		this.findFirstFunction = builder.findFirstFunction;
	}

	/**
	 * Method used to change directives object before use it (findAll method).
	 * 
	 * @param pageable   The object to be changed
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
	 * Method used to handle pageable find all errors.
	 * 
	 * @param pageable   The object used to pageable find all
	 * @param exception  Exception thrown by pageable find all operation
	 * @param directives Objects used to configure the pageable find all operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorFindAll(
			final Pageable pageable,
			final Exception exception,
			final Object... directives) {

		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SafeVarargs
	public final MultiplePagedResult findAll(@Nullable final Pageable pageable, @NotNull final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Pageable finding all by '{}', session '{}'", pageable, session);

		final var finalPageable = preFindAll(pageable, directives);

		final MultiplePagedResult multiplePagedResult;

		try {
			multiplePagedResult = findAllFunction.apply(finalPageable, directives);
		} catch (final Exception ex) {

			final var finalException = errorFindAll(pageable, ex, directives);

			LOGGER.debug("Error pageable find all, pageable '{}', session '{}', error '{}'",
					pageable, session, getRootCauseMessage(finalException));

			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					FIND_ALL_ENTITIES_PAGEABLE,
					getEntityClazz(),
					finalPageable,
					directives);
		}

		final var finalMultiplePagedResult = posFindAll(multiplePagedResult);

		LOGGER.debug("Found all by pageable '{}', session '{}'", finalPageable, session);

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
	protected Sort preFindOne(final Sort sort, final Object... directives) {
		return sort;
	}

	/**
	 * 
	 * @param oneResult
	 * 
	 * @return
	 */
	protected OneResult posFindOne(final OneResult oneResult) {
		return oneResult;
	}

	/**
	 * Method used to handle pageable find all errors.
	 * 
	 * @param sort       The object used to pageable find all
	 * @param exception  Exception thrown by pageable find all operation
	 * @param directives Objects used to configure the pageable find all operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorFindOne(
			final Sort sort,
			final Exception exception,
			final Object... directives) {

		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OneResult findOne(@Nullable final Sort sort, final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Finding first by '{}', session '{}'", sort, session);

		final var finalSort = preFindOne(sort, directives);

		final OneResult oneResult;

		try {
			oneResult = findFirstFunction.apply(finalSort);
		} catch (final Exception ex) {
			final var finalException = errorFindOne(finalSort, ex, directives);

			LOGGER.debug("Error find first, sort '{}', session '{}', error '{}'",
					finalSort, session, getRootCauseMessage(finalException));

			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					FIND_ENTITY_SORTED,
					getEntityClazz(),
					sort,
					directives);
		}

		final var finalOneResult = posFindOne(oneResult);

		LOGGER.debug("Found first by '{}', session '{}'", finalSort, session);

		return finalOneResult;
	}
}
