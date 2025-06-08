package org.reusablecomponents.base.core.application.query.entity.paginationspecification;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.function.Consumer;

import org.apache.commons.lang3.function.TriFunction;
import org.reusablecomponents.base.core.application.base.BaseFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotNull;

/**
 * The <code>EntityQueryPaginationSpecificationFacade</code> builder's
 * class.
 */
public class QueryPaginationSpecificationFacadeBuilder<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification>
		extends BaseFacadeBuilder {

	/**
	 * Function that executes find By Specificationid algorithm
	 */
	public TriFunction<Pageable, Specification, Object[], MultiplePagedResult> findBySpecificationFunction;

	/**
	 * Function that executes find One By Specificationid and Sort algorithm
	 */
	public TriFunction<Sort, Specification, Object[], OneResult> findOneByFunctionWithOrder;

	/**
	 * Default constructor.
	 * 
	 * @param function Consumer function
	 */
	public QueryPaginationSpecificationFacadeBuilder(
			@NotNull final Consumer<QueryPaginationSpecificationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification>> function) {

		super(function);

		function.accept(this);

		checkNotNull(findBySpecificationFunction, "Please pass a non-null 'findBySpecificationFunction'");
		checkNotNull(findOneByFunctionWithOrder, "Please pass a non-null 'findOneByFunctionWithOrder'");
	}

}
