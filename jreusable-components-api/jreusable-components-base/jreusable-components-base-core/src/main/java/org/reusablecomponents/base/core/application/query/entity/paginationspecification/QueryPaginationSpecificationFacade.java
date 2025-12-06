package org.reusablecomponents.base.core.application.query.entity.paginationspecification;

import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_by_specification_paged.FindBySpecificationPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_one_by_specification_sorted.FindOneBySpecificationSortedFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * Interface responsible for establishing contracts to retrieve objects, common
 * to all projects.
 */
public non-sealed class QueryPaginationSpecificationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Specification, Pageable, Sort>
		extends
		AbstractQueryPaginationSpecificationFacade<Entity, Id, OneResult, MultiplePagedResult, Specification, Pageable, Sort>
		implements
		InterfaceQuerySpecificationPaginationFacade<Entity, Id, OneResult, MultiplePagedResult, Specification, Pageable, Sort> {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryPaginationSpecificationFacade.class);

	protected final FindBySpecificationPagedFunction<Specification, Pageable, MultiplePagedResult> findBySpecificationPagedFunction;

	protected final FindOneBySpecificationSortedFunction<Specification, Sort, OneResult> findOneBySpecificationSortedFunction;

	/**
	 * Default constructor
	 * 
	 * @param builder Object in charge to construct this one
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
		LOGGER.debug("Executing default findByPaginationPaged, specification {}, pageable {}, directives {}",
				specification, pageable, directives);

		final var multiplePagedResult = execute(
				specification, pageable,
				getPreFindBySpecificationPagedFunction(),
				getFindBySpecificationPagedFunction(),
				getPosFindBySpecificationPagedFunction(),
				getErrorFindBySpecificationPagedFunction(),
				directives);

		LOGGER.debug("Default findByPaginationPaged executed, multiplePagedResult {}, directives {}",
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
		LOGGER.debug("Executing default findOneByPaginationSorted, sort {}, specification {}, directives {}",
				sort, specification, directives);

		final var oneResult = execute(
				specification, sort,
				getPreFindOneBySpecificationSortedFunction(),
				getFindOneBySpecificationSortedFunction(),
				getPosFindOneBySpecificationSortedFunction(),
				getErrorFindOneBySpecificationSortedFunction(), directives);

		LOGGER.debug("Default findOneByPaginationSorted executed, oneResult {}, directives {}",
				oneResult, directives);
		return oneResult;
	}

	@NotNull
	protected FindBySpecificationPagedFunction<Specification, Pageable, MultiplePagedResult> getFindBySpecificationPagedFunction() {
		LOGGER.debug("Returning findBySpecificationPagedFunction function {}",
				findBySpecificationPagedFunction.getName());
		return findBySpecificationPagedFunction;
	}

	@NotNull
	protected FindOneBySpecificationSortedFunction<Specification, Sort, OneResult> getFindOneBySpecificationSortedFunction() {
		LOGGER.debug("Returning findOneBySpecificationSortedFunction function {}",
				findOneBySpecificationSortedFunction.getName());
		return findOneBySpecificationSortedFunction;
	}
}
