package org.reusablecomponents.base.core.application.query.entity.pagination;

import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged.FindAllPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted.FindOneSortedFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * The default <code>InterfaceEntityQueryPaginationFacade</code>'s
 * implementation.
 */
public non-sealed class QueryPaginationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort>
		extends AbstractQueryPaginationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort>
		implements InterfaceQueryPaginationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryPaginationFacade.class);

	/**
	 * Function that executes the find all operation in the
	 * {@link #findAllPaged(Object, Object...) findAllPaged} method
	 */
	protected final FindAllPagedFunction<Pageable, MultiplePagedResult> findAllPagedFunction;

	/**
	 * Function that executes the find one operation in the
	 * {@link #findOneSorted(Object, Object...) findOneSorted} method
	 */
	protected final FindOneSortedFunction<Sort, OneResult> findOneSortedFunction;

	/**
	 * Default constructor
	 * 
	 * @param builder Object in charge to construct this one
	 */
	protected QueryPaginationFacade(
			final QueryPaginationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> builder) {
		super(builder);
		this.findAllPagedFunction = builder.findAllPagedFunction;
		this.findOneSortedFunction = builder.findOneSortedFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SafeVarargs
	public final MultiplePagedResult findAllPaged(final Pageable pageable, final Object... directives) {
		LOGGER.atDebug().log("Executing default findAll, pageable {}, directives {}", pageable, directives);

		final var multipleResult = execute(
				pageable, getPreFindAllPagedFunction(),
				getFindAllPagedFunction(), getPosFindAllPagedFunction(),
				getErrorFindAllPagedFunction(), directives);

		LOGGER.atDebug().log("Default findAll executed, multipleResult {}, directives {}", multipleResult, directives);
		return multipleResult;
	}

	/**
	 * Gets the find all paged function {@link #findAllPagedFunction},
	 * provided by the builder.
	 * 
	 * @return the find all paged function
	 * 
	 * @see FindAllPagedFunction
	 */
	@NotNull
	protected FindAllPagedFunction<Pageable, MultiplePagedResult> getFindAllPagedFunction() {
		LOGGER.atDebug().log("Returning findAllPagedFunction function {}", findAllPagedFunction.getName());
		return findAllPagedFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OneResult findOneSorted(final Sort sort, final Object... directives) {
		LOGGER.atDebug().log("Executing default findOne, pageable {}, directives {}", sort, directives);

		final var oneResult = execute(
				sort, getPreFindOneSortedFunction(),
				getFindOneSortedFunction(), getPosFindOneSortedFunction(),
				getErrorFindOneSortedFunction(), directives);

		LOGGER.atDebug().log("Default findOne executed, oneResult {}, directives {}", oneResult, directives);
		return oneResult;
	}

	@NotNull
	protected FindOneSortedFunction<Sort, OneResult> getFindOneSortedFunction() {
		LOGGER.atDebug().log("Returning findOneSortedFunction function {}", findOneSortedFunction.getName());
		return findOneSortedFunction;
	}
}
