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
import org.reusablecomponents.base.core.domain.InterfaceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The <code>AbstractQueryPaginationSpecificationFacade</code> builder's class.
 * 
 * This class is responsible for building the
 * <code>AbstractQueryPaginationSpecificationFacade</code> object.
 * 
 * For each function in this class, if it is not set, it will be set with a
 * default function that just logs the execution and returns the input
 * parameters, example: "Default function 'functionName', input parameters:
 * ['parameter1']".
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 * 
 * @see BaseFacadeBuilder
 * @see AbstractQueryPaginationSpecificationFacade
 */
public abstract class AbstractQueryPaginationSpecificationFacadeBuilder<Entity extends InterfaceEntity<Id>, Id, OneResult, MultiplePagedResult, Specification, Pageable, Sort>
        extends BaseFacadeBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            AbstractQueryPaginationSpecificationFacadeBuilder.class);

    /**
     * Check
     * {@link AbstractQueryPaginationSpecificationFacade#preFindBySpecificationPagedFunction
     * AbstractQueryPaginationSpecificationFacade.preFindBySpecificationPagedFunction}.
     * 
     * @see PreFindBySpecificationPagedFunction
     */
    public PreFindBySpecificationPagedFunction<Specification, Pageable> preFindBySpecificationPagedFunction;

    /**
     * Check
     * {@link AbstractQueryPaginationSpecificationFacade#posFindBySpecificationPagedFunction
     * AbstractQueryPaginationSpecificationFacade.posFindBySpecificationPagedFunction}.
     * 
     * @see PosFindBySpecificationPagedFunction
     */
    public PosFindBySpecificationPagedFunction<MultiplePagedResult> posFindBySpecificationPagedFunction;

    /**
     * Check
     * {@link AbstractQueryPaginationSpecificationFacade#errorFindBySpecificationPagedFunction
     * AbstractQueryPaginationSpecificationFacade.errorFindBySpecificationPagedFunction}.
     * 
     * @see ErrorFindBySpecificationPagedFunction
     */
    public ErrorFindBySpecificationPagedFunction<Specification, Pageable> errorFindBySpecificationPagedFunction;

    /**
     * Check
     * {@link AbstractQueryPaginationSpecificationFacade#preFindOneBySpecificationSortedFunction
     * AbstractQueryPaginationSpecificationFacade.preFindOneBySpecificationSortedFunction}.
     * 
     * @see PreFindOneBySpecificationSortedFunction
     */
    public PreFindOneBySpecificationSortedFunction<Specification, Sort> preFindOneBySpecificationSortedFunction;

    /**
     * Check
     * {@link AbstractQueryPaginationSpecificationFacade#posFindOneBySpecificationSortedFunction
     * AbstractQueryPaginationSpecificationFacade.posFindOneBySpecificationSortedFunction}.
     * 
     * @see PosFindOneBySpecificationSortedFunction
     */
    public PosFindOneBySpecificationSortedFunction<OneResult> posFindOneBySpecificationSortedFunction;

    /**
     * Check
     * {@link AbstractQueryPaginationSpecificationFacade#errorFindOneBySpecificationSortedFunction
     * AbstractQueryPaginationSpecificationFacade.errorFindOneBySpecificationSortedFunction}.
     * 
     * @see ErrorFindOneBySpecificationSortedFunction
     */
    public ErrorFindOneBySpecificationSortedFunction<Specification, Sort> errorFindOneBySpecificationSortedFunction;

    /**
     * Constructor for AbstractQueryPaginationSpecificationFacadeBuilder
     * 
     * @param function A consumer function to initialize the builder, can't be null.
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

    /**
     * Gets the pre find one by specification sorted function
     * {@link #preFindOneBySpecificationSortedFunction
     * preFindOneBySpecificationSortedFunction}, if it is not set, it will be set
     * with a default function that logs the execution.
     * 
     * @return The pre find one by specification sorted function.
     * @see PreFindOneBySpecificationSortedFunction
     */
    private PreFindOneBySpecificationSortedFunction<Specification, Sort> getPreFindOneBySpecificationSortedFunction() {
        return nonNull(preFindOneBySpecificationSortedFunction)
                ? preFindOneBySpecificationSortedFunction
                : (specification, sort, directives) -> {
                    LOGGER.atDebug().log(
                            "Default preFindOneBySpecificationSortedFunction, specification {}, sort {}, directives {}",
                            specification, sort, directives);
                    return specification;
                };
    }

    /**
     * Gets the pos find one by specification sorted function
     * {@link #posFindOneBySpecificationSortedFunction
     * posFindOneBySpecificationSortedFunction}, if it is not set, it will be set
     * with a default function that logs the execution and returns the input
     * parameter oneResult.
     * 
     * @return The pos find one by specification sorted function.
     * @see PosFindOneBySpecificationSortedFunction
     */
    private PosFindOneBySpecificationSortedFunction<OneResult> getPosFindOneBySpecificationSortedFunction() {
        return nonNull(posFindOneBySpecificationSortedFunction)
                ? posFindOneBySpecificationSortedFunction
                : (oneResult, directives) -> {
                    LOGGER.atDebug().log(
                            "Default posFindOneBySpecificationSortedFunction, oneResult {}, directives {}",
                            oneResult, directives);
                    return oneResult;
                };
    }

    /**
     * Gets the error find one by specification sorted function
     * {@link #errorFindOneBySpecificationSortedFunction
     * errorFindOneBySpecificationSortedFunction}, if it is not set, it will be set
     * with a default function that logs the execution and returns the input
     * parameter exception.
     * 
     * @return The error find one by specification sorted function.
     * @see ErrorFindOneBySpecificationSortedFunction
     */
    private ErrorFindOneBySpecificationSortedFunction<Specification, Sort> getErrorFindOneBySpecificationSortedFunction() {
        return nonNull(errorFindOneBySpecificationSortedFunction)
                ? errorFindOneBySpecificationSortedFunction
                : (exception, specification, sort, directives) -> {
                    LOGGER.atDebug().log(
                            "Default errorFindOneBySpecificationSortedFunction, exception {}, specification {}, sort {}, directives {}",
                            exception, specification, sort, directives);
                    return exception;
                };
    }

    /**
     * Gets the pre find by specification paged function
     * {@link #preFindBySpecificationPagedFunction
     * preFindBySpecificationPagedFunction}, if it is not set, it will be set
     * with a default function that logs the execution and returns the input
     * parameter specification.
     * 
     * @return The pre find by specification paged function.
     * @see PreFindBySpecificationPagedFunction
     */
    private PreFindBySpecificationPagedFunction<Specification, Pageable> getPreFindBySpecificationPagedFunction() {
        return nonNull(preFindBySpecificationPagedFunction)
                ? preFindBySpecificationPagedFunction
                : (specification, pageable, directives) -> {
                    LOGGER.atDebug().log(
                            "Default preFindBySpecificationPagedFunction, specification {}, pageable {}, directives {}",
                            specification, pageable, directives);
                    return specification;
                };
    }

    /**
     * Gets the post find by specification paged function
     * {@link #posFindBySpecificationPagedFunction
     * posFindBySpecificationPagedFunction}, if it is not set, it will be set
     * with a default function that logs the execution and returns the input
     * parameter multiplePagedResult.
     * 
     * @return The post find by specification paged function.
     * @see PosFindBySpecificationPagedFunction
     */
    private PosFindBySpecificationPagedFunction<MultiplePagedResult> getPosFindBySpecificationPagedFunction() {
        return nonNull(posFindBySpecificationPagedFunction)
                ? posFindBySpecificationPagedFunction
                : (multiplePagedResult, directives) -> {
                    LOGGER.atDebug().log(
                            "Default posFindBySpecificationPagedFunction, multiplePagedResult {}, directives {}",
                            multiplePagedResult, directives);
                    return multiplePagedResult;
                };
    }

    /**
     * Gets the error find by specification paged function
     * {@link #errorFindBySpecificationPagedFunction
     * errorFindBySpecificationPagedFunction}, if it is not set, it will be set
     * with a default function that logs the execution and returns the input
     * parameter exception.
     * 
     * @return The error find by specification paged function.
     * @see ErrorFindBySpecificationPagedFunction
     */
    private ErrorFindBySpecificationPagedFunction<Specification, Pageable> getErrorFindBySpecificationPagedFunction() {
        return nonNull(errorFindBySpecificationPagedFunction)
                ? errorFindBySpecificationPagedFunction
                : (exception, specification, pageable, directives) -> {
                    LOGGER.atDebug().log(
                            "Default errorFindBySpecificationPagedFunction, exception {}, specification {}, pageable {}, directives {}",
                            exception, specification, pageable, directives);
                    return exception;
                };
    }
}
