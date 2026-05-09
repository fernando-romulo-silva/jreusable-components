package org.reusablecomponents.base.core.application.query.entity.pagination;

import static java.util.Objects.nonNull;

import java.util.function.Consumer;

import org.reusablecomponents.base.core.application.base.BaseFacadeBuilder;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged.ErrorFindAllPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged.PosFindAllPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged.PreFindAllPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted.ErrorFindOneSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted.PosFindOneSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted.PreFindOneSortedFunction;
import org.reusablecomponents.base.core.domain.InterfaceEntity;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * The <code>AbstractQueryPaginationFacade</code> builder's class.
 * 
 * This class is responsible for building the
 * <code>AbstractQueryPaginationFacade</code> object.
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
 * @see AbstractQueryPaginationFacade
 */
public class AbstractQueryPaginationFacadeBuilder<Entity extends InterfaceEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort>
        extends BaseFacadeBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQueryPaginationFacadeBuilder.class);

    /**
     * Check {@link AbstractQueryPaginationFacade#preFindAllPagedFunction
     * AbstractQueryPaginationFacade.preFindAllPagedFunction}.
     * 
     * @see PreFindAllPagedFunction
     */
    public PreFindAllPagedFunction<Pageable> preFindAllPagedFunction;

    /**
     * Check {@link AbstractQueryPaginationFacade#posFindAllPagedFunction
     * AbstractQueryPaginationFacade.posFindAllPagedFunction}.
     * 
     * @see PosFindAllPagedFunction
     */
    public PosFindAllPagedFunction<MultiplePagedResult> posFindAllPagedFunction;

    /**
     * Check {@link AbstractQueryPaginationFacade#errorFindAllPagedFunction
     * AbstractQueryPaginationFacade.errorFindAllPagedFunction}.
     * 
     * @see ErrorFindAllPagedFunction
     */
    public ErrorFindAllPagedFunction<BaseException, Pageable> errorFindAllPagedFunction;

    /**
     * Check {@link AbstractQueryPaginationFacade#preFindOneSortedFunction
     * AbstractQueryPaginationFacade.preFindOneSortedFunction}.
     * 
     * @see PreFindOneSortedFunction
     */
    public PreFindOneSortedFunction<Sort> preFindOneSortedFunction;

    /**
     * Check {@link AbstractQueryPaginationFacade#posFindOneSortedFunction
     * AbstractQueryPaginationFacade.posFindOneSortedFunction}.
     * 
     * @see PosFindOneSortedFunction
     */
    public PosFindOneSortedFunction<OneResult> posFindOneSortedFunction;

    /**
     * Check {@link AbstractQueryPaginationFacade#errorFindOneSortedFunction
     * AbstractQueryPaginationFacade.errorFindOneSortedFunction}.
     * 
     * @see ErrorFindOneSortedFunction
     */
    public ErrorFindOneSortedFunction<BaseException, Sort> errorFindOneSortedFunction;

    /**
     * Constructor for AbstractQueryPaginationFacadeBuilder
     * 
     * @param function A consumer function to initialize the builder, can't be null.
     */
    public AbstractQueryPaginationFacadeBuilder(
            @NotNull final Consumer<? extends AbstractQueryPaginationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort>> function) {
        super(function);

        this.preFindAllPagedFunction = getPreFindAllPagedFunction();
        this.posFindAllPagedFunction = getPosFindAllPagedFunction();
        this.errorFindAllPagedFunction = getErrorFindAllPagedFunction();

        this.preFindOneSortedFunction = getPreFindOneSortedFunction();
        this.posFindOneSortedFunction = getPosFindOneSortedFunction();
        this.errorFindOneSortedFunction = getErrorFindOneSortedFunction();
    }

    /**
     * Gets the pre find all paged function {@link #preFindAllPagedFunction
     * preFindAllPagedFunction}, if it is not set, it will be set with a
     * default function that logs the execution.
     * 
     * @return The pre find all paged function.
     * @see PreFindAllPagedFunction
     */
    private PreFindAllPagedFunction<Pageable> getPreFindAllPagedFunction() {
        return nonNull(preFindAllPagedFunction)
                ? preFindAllPagedFunction
                : (pageable, directives) -> {
                    LOGGER.atDebug().log("Executing default preFindAll, pageable {}, directives {}",
                            pageable, directives);
                    return pageable;
                };
    }

    /**
     * Gets the pos find all paged function {@link #posFindAllPagedFunction
     * posFindAllPagedFunction}, if it is not set, it will be set with a
     * default function that logs the execution and returns the input parameter.
     * 
     * @return The pos find all paged function.
     * @see PosFindAllPagedFunction
     */
    private PosFindAllPagedFunction<MultiplePagedResult> getPosFindAllPagedFunction() {
        return nonNull(posFindAllPagedFunction)
                ? posFindAllPagedFunction
                : (multiplePagedResult, directives) -> {
                    LOGGER.atDebug().log("Executing default posFindAll, multiplePagedResult {}, directives {}",
                            multiplePagedResult, directives);
                    return multiplePagedResult;
                };
    }

    /**
     * Gets the error find all paged function {@link #errorFindAllPagedFunction
     * errorFindAllPagedFunction}, if it is not set, it will be set with a
     * default function that logs the execution and returns the input parameter.
     * 
     * @return The error find all paged function.
     * @see ErrorFindAllPagedFunction
     */
    private ErrorFindAllPagedFunction<BaseException, Pageable> getErrorFindAllPagedFunction() {
        return nonNull(errorFindAllPagedFunction)
                ? errorFindAllPagedFunction
                : (exception, pageable, directives) -> {
                    LOGGER.atDebug().log("Executing default errorFindAll, pageable {}, exception {}, directives {}",
                            pageable, exception, directives);
                    return exception;
                };
    }

    /**
     * Gets the pre find one sorted function {@link #preFindOneSortedFunction
     * preFindOneSortedFunction}, if it is not set, it will be set with a
     * default function that logs the execution.
     * 
     * @return The pre find one sorted function.
     * @see PreFindOneSortedFunction
     */
    private PreFindOneSortedFunction<Sort> getPreFindOneSortedFunction() {
        return nonNull(preFindOneSortedFunction)
                ? preFindOneSortedFunction
                : (sort, directives) -> {
                    LOGGER.atDebug().log("Executing default preFindOne, sort {}, directives {}", sort, directives);
                    return sort;
                };
    }

    /**
     * Gets the pos find one sorted function {@link #posFindOneSortedFunction
     * posFindOneSortedFunction}, if it is not set, it will be set with a
     * default function that logs the execution and returns the input parameter.
     * 
     * @return The pos find one sorted function.
     * @see PosFindOneSortedFunction
     */
    private PosFindOneSortedFunction<OneResult> getPosFindOneSortedFunction() {
        return nonNull(posFindOneSortedFunction)
                ? posFindOneSortedFunction
                : (oneResult, directives) -> {
                    LOGGER.atDebug().log("Executing default posFindOne, oneResult {}, directives {}",
                            oneResult, directives);
                    return oneResult;
                };
    }

    /**
     * Gets the error find one sorted function {@link #errorFindOneSortedFunction
     * errorFindOneSortedFunction}, if it is not set, it will be set with a
     * default function that logs the execution and returns the input parameter.
     * 
     * @return The error find one sorted function.
     * @see ErrorFindOneSortedFunction
     */
    private ErrorFindOneSortedFunction<BaseException, Sort> getErrorFindOneSortedFunction() {
        return nonNull(errorFindOneSortedFunction)
                ? errorFindOneSortedFunction
                : (exception, sort, directives) -> {
                    LOGGER.atDebug().log("Executing default errorFindOne, sort {}, exception {}, directives {}", sort,
                            exception, directives);
                    return exception;
                };
    }
}
