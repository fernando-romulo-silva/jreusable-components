package org.reusablecomponents.base.core.application.query.entity.paginationspecification;

import static java.util.Objects.nonNull;

import java.util.function.Consumer;

import org.apache.commons.lang3.function.TriFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_by_specification_paged.FindBySpecificationPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_one_by_specification_sorted.FindOneBySpecificationSortedFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotNull;

/**
 * The <code>EntityQueryPaginationSpecificationFacade</code> builder's
 * class.
 */
public class QueryPaginationSpecificationFacadeBuilder<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Specification, Pageable, Sort>
		extends
		AbstractQueryPaginationSpecificationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Specification, Pageable, Sort> {
	/**
	 * Function that executes find By Specificationid algorithm
	 */
	public FindBySpecificationPagedFunction<Specification, Pageable, MultiplePagedResult> findPagedAndSpecificatedByFunction;

	/**
	 * Function that executes find One By Specificationid and Sort algorithm
	 */
	public FindOneBySpecificationSortedFunction<Specification, Sort, OneResult> findOneSortedSpecificatedByFunction;

	/**
	 * Default constructor.
	 * 
	 * @param function Consumer function
	 */
	public QueryPaginationSpecificationFacadeBuilder(
			@NotNull final Consumer<QueryPaginationSpecificationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Specification, Pageable, Sort>> function) {
		super(function);

		this.findPagedAndSpecificatedByFunction = nonNull(findPagedAndSpecificatedByFunction)
				? findPagedAndSpecificatedByFunction
				: (specification, pageable, directives) -> {
					throw new UnsupportedOperationException(
							"Unimplemented function 'findPagedAndSpecificatedByFunction'");
				};

		this.findOneSortedSpecificatedByFunction = nonNull(findOneSortedSpecificatedByFunction)
				? findOneSortedSpecificatedByFunction
				: (specification, sort, directives) -> {
					throw new UnsupportedOperationException(
							"Unimplemented function 'findOneSortedSpecificatedByFunction'");
				};
	}
}
