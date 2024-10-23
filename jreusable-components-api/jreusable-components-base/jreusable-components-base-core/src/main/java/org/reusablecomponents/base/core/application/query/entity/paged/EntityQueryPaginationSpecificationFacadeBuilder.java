package org.reusablecomponents.base.core.application.query.entity.paged;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.function.Consumer;

import org.apache.commons.lang3.function.TriFunction;
import org.reusablecomponents.base.core.application.base.EntiyBaseFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotNull;

/**
 * The <code>EntityQueryPaginationSpecificationFacade</code> builder's
 * class.
 */
public class EntityQueryPaginationSpecificationFacadeBuilder<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification>
        extends EntiyBaseFacadeBuilder {

    /**
     * Function that executes find By Specificationid algorithm
     */
    public TriFunction<Specification, Pageable, Object[], MultiplePagedResult> findBySpecificationFunction;

    /**
     * Function that executes find One By Specificationid and Sort algorithm
     */
    public TriFunction<Specification, Sort, Object[], OneResult> findOneByFunctionWithOrder;

    /**
     * Default constructor.
     * 
     * @param function Consumer function
     */
    public EntityQueryPaginationSpecificationFacadeBuilder(
            @NotNull final Consumer<EntityQueryPaginationSpecificationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification>> function) {

        super(function);

        function.accept(this);

        checkNotNull(findBySpecificationFunction, "Please pass a non-null 'findBySpecificationFunction'");
        checkNotNull(findOneByFunctionWithOrder, "Please pass a non-null 'findOneByFunctionWithOrder'");
    }

}
