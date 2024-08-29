package org.reusablecomponents.base.core.application.query.entity.paged;

import static org.reusablecomponents.base.messaging.operation.QueryOperation.FIND_ALL_ENTITIES_PAGEABLE;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.reusablecomponents.base.core.application.base.EntiyBaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <MultiplePagedResult>
 * @param <Pageable>
 * @param <Sort>
 */
public non-sealed class EntityQueryPaginationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort>
		extends EntiyBaseFacade<Entity, Id>
		implements InterfaceEntityQueryPaginationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntityQueryPaginationFacade.class);

	protected final BiFunction<Pageable, Object[], MultiplePagedResult> findAllFunction;

	protected final Function<Sort, OneResult> findFirstFunction;

	/**
	 * @param findAllFunction
	 * @param findFirstFunction
	 */
	public EntityQueryPaginationFacade(
			@NotNull final BiFunction<Pageable, Object[], MultiplePagedResult> findAllFunction,
			@NotNull final Function<Sort, OneResult> findFirstFunction) {
		super();
		this.findAllFunction = findAllFunction;
		this.findFirstFunction = findFirstFunction;
	}

	// ---------------------------------------------------------------------------
	protected String convertPageableToPublishDataIn(final Pageable pageable) {
		return Objects.toString(pageable);
	}

	protected String convertMultiplePagedResultToPublishDataOut(final MultiplePagedResult multiplePagedResult) {
		return Objects.toString(multiplePagedResult);
	}

	protected Pageable preFindAll(final Pageable pageable, @NotNull final Object... directives) {
		return pageable;
	}

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

		final MultiplePagedResult result;

		try {
			result = findAllFunction.apply(finalPageable, directives);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(ex, i18nService);
		}

		final var finalResult = posFindAll(result);

		final var dataIn = convertPageableToPublishDataIn(finalPageable);
		final var dataOut = convertMultiplePagedResultToPublishDataOut(finalResult);
		publishEvent(dataIn, dataOut, FIND_ALL_ENTITIES_PAGEABLE);

		LOGGER.debug("Found all by '{}', session '{}'", finalPageable, session);

		return finalResult;
	}

	// ---------------------------------------------------------------------------

	protected String convertSortToPublishDataIn(final Sort sort) {
		return Objects.toString(sort);
	}

	protected String convertOneResultResultToPublishDataOut(final OneResult oneResult) {
		return Objects.toString(oneResult);
	}

	protected Sort preFindFirst(final Sort sort) {
		return sort;
	}

	protected OneResult posFindFirst(final OneResult oneResult) {
		return oneResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final OneResult findFirst(@Nullable final Sort sort) {

		final var session = securityService.getSession();

		LOGGER.debug("Finding first by '{}', session '{}'", sort, session);

		final var finalSort = preFindFirst(sort);

		final OneResult result;

		try {
			result = findFirstFunction.apply(finalSort);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(ex, i18nService);
		}

		final var finalResult = posFindFirst(result);

		final var dataIn = convertSortToPublishDataIn(finalSort);
		final var dataOut = convertOneResultResultToPublishDataOut(finalResult);
		publishEvent(dataIn, dataOut, FIND_ALL_ENTITIES_PAGEABLE);

		LOGGER.debug("Found first by '{}', session '{}'", finalSort, session);

		return finalResult;
	}
}
