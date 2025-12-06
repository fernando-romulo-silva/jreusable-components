package org.reusablecomponents.base.core.application.query.entity.pagination;

import static java.util.Objects.nonNull;

import java.util.function.Consumer;

import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged.FindAllPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted.FindOneSortedFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotNull;

/**
 * The <code>EntityQueryPaginationFacade</code> builder's class.
 */
public class QueryPaginationFacadeBuilder<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort>
        extends AbstractQueryPaginationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> {

    /**
     * Function that executes find all paged
     */
    public FindAllPagedFunction<Pageable, MultiplePagedResult> findAllPagedFunction;

    /**
     * Function that executes find one by a specific order
     */
    public FindOneSortedFunction<Sort, OneResult> findOneSortedFunction;

    /**
     * Default constructor.
     * 
     * @param function Consumer function
     */
    public QueryPaginationFacadeBuilder(
            @NotNull final Consumer<QueryPaginationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort>> function) {
        super(function);

        this.findAllPagedFunction = nonNull(findAllPagedFunction)
                ? findAllPagedFunction
                : (pageable, directives) -> {
                    throw new UnsupportedOperationException("Unimplemented function 'findAllPagedFunction'");
                };

        this.findOneSortedFunction = nonNull(findOneSortedFunction)
                ? findOneSortedFunction
                : (sort, directives) -> {
                    throw new UnsupportedOperationException("Unimplemented function 'findOneSortedFunction'");
                };
    }
}
