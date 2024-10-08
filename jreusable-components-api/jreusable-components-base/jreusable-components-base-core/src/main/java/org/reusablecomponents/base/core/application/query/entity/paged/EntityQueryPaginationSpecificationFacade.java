package org.reusablecomponents.base.core.application.query.entity.paged;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.FIND_ALL_ENTITIES_PAGEABLE;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.FIND_ENTITIES_BY_SPECIFICATION_PAGEABLE;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.Objects;

import org.apache.commons.lang3.function.TriFunction;
import org.reusablecomponents.base.core.application.base.EntiyBaseFacade;
import org.reusablecomponents.base.core.application.base.EntiyBaseFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Supplier;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 * @param <MultiplePagedResult>
 * @param <Pageable>
 * @param <Specification>
 * @param <Sort>
 */
public non-sealed class EntityQueryPaginationSpecificationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification>
		extends EntiyBaseFacade<Entity, Id>
		implements
		InterfaceEntityQueryPaginationSpecificationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntityQueryPaginationSpecificationFacade.class);

	protected final TriFunction<Specification, Pageable, Object[], MultiplePagedResult> findBySpecificationFunction;

	protected final TriFunction<Specification, Sort, Object[], OneResult> findOneByFunctionWithOrder;

	/**
	 * @param findBySpecificationFunction
	 * @param findOneByFunctionWithOrder
	 */
	public EntityQueryPaginationSpecificationFacade() {
		super(new EntiyBaseFacadeBuilder($ -> {

		}));

		// @NotNull final TriFunction<Specification, Pageable, Object[],
		// MultiplePagedResult> findBySpecificationFunction,
		// @NotNull final TriFunction<Specification, Sort, Object[], OneResult>
		// findOneByFunctionWithOrder
		this.findBySpecificationFunction = null;
		this.findOneByFunctionWithOrder = null;
	}

	// ---------------------------------------------------------------------------

	protected Supplier<String> convertPageableToPublishData(final Pageable pageable,
			final Specification specification) {
		return () -> Objects.toString(pageable).concat(SPACE).concat(Objects.toString(specification));
	}

	protected String convertMultiplePagedResultToPublishData(final MultiplePagedResult multiplePagedResult) {
		return Objects.toString(multiplePagedResult);
	}

	protected Entry<Pageable, Specification> preFindBy(final Pageable pageable, final Specification specification) {
		return new SimpleEntry<>(pageable, specification);
	}

	protected MultiplePagedResult posFindBy(final MultiplePagedResult multiplePagedResult) {
		return multiplePagedResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MultiplePagedResult findBy(@Nullable final Pageable pageable, @Nullable final Specification specification,
			@NotNull final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Finding by '{}' & '{}', session '{}'", pageable, specification, session);

		final var finalEntry = preFindBy(pageable, specification);

		final var finalPageable = finalEntry.getKey();
		final var finalSpecification = finalEntry.getValue();

		final MultiplePagedResult result;

		try {
			result = findBySpecificationFunction.apply(finalSpecification, finalPageable, directives);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(ex, i18nService);
		}

		final var finalResult = posFindBy(result);

		final var dataIn = convertPageableToPublishData(finalPageable, finalSpecification);
		final var dataOut = convertMultiplePagedResultToPublishData(finalResult);
		publishEvent(dataIn, dataOut, FIND_ENTITIES_BY_SPECIFICATION_PAGEABLE);

		LOGGER.debug("Found by '{}', session '{}'", finalPageable, session);

		return finalResult;
	}

	// ---------------------------------------------------------------------------

	protected Supplier<String> convertSortToPublishData(final Specification specification, final Sort sort) {
		return () -> Objects.toString(specification).concat(SPACE).concat(Objects.toString(sort));
	}

	protected Supplier<String> convertOneResultResultToPublishData(final OneResult oneResult) {
		return () -> Objects.toString(oneResult);
	}

	protected Entry<Specification, Sort> preFindOneBy(final Specification specification, final Sort sort) {
		return new SimpleEntry<>(specification, sort);
	}

	protected OneResult posFindOneBy(final OneResult oneResult) {
		return oneResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OneResult findOneBy(@Nullable final Specification specification, @Nullable final Sort sort) {

		final var session = securityService.getSession();

		LOGGER.debug("Finding one by '{}' & '{}', session '{}'", specification, sort, session);

		final var finalEntry = preFindOneBy(specification, sort);
		final var finalSpecification = finalEntry.getKey();
		final var finalSort = finalEntry.getValue();

		final OneResult result;

		try {
			result = findOneByFunctionWithOrder.apply(finalSpecification, finalSort, null);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(ex, i18nService);
		}

		final var finalResult = posFindOneBy(result);

		final var dataIn = convertSortToPublishData(finalSpecification, finalSort);
		final var dataOut = convertOneResultResultToPublishData(finalResult);
		publishEvent(dataIn, dataOut, FIND_ALL_ENTITIES_PAGEABLE);

		LOGGER.debug("Found first by '{}' & '{}', session '{}'", finalSpecification, sort, session);

		return finalResult;
	}
}
