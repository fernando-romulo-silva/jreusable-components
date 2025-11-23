package org.reusablecomponents.base.core.application.query.entity.paginationspecification;

import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_by_specification_paged.ErrorFindBySpecificationPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_by_specification_paged.PosFindBySpecificationPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_by_specification_paged.PreFindBySpecificationPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_one_by_specification_sorted.ErrorFindOneBySpecificationSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_one_by_specification_sorted.PosFindOneBySpecificationSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.function.find_one_by_specification_sorted.PreFindOneBySpecificationSortedFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public sealed abstract class AbstractQueryPaginationSpecificationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Specification, Pageable, Sort>
        extends BaseFacade<Entity, Id> permits QueryPaginationSpecificationFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQueryPaginationSpecificationFacade.class);

    /**
     * Function executed in
     * {@link QueryPaginationSpecificationFacade#findByPaginationPaged(Object, Object, Object...)
     * findBySpec}
     * method before the
     * {@link QueryPaginationSpecificationFacade#findBySpecificationPagedFunction
     * findByPagAndSpecFunction},
     * use it to configure, change, etc. the input.
     */
    protected PreFindBySpecificationPagedFunction<Specification, Pageable> preFindBySpecificationPagedFunction;

    /**
     * Function executed in
     * {@link QueryPaginationSpecificationFacade#findByPaginationPaged(Object, Object, Object...)
     * findBySpec} method after the
     * {@link QueryPaginationSpecificationFacade#findBySpecificationPagedFunction
     * findByPagAndSpecFunction}, use it to configure, change, etc. the result.
     */
    protected PosFindBySpecificationPagedFunction<MultiplePagedResult> posFindBySpecificationPagedFunction;

    /**
     * Function used to handle
     * {@link QueryPaginationSpecificationFacade#findByPaginationPaged(Object, Object, Object...)
     * findBySpec} errors.
     */
    protected ErrorFindBySpecificationPagedFunction<Specification, Pageable> errorFindBySpecificationPagedFunction;

    /**
     * Function executed in
     * {@link QueryPaginationSpecificationFacade#findOneBy(Object, Object...)
     * findOneBy} method before the
     * {@link QueryPaginationSpecificationFacade#findOneBySpecificationSortedFunction
     * findOneBySpecificationSortedFunction}, use it to configure, change, etc. the
     * input.
     */
    protected PreFindOneBySpecificationSortedFunction<Specification, Sort> preFindOneBySpecificationSortedFunction;

    /**
     * Function executed in
     * {@link QueryPaginationSpecificationFacade#findOneBy(Object, Object...)
     * findOneBy} method
     * after the
     * {@link QueryPaginationSpecificationFacade#findOneBySpecificationSortedFunction
     * findOneBySpecificationSortedFunction},
     * use it to configure, change, etc. the result.
     */
    protected PosFindOneBySpecificationSortedFunction<OneResult> posFindOneBySpecificationSortedFunction;

    /**
     * Method executed in
     * {@link QueryPaginationSpecificationFacade#findOneBy(Object, Object...)
     * findOneBy} method to
     * handle
     * {@link QueryPaginationSpecificationFacade#findOneBySpecificationSortedFunction
     * findOneBySpecificationSortedFunction}
     * errors.
     */
    protected ErrorFindOneBySpecificationSortedFunction<Specification, Sort> errorFindOneBySpecificationSortedFunction;

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

    @NotNull
    protected PreFindBySpecificationPagedFunction<Specification, Pageable> getPreFindBySpecificationPagedFunction() {
        LOGGER.debug("Returning preFindBySpecificationPagedFunction function {}",
                preFindBySpecificationPagedFunction.getName());
        return preFindBySpecificationPagedFunction;
    }

    @NotNull
    protected PosFindBySpecificationPagedFunction<MultiplePagedResult> getPosFindBySpecificationPagedFunction() {
        LOGGER.debug("Returning posFindBySpecificationPagedFunction function {}",
                posFindBySpecificationPagedFunction.getName());
        return posFindBySpecificationPagedFunction;
    }

    @NotNull
    protected ErrorFindBySpecificationPagedFunction<Specification, Pageable> getErrorFindBySpecificationPagedFunction() {
        LOGGER.debug("Returning errorFindBySpecificationPagedFunction function {}",
                errorFindBySpecificationPagedFunction.getName());
        return errorFindBySpecificationPagedFunction;
    }

    @NotNull
    protected PreFindOneBySpecificationSortedFunction<Specification, Sort> getPreFindOneBySpecificationSortedFunction() {
        LOGGER.debug("Returning preFindOneBySpecificationSortedFunction function {}",
                preFindOneBySpecificationSortedFunction.getName());
        return preFindOneBySpecificationSortedFunction;
    }

    @NotNull
    protected PosFindOneBySpecificationSortedFunction<OneResult> getPosFindOneBySpecificationSortedFunction() {
        LOGGER.debug("Returning posFindOneBySpecificationSortedFunction function {}",
                posFindOneBySpecificationSortedFunction.getName());
        return posFindOneBySpecificationSortedFunction;
    }

    @NotNull
    protected ErrorFindOneBySpecificationSortedFunction<Specification, Sort> getErrorFindOneBySpecificationSortedFunction() {
        LOGGER.debug("Returning errorFindOneBySpecificationSortedFunction function {}",
                errorFindOneBySpecificationSortedFunction.getName());
        return errorFindOneBySpecificationSortedFunction;
    }
}
