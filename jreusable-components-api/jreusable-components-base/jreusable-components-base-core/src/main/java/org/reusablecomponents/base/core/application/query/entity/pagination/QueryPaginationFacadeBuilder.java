package org.reusablecomponents.base.core.application.query.entity.pagination;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.reusablecomponents.base.core.application.base.BaseFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotNull;

/**
 * The <code>EntityQueryPaginationFacade</code> builder's class.
 */
public class QueryPaginationFacadeBuilder<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort>
        extends BaseFacadeBuilder {

    /**
     * Function that executes find all, but paged
     */
    public BiFunction<Pageable, Object[], MultiplePagedResult> findAllFunction;

    /**
     * Function that executes find first by a specific order
     */
    public BiFunction<Sort, Object[], OneResult> findFirstFunction;

    /**
     * Default constructor.
     * 
     * @param function Consumer function
     */
    public QueryPaginationFacadeBuilder(
            @NotNull final Consumer<QueryPaginationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort>> function) {

        super(function);

        function.accept(this);

        checkNotNull(findAllFunction, "Please pass a non-null 'findAllFunction'");
        checkNotNull(findFirstFunction, "Please pass a non-null 'findFirstFunction'");
    }
}
