package org.reusablecomponents.base.core.application.query.entity.nonpaged;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.reusablecomponents.base.core.application.base.EntiyBaseFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;

/**
 * The <code>InterfaceEntityQueryFacade</code> builder's class.
 */
public class EntityQueryFacadeBuilder<Entity extends AbstractEntity<Id>, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>
        extends EntiyBaseFacadeBuilder {

    /**
     * Function that executes exists by id algorithm
     */
    public Function<QueryIdIn, ExistsResult> existsByIdFunction;

    /**
     * Function that executes find by id algorithm
     */
    public BiFunction<QueryIdIn, Object[], OneResult> findByIdFunction;

    /**
     * Function that executes find all algorithm
     */
    public Function<Object[], MultipleResult> findAllFunction;

    /**
     * Function that executes count all algorithm
     */
    public Supplier<CountResult> countAllFunction;

    /**
     * Function that executes exist all algorithm
     */
    public Supplier<ExistsResult> existsAllFunction;

    /**
     * Default constructor.
     * 
     * @param function Consumer function
     */
    public EntityQueryFacadeBuilder(
            final Consumer<EntityQueryFacadeBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>> function) {

        super(function);

        function.accept(this);

        checkNotNull(existsByIdFunction, "Please pass a non-null 'existsByIdFunction'");
        checkNotNull(findByIdFunction, "Please pass a non-null 'findByIdFunction'");

        checkNotNull(findAllFunction, "Please pass a non-null 'findAllFunction'");
        checkNotNull(countAllFunction, "Please pass a non-null 'countAllFunction'");

        checkNotNull(existsAllFunction, "Please pass a non-null 'existsAllFunction'");
    }
}
