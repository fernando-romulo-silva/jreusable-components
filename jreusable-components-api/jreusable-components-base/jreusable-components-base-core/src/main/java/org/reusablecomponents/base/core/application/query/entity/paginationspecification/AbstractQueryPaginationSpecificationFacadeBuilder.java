package org.reusablecomponents.base.core.application.query.entity.paginationspecification;

import static java.util.Objects.nonNull;

import java.util.function.Consumer;

import org.reusablecomponents.base.core.application.base.BaseFacadeBuilder;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_by_specification_paged.ErrorFindBySpecificationPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_by_specification_paged.PosFindBySpecificationPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_by_specification_paged.PreFindBySpecificationPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_one_by_specification_sorted.ErrorFindOneBySpecificationSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_one_by_specification_sorted.PosFindOneBySpecificationSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_one_by_specification_sorted.PreFindOneBySpecificationSortedFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public abstract class AbstractQueryPaginationSpecificationFacadeBuilder<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Specification, Pageable, Sort>
		extends BaseFacadeBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(
			AbstractQueryPaginationSpecificationFacadeBuilder.class);

	public PreFindBySpecificationPagedFunction<Specification, Pageable> preFindBySpecificationPagedFunction;

	public PosFindBySpecificationPagedFunction<MultiplePagedResult> posFindBySpecificationPagedFunction;

	public ErrorFindBySpecificationPagedFunction<Specification, Pageable> errorFindBySpecificationPagedFunction;

	public PreFindOneBySpecificationSortedFunction<Specification, Sort> preFindOneBySpecificationSortedFunction;

	public PosFindOneBySpecificationSortedFunction<OneResult> posFindOneBySpecificationSortedFunction;

	public ErrorFindOneBySpecificationSortedFunction<Specification, Sort> errorFindOneBySpecificationSortedFunction;

	/**
	 * 
	 */
	protected AbstractQueryPaginationSpecificationFacadeBuilder(
			final Consumer<? extends AbstractQueryPaginationSpecificationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Specification, Pageable, Sort>> function) {
		super(function);

		this.preFindBySpecificationPagedFunction = getPreFindBySpecificationPagedFunction();
		this.posFindBySpecificationPagedFunction = getPosFindBySpecificationPagedFunction();
		this.errorFindBySpecificationPagedFunction = getErrorFindBySpecificationPagedFunction();

		this.preFindOneBySpecificationSortedFunction = getPreFindOneBySpecificationSortedFunction();
		this.posFindOneBySpecificationSortedFunction = getPosFindOneBySpecificationSortedFunction();
		this.errorFindOneBySpecificationSortedFunction = getErrorFindOneBySpecificationSortedFunction();
	}

	private PreFindOneBySpecificationSortedFunction<Specification, Sort> getPreFindOneBySpecificationSortedFunction() {
		return nonNull(preFindOneBySpecificationSortedFunction)
				? preFindOneBySpecificationSortedFunction
				: (specification, sort, directives) -> {
					LOGGER.debug(
							"Default preFindOneBySpecificationSortedFunction, specification {}, sort {}, directives {}",
							specification, sort, directives);
					return specification;
				};
	}

	private PosFindOneBySpecificationSortedFunction<OneResult> getPosFindOneBySpecificationSortedFunction() {
		return nonNull(posFindOneBySpecificationSortedFunction)
				? posFindOneBySpecificationSortedFunction
				: (oneResult, directives) -> {
					LOGGER.debug(
							"Default preFindOneBySpecificationSortedFunction, oneResult {}, directives {}",
							oneResult, directives);
					return oneResult;
				};
	}

	private ErrorFindOneBySpecificationSortedFunction<Specification, Sort> getErrorFindOneBySpecificationSortedFunction() {
		return nonNull(errorFindOneBySpecificationSortedFunction)
				? errorFindOneBySpecificationSortedFunction
				: (exception, specification, sort, directives) -> {
					LOGGER.debug(
							"Default errorFindOneBySpecificationSortedFunction, exception {}, specification {}, sort {}, directives {}",
							exception, specification, sort, directives);
					return exception;
				};
	}

	private PreFindBySpecificationPagedFunction<Specification, Pageable> getPreFindBySpecificationPagedFunction() {
		return nonNull(preFindBySpecificationPagedFunction)
				? preFindBySpecificationPagedFunction
				: (specification, pageable, directives) -> {
					LOGGER.debug(
							"Default preFindBySpecificationPagedFunction, specification {}, pageable {}, directives {}",
							specification, pageable, directives);
					return specification;
				};
	}

	private PosFindBySpecificationPagedFunction<MultiplePagedResult> getPosFindBySpecificationPagedFunction() {
		return nonNull(posFindBySpecificationPagedFunction)
				? posFindBySpecificationPagedFunction
				: (multiplePagedResult, directives) -> {
					LOGGER.debug(
							"Default posFindBySpecificationPagedFunction, multiplePagedResult {}, directives {}",
							multiplePagedResult, directives);
					return multiplePagedResult;
				};
	}

	private ErrorFindBySpecificationPagedFunction<Specification, Pageable> getErrorFindBySpecificationPagedFunction() {
		return nonNull(errorFindBySpecificationPagedFunction)
				? errorFindBySpecificationPagedFunction
				: (exception, specification, pageable, directives) -> {
					LOGGER.debug(
							"Default errorFindBySpecificationPagedFunction, exception {}, multiplePagedResult {}, pageable {}, directives {}",
							exception, specification, pageable, directives);
					return exception;
				};
	}
}
