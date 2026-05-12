package org.reusablecomponents.base.core.application.query.entity.paginationspecification;

import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_by_specification_paged.FindBySpecificationPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_one_by_specification_sorted.FindOneBySpecificationSortedFunction;
import org.reusablecomponents.base.core.domain.InterfaceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * This class is responsible for implementing query with pagination and
 * specification defined in the
 * {@link InterfaceQuerySpecificationPaginationFacade} interface using
 * functional approach. <br />
 * 
 * For each query operation, this class use a function:
 * <ul>
 * <li>{@link #findBySpecificationPagedFunction} function used to execute the
 * {@link #findByPaginationPaged(Specification, Pageable, Object...)} method.
 * 
 * <li>{@link #findOneBySpecificationSortedFunction} function used to execute
 * the {@link #findOneByPaginationSorted(Specification, Sort, Object...)}
 * method.
 * </ul>
 * 
 * Each query operation also have pre and pos functions, used to execute logic
 * before and after the main function, and an error function, used to execute
 * logic in case of error defined in
 * {@link AbstractQueryPaginationSpecificationFacade}.
 * 
 * <p>
 * All functions used in this class are provided by the
 * {@link QueryPaginationSpecificationFacadeBuilder} builder.
 * </p>
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 * 
 * @see AbstractQueryPaginationSpecificationFacade
 * @see InterfaceQuerySpecificationPaginationFacade
 */
public non-sealed class QueryPaginationSpecificationFacade<Entity extends InterfaceEntity<Id>, Id, OneResult, MultiplePagedResult, Specification, Pageable, Sort>
		extends
		AbstractQueryPaginationSpecificationFacade<Entity, Id, OneResult, MultiplePagedResult, Specification, Pageable, Sort>
		implements
		InterfaceQuerySpecificationPaginationFacade<Entity, Id, OneResult, MultiplePagedResult, Specification, Pageable, Sort> {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryPaginationSpecificationFacade.class);

	protected final FindBySpecificationPagedFunction<Specification, Pageable, MultiplePagedResult> findBySpecificationPagedFunction;

	protected final FindOneBySpecificationSortedFunction<Specification, Sort, OneResult> findOneBySpecificationSortedFunction;

	/**
	 * Default constructor, used by the builder to construct this class.
	 * 
	 * @param builder Object in charge to construct this one, can't be null
	 * 
	 * @throws NullPointerException if the builder is null
	 * 
	 * @see QueryPaginationSpecificationFacadeBuilder
	 */
	protected QueryPaginationSpecificationFacade(
			final QueryPaginationSpecificationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Specification, Pageable, Sort> builder) {
		super(builder);
		this.findBySpecificationPagedFunction = builder.findPagedAndSpecificatedByFunction;
		this.findOneBySpecificationSortedFunction = builder.findOneSortedSpecificatedByFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MultiplePagedResult findByPaginationPaged(
			final Specification specification,
			final Pageable pageable,
			final Object... directives) {
		LOGGER.atDebug().log("Executing default findByPaginationPaged, specification {}, pageable {}, directives {}",
				specification, pageable, directives);

		final var multiplePagedResult = execute(
				specification, pageable,
				getPreFindBySpecificationPagedFunction(),
				getFindBySpecificationPagedFunction(),
				getPosFindBySpecificationPagedFunction(),
				getErrorFindBySpecificationPagedFunction(),
				directives);

		LOGGER.atDebug().log("Default findByPaginationPaged executed, multiplePagedResult {}, directives {}",
				multiplePagedResult, directives);
		return multiplePagedResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OneResult findOneByPaginationSorted(
			final Specification specification,
			final Sort sort,
			final Object... directives) {
		LOGGER.atDebug().log("Executing default findOneByPaginationSorted, sort {}, specification {}, directives {}",
				sort, specification, directives);

		final var oneResult = execute(
				specification, sort,
				getPreFindOneBySpecificationSortedFunction(),
				getFindOneBySpecificationSortedFunction(),
				getPosFindOneBySpecificationSortedFunction(),
				getErrorFindOneBySpecificationSortedFunction(), directives);

		LOGGER.atDebug().log("Default findOneByPaginationSorted executed, oneResult {}, directives {}",
				oneResult, directives);
		return oneResult;
	}

	/**
	 * Gets the pre find all function {@link #findBySpecificationPagedFunction},
	 * provided by the builder.
	 * 
	 * @return The pre find by specification paged function.
	 * 
	 * @see FindBySpecificationPagedFunction
	 */
	@NotNull
	protected FindBySpecificationPagedFunction<Specification, Pageable, MultiplePagedResult> getFindBySpecificationPagedFunction() {
		LOGGER.atDebug()
				.log("Returning findBySpecificationPagedFunction function {}",
						findBySpecificationPagedFunction.getName());
		return findBySpecificationPagedFunction;
	}

	/**
	 * Gets the pre find one by specification sorted function
	 * {@link #findOneBySpecificationSortedFunction}, provided by the builder.
	 * 
	 * @return The pre find one by specification sorted function.
	 * 
	 * @see FindOneBySpecificationSortedFunction
	 */
	@NotNull
	protected FindOneBySpecificationSortedFunction<Specification, Sort, OneResult> getFindOneBySpecificationSortedFunction() {
		LOGGER.atDebug()
				.log("Returning findOneBySpecificationSortedFunction function {}",
						findOneBySpecificationSortedFunction.getName());
		return findOneBySpecificationSortedFunction;
	}
}
