package org.reusablecomponents.base.core.application.query.entity.paginationspecification;

import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_by_specification_paged.ErrorFindBySpecificationPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_by_specification_paged.PosFindBySpecificationPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_by_specification_paged.PreFindBySpecificationPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_one_by_specification_sorted.ErrorFindOneBySpecificationSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_one_by_specification_sorted.PosFindOneBySpecificationSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_one_by_specification_sorted.PreFindOneBySpecificationSortedFunction;
import org.reusablecomponents.base.core.domain.InterfaceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * Abstract class for query pagination specification facades, providing common
 * functionality for handling pre, post, and error functions for various query
 * operations.
 * 
 * This class provide functions for pre, post, and error execution of the main
 * functions defined in {@link QueryPaginationSpecificationFacade}.
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 * 
 * @see QueryPaginationSpecificationFacade
 * @see BaseFacade
 */
public sealed abstract class AbstractQueryPaginationSpecificationFacade<Entity extends InterfaceEntity<Id>, Id, OneResult, MultiplePagedResult, Specification, Pageable, Sort>
        extends BaseFacade<Entity, Id> permits QueryPaginationSpecificationFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQueryPaginationSpecificationFacade.class);

    /**
     * Function executed in
     * {@link QueryPaginationSpecificationFacade#findByPaginationPaged(Object, Object, Object...)}
     * method before the
     * {@link QueryPaginationSpecificationFacade#findBySpecificationPagedFunction},
     * use it to configure, change, etc. the input.
     * 
     * @see PreFindBySpecificationPagedFunction
     */
    protected PreFindBySpecificationPagedFunction<Specification, Pageable> preFindBySpecificationPagedFunction;

    /**
     * Function executed in
     * {@link QueryPaginationSpecificationFacade#findByPaginationPaged(Object, Object, Object...)}
     * method after the
     * {@link QueryPaginationSpecificationFacade#findBySpecificationPagedFunction},
     * use it to configure, change, etc. the result.
     * 
     * @see PosFindBySpecificationPagedFunction
     */
    protected PosFindBySpecificationPagedFunction<MultiplePagedResult> posFindBySpecificationPagedFunction;

    /**
     * Function executed in
     * {@link QueryPaginationSpecificationFacade#findByPaginationPaged(Object, Object, Object...)}
     * to handle
     * {@link QueryPaginationSpecificationFacade#findBySpecificationPagedFunction}
     * errors.
     * 
     * @see ErrorFindBySpecificationPagedFunction
     */
    protected ErrorFindBySpecificationPagedFunction<Specification, Pageable> errorFindBySpecificationPagedFunction;

    /**
     * Function executed in
     * {@link QueryPaginationSpecificationFacade#findOneByPaginationSorted(Object, Object, Object...)}
     * method before the
     * {@link QueryPaginationSpecificationFacade#findOneBySpecificationSortedFunction},
     * use it to configure, change, etc. the input.
     * 
     * @see PreFindOneBySpecificationSortedFunction
     */
    protected PreFindOneBySpecificationSortedFunction<Specification, Sort> preFindOneBySpecificationSortedFunction;

    /**
     * Function executed in
     * {@link QueryPaginationSpecificationFacade#findOneByPaginationSorted(Object, Object, Object...)}
     * method after the
     * {@link QueryPaginationSpecificationFacade#findOneBySpecificationSortedFunction},
     * use it to configure, change, etc. the result.
     * 
     * @see PosFindOneBySpecificationSortedFunction
     */
    protected PosFindOneBySpecificationSortedFunction<OneResult> posFindOneBySpecificationSortedFunction;

    /**
     * Method executed in
     * {@link QueryPaginationSpecificationFacade#findOneBy(Object, Object...)}
     * method to handle
     * {@link QueryPaginationSpecificationFacade#findOneBySpecificationSortedFunction}
     * errors.
     * 
     * @see ErrorFindOneBySpecificationSortedFunction
     */
    protected ErrorFindOneBySpecificationSortedFunction<Specification, Sort> errorFindOneBySpecificationSortedFunction;

    /**
     * Default constructor, used by the builder to construct this class.
     * 
     * @param builder The builder used to construct this class, can't be null
     * 
     * @throws NullPointerException if the builder is null
     * 
     * @see AbstractQueryPaginationSpecificationFacadeBuilder
     */
    protected AbstractQueryPaginationSpecificationFacade(
            @NotNull final AbstractQueryPaginationSpecificationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Specification, Pageable, Sort> builder) {
        super(builder);

        this.preFindBySpecificationPagedFunction = builder.preFindBySpecificationPagedFunction;
        this.posFindBySpecificationPagedFunction = builder.posFindBySpecificationPagedFunction;
        this.errorFindBySpecificationPagedFunction = builder.errorFindBySpecificationPagedFunction;

        this.preFindOneBySpecificationSortedFunction = builder.preFindOneBySpecificationSortedFunction;
        this.posFindOneBySpecificationSortedFunction = builder.posFindOneBySpecificationSortedFunction;
        this.errorFindOneBySpecificationSortedFunction = builder.errorFindOneBySpecificationSortedFunction;
    }

    /**
     * Gets the pre find by specification paged function
     * {@link #preFindBySpecificationPagedFunction}, provided by the builder.
     * 
     * @return The pre find by specification paged function.
     * 
     * @see PreFindBySpecificationPagedFunction
     */
    @NotNull
    protected PreFindBySpecificationPagedFunction<Specification, Pageable> getPreFindBySpecificationPagedFunction() {
        LOGGER.atDebug().log("Returning preFindBySpecificationPagedFunction function {}",
                preFindBySpecificationPagedFunction.getName());
        return preFindBySpecificationPagedFunction;
    }

    /**
     * Gets the find by specification paged function
     * {@link #posFindBySpecificationPagedFunction}, provided by the builder.
     * 
     * @return The find by specification paged function.
     * 
     * @see PosFindBySpecificationPagedFunction
     */
    @NotNull
    protected PosFindBySpecificationPagedFunction<MultiplePagedResult> getPosFindBySpecificationPagedFunction() {
        LOGGER.atDebug().log("Returning posFindBySpecificationPagedFunction function {}",
                posFindBySpecificationPagedFunction.getName());
        return posFindBySpecificationPagedFunction;
    }

    /**
     * Gets the error find by specification paged function
     * {@link #errorFindBySpecificationPagedFunction}, provided by the builder.
     * 
     * @return The error find by specification paged function.
     * 
     * @see ErrorFindBySpecificationPagedFunction
     */
    @NotNull
    protected ErrorFindBySpecificationPagedFunction<Specification, Pageable> getErrorFindBySpecificationPagedFunction() {
        LOGGER.atDebug().log("Returning errorFindBySpecificationPagedFunction function {}",
                errorFindBySpecificationPagedFunction.getName());
        return errorFindBySpecificationPagedFunction;
    }

    /**
     * Gets the pre find one by specification sorted function
     * {@link #preFindOneBySpecificationSortedFunction}, provided by the builder.
     * 
     * @return The pre find one by specification sorted function.
     * 
     * @see PreFindOneBySpecificationSortedFunction
     */
    @NotNull
    protected PreFindOneBySpecificationSortedFunction<Specification, Sort> getPreFindOneBySpecificationSortedFunction() {
        LOGGER.atDebug().log("Returning preFindOneBySpecificationSortedFunction function {}",
                preFindOneBySpecificationSortedFunction.getName());
        return preFindOneBySpecificationSortedFunction;
    }

    /**
     * Gets the post find one by specification sorted function
     * {@link #posFindOneBySpecificationSortedFunction}, provided by the builder.
     * 
     * @return The post find one by specification sorted function.
     * 
     * @see PosFindOneBySpecificationSortedFunction
     */
    @NotNull
    protected PosFindOneBySpecificationSortedFunction<OneResult> getPosFindOneBySpecificationSortedFunction() {
        LOGGER.atDebug().log("Returning posFindOneBySpecificationSortedFunction function {}",
                posFindOneBySpecificationSortedFunction.getName());
        return posFindOneBySpecificationSortedFunction;
    }

    /**
     * Gets the error find one by specification sorted function
     * {@link #errorFindOneBySpecificationSortedFunction}, provided by the builder.
     * 
     * @return The error find one by specification sorted function.
     * 
     * @see ErrorFindOneBySpecificationSortedFunction
     */
    @NotNull
    protected ErrorFindOneBySpecificationSortedFunction<Specification, Sort> getErrorFindOneBySpecificationSortedFunction() {
        LOGGER.atDebug().log("Returning errorFindOneBySpecificationSortedFunction function {}",
                errorFindOneBySpecificationSortedFunction.getName());
        return errorFindOneBySpecificationSortedFunction;
    }
}
