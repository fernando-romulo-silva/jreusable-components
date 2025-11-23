package org.reusablecomponents.base.core.application.query.entity.pagination;

import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged.ErrorFindAllPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged.PosFindAllPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged.PreFindAllPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted.ErrorFindOneSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted.PosFindOneSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted.PreFindOneSortedFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public sealed abstract class AbstractQueryPaginationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort>
        extends BaseFacade<Entity, Id>
        permits QueryPaginationFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQueryPaginationFacade.class);

    /**
     * Function executed in
     * {@link QueryPaginationFacade#findAllPaged(Object, Object...)
     * findAll} method before the {@link QueryPaginationFacade#findAllPagedFunction
     * findAllFunction}, use it to configure, change, etc. the input.
     */
    protected PreFindAllPagedFunction<Pageable> preFindAllPagedFunction;

    /**
     * Function executed in
     * {@link QueryPaginationFacade#findAllPaged(Object, Object...)
     * findAll} method after the {@link QueryPaginationFacade#findAllPagedFunction
     * findAllFunction}, use it to configure, change, etc. the result.
     */
    protected PosFindAllPagedFunction<MultiplePagedResult> posFindAllPagedFunction;

    /**
     * Function executed in
     * {@link QueryPaginationFacade#findAllPaged(Object, Object...)
     * findAll} method to handle {@link QueryPaginationFacade#findAllPagedFunction
     * findAllFunction} errors.
     */
    protected ErrorFindAllPagedFunction<BaseException, Pageable> errorFindAllPagedFunction;

    /**
     * Function executed in
     * {@link QueryPaginationFacade#findOneSorted(Object, Object...)
     * findOne} method before the {@link QueryPaginationFacade#findOneSortedFunction
     * findOneFunction}, use it to configure, change, etc. the input.
     */
    protected PreFindOneSortedFunction<Sort> preFindOneSortedFunction;

    /**
     * Function executed in
     * {@link QueryPaginationFacade#findOneSorted(Object, Object...)
     * findOne} method after the {@link QueryPaginationFacade#findOneSortedFunction
     * findOneFunction}, use it to configure, change, etc. the result.
     */
    protected PosFindOneSortedFunction<OneResult> posFindOneSortedFunction;

    /**
     * Function executed in
     * {@link QueryPaginationFacade#findOneSorted(Object, Object...)
     * findOne} method to handle {@link QueryPaginationFacade#findOneSortedFunction
     * findOneFunction} errors.
     */
    protected ErrorFindOneSortedFunction<BaseException, Sort> errorFindOneSortedFunction;

    /**
     * 
     * @param builder
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

    @NotNull
    public PreFindAllPagedFunction<Pageable> getPreFindAllPagedFunction() {
        LOGGER.debug("Returning preFindAllPagedFunction function {}", preFindAllPagedFunction.getName());
        return preFindAllPagedFunction;
    }

    @NotNull
    public PosFindAllPagedFunction<MultiplePagedResult> getPosFindAllPagedFunction() {
        LOGGER.debug("Returning posFindAllPagedFunction function {}", posFindAllPagedFunction.getName());
        return posFindAllPagedFunction;
    }

    @NotNull
    public ErrorFindAllPagedFunction<BaseException, Pageable> getErrorFindAllPagedFunction() {
        LOGGER.debug("Returning errorFindAllPagedFunction function {}", errorFindAllPagedFunction.getName());
        return errorFindAllPagedFunction;
    }

    @NotNull
    public PreFindOneSortedFunction<Sort> getPreFindOneSortedFunction() {
        LOGGER.debug("Returning preFindOneSortedFunction function {}", preFindOneSortedFunction.getName());
        return preFindOneSortedFunction;
    }

    @NotNull
    public PosFindOneSortedFunction<OneResult> getPosFindOneSortedFunction() {
        LOGGER.debug("Returning posFindOneSortedFunction function {}", posFindOneSortedFunction.getName());
        return posFindOneSortedFunction;
    }

    @NotNull
    public ErrorFindOneSortedFunction<BaseException, Sort> getErrorFindOneSortedFunction() {
        LOGGER.debug("Returning errorFindOneSortedFunction function {}", errorFindOneSortedFunction.getName());
        return errorFindOneSortedFunction;
    }
}
