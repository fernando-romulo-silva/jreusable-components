package org.reusablecomponents.base.core.application.query.entity.pagination;

import static java.util.Objects.nonNull;

import java.util.function.Consumer;

import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged.FindAllPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted.FindOneSortedFunction;
import org.reusablecomponents.base.core.domain.InterfaceEntity;

import jakarta.validation.constraints.NotNull;

/**
 * The <code>QueryPaginationFacade</code> builder's class.
 * 
 * This class is responsible for building the <code>QueryPaginationFacade</code>
 * object.
 * 
 * For each function in this class, if it is not set, it will be set with a
 * default function that throws an UnsupportedOperationException with a message
 * that the function is not implemented, example: "Unimplemented function
 * 'functionName'".
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 * 
 * @see AbstractQueryPaginationFacadeBuilder
 * @see QueryPaginationFacade
 */
public class QueryPaginationFacadeBuilder<Entity extends InterfaceEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort>
        extends AbstractQueryPaginationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> {

    /**
     * Check {@link QueryPaginationFacade#findAllPagedFunction
     * QueryPaginationFacade.findAllPagedFunction}.
     * 
     * @see FindAllPagedFunction
     */
    public FindAllPagedFunction<Pageable, MultiplePagedResult> findAllPagedFunction;

    /**
     * Check {@link QueryPaginationFacade#findOneSortedFunction
     * QueryPaginationFacade.findOneSortedFunction}.
     * 
     * @see FindOneSortedFunction
     */
    public FindOneSortedFunction<Sort, OneResult> findOneSortedFunction;

    /**
     * Default constructor.
     * 
     * @param function Consumer function, can't be null, used to set the builder
     *                 attributes with a lambda expression, example:
     * 
     *                 <pre>
     *                 new QueryPaginationFacadeBuilder&lt;Entity, Id, QueryIdIn, OneResult, MultiplePagedResult, Pageable, Sort&gt;(
     *                         builder -&gt; {
     *                             builder.findAllPagedFunction = (directives) -&gt; {
     *                                 // implementation of the find all paged operation in the persistence
     *                                 // layer.
     *                             };
     *                             builder.findOneSortedFunction = (sort, directives) -&gt; {
     *                                 // implementation of the find one sorted operation in the persistence
     *                                 // layer.
     *                             };
     *                             // set other functions...
     *                         });
     *                 </pre>
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
