package org.reusablecomponents.base.core.application.query.entity.pagination;

import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged.ErrorFindAllPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged.PosFindAllPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged.PreFindAllPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted.ErrorFindOneSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted.PosFindOneSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted.PreFindOneSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.AbstractQueryFacadeBuilder;
import org.reusablecomponents.base.core.domain.InterfaceEntity;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * Abstract class for query facades with pagination, providing common
 * functionality for handling pre, post, and error functions for various query
 * operations.
 * 
 * This class provide functions for pre, post, and error execution of the main
 * functions defined in {@link QueryPaginationFacade}.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 * 
 * @see QueryPaginationFacade
 * @see BaseFacade
 */
public sealed abstract class AbstractQueryPaginationFacade<Entity extends InterfaceEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort>
        extends BaseFacade<Entity, Id>
        permits QueryPaginationFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQueryPaginationFacade.class);

    /**
     * Function executed in
     * {@link QueryPaginationFacade#findAllPaged(Object, Object...)} method before
     * the {@link QueryPaginationFacade#findAllPagedFunction}, use it to configure,
     * change, etc. the input.
     */
    protected PreFindAllPagedFunction<Pageable> preFindAllPagedFunction;

    /**
     * Function executed in
     * {@link QueryPaginationFacade#findAllPaged(Object, Object...)} method after
     * the {@link QueryPaginationFacade#findAllPagedFunction}, use it to configure,
     * change, etc. the result.
     */
    protected PosFindAllPagedFunction<MultiplePagedResult> posFindAllPagedFunction;

    /**
     * Function executed in
     * {@link QueryPaginationFacade#findAllPaged(Object, Object...)} method to
     * handle {@link QueryPaginationFacade#findAllPagedFunction} errors.
     */
    protected ErrorFindAllPagedFunction<BaseException, Pageable> errorFindAllPagedFunction;

    /**
     * Function executed in
     * {@link QueryPaginationFacade#findOneSorted(Object, Object...)} method before
     * the {@link QueryPaginationFacade#findOneSortedFunction}, use it to configure,
     * change, etc. the input.
     */
    protected PreFindOneSortedFunction<Sort> preFindOneSortedFunction;

    /**
     * Function executed in
     * {@link QueryPaginationFacade#findOneSorted(Object, Object...)} method after
     * the {@link QueryPaginationFacade#findOneSortedFunction}, use it to configure,
     * change, etc. the result.
     */
    protected PosFindOneSortedFunction<OneResult> posFindOneSortedFunction;

    /**
     * Function executed in
     * {@link QueryPaginationFacade#findOneSorted(Object, Object...)} method to
     * handle {@link QueryPaginationFacade#findOneSortedFunction} errors.
     */
    protected ErrorFindOneSortedFunction<BaseException, Sort> errorFindOneSortedFunction;

    /**
     * Default constructor, used by the builder to construct this class.
     * 
     * @param builder The builder used to construct this class, can't be null
     * 
     * @throws NullPointerException if the builder is null
     * 
     * @see AbstractQueryFacadeBuilder
     */
    protected AbstractQueryPaginationFacade(
            @NotNull final AbstractQueryPaginationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> builder) {
        super(builder);

        this.preFindAllPagedFunction = builder.preFindAllPagedFunction;
        this.posFindAllPagedFunction = builder.posFindAllPagedFunction;
        this.errorFindAllPagedFunction = builder.errorFindAllPagedFunction;

        this.preFindOneSortedFunction = builder.preFindOneSortedFunction;
        this.posFindOneSortedFunction = builder.posFindOneSortedFunction;
        this.errorFindOneSortedFunction = builder.errorFindOneSortedFunction;
    }

    /**
     * Gets the pre find all paged function {@link #preFindAllPagedFunction},
     * provided by the builder.
     * 
     * @return the pre find all paged function
     * 
     * @see PreFindAllPagedFunction
     */
    @NotNull
    public PreFindAllPagedFunction<Pageable> getPreFindAllPagedFunction() {
        LOGGER.atDebug().log("Returning preFindAllPagedFunction function {}", preFindAllPagedFunction.getName());
        return preFindAllPagedFunction;
    }

    /**
     * Gets the pos find all paged function {@link #posFindAllPagedFunction},
     * provided by the builder.
     * 
     * @return the pos find all paged function
     * 
     * @see PosFindAllPagedFunction
     */
    @NotNull
    public PosFindAllPagedFunction<MultiplePagedResult> getPosFindAllPagedFunction() {
        LOGGER.atDebug().log("Returning posFindAllPagedFunction function {}", posFindAllPagedFunction.getName());
        return posFindAllPagedFunction;
    }

    /**
     * Gets the error find all paged function {@link #errorFindAllPagedFunction},
     * provided by the builder.
     * 
     * @return the error find all paged function
     * 
     * @see ErrorFindAllPagedFunction
     */
    @NotNull
    public ErrorFindAllPagedFunction<BaseException, Pageable> getErrorFindAllPagedFunction() {
        LOGGER.atDebug().log("Returning errorFindAllPagedFunction function {}", errorFindAllPagedFunction.getName());
        return errorFindAllPagedFunction;
    }

    /**
     * Gets the pre find one sorted function {@link #preFindOneSortedFunction},
     * provided by the builder.
     * 
     * @return the pre find one sorted function
     * 
     * @see PreFindOneSortedFunction
     */
    @NotNull
    public PreFindOneSortedFunction<Sort> getPreFindOneSortedFunction() {
        LOGGER.atDebug().log("Returning preFindOneSortedFunction function {}", preFindOneSortedFunction.getName());
        return preFindOneSortedFunction;
    }

    /**
     * Gets the pos find one sorted function {@link #posFindOneSortedFunction},
     * provided by the builder.
     * 
     * @return the pos find one sorted function
     * 
     * @see PosFindOneSortedFunction
     */
    @NotNull
    public PosFindOneSortedFunction<OneResult> getPosFindOneSortedFunction() {
        LOGGER.atDebug().log("Returning posFindOneSortedFunction function {}", posFindOneSortedFunction.getName());
        return posFindOneSortedFunction;
    }

    /**
     * Gets the error find one sorted function {@link #errorFindOneSortedFunction},
     * provided by the builder.
     * 
     * @return the error find one sorted function
     * 
     * @see ErrorFindOneSortedFunction
     */
    @NotNull
    public ErrorFindOneSortedFunction<BaseException, Sort> getErrorFindOneSortedFunction() {
        LOGGER.atDebug().log("Returning errorFindOneSortedFunction function {}", errorFindOneSortedFunction.getName());
        return errorFindOneSortedFunction;
    }
}
