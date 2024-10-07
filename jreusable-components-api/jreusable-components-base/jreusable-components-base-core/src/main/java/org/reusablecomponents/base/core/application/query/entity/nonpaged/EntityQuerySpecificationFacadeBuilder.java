package org.reusablecomponents.base.core.application.query.entity.nonpaged;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.reusablecomponents.base.core.application.base.EntiyBaseFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;

/**
 * The <code>EntityQuerySpecificationFacade</code> builder's class.
 */
public class EntityQuerySpecificationFacadeBuilder<Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification>
        extends EntiyBaseFacadeBuilder {

    /**
     * Function that executes find by specification algorithm
     */
    public BiFunction<Specification, Object[], MultipleResult> findBySpecificationFunction;

    /**
     * Function that executes find One by algorithm
     */
    public BiFunction<Specification, Object[], OneResult> findOneByFunction;

    /**
     * Function that executes exists by algorithm
     */
    public Function<Specification, ExistsResult> existsBySpecificationFunction;

    /**
     * Function that executes count by specification algorithm
     */
    public Function<Specification, CountResult> countBySpecificationFunction;

    /**
     * Default constructor
     * 
     * @param builder Object in charge to construct this one
     */
    public EntityQuerySpecificationFacadeBuilder(
            final Consumer<EntityQuerySpecificationFacadeBuilder<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification>> function) {

        super(function);

        function.accept(this);

        checkNotNull(findBySpecificationFunction, "Please pass a non-null 'findBySpecificationFunction'");
        checkNotNull(findOneByFunction, "Please pass a non-null 'findOneByFunction'");
        checkNotNull(existsBySpecificationFunction, "Please pass a non-null 'existsBySpecificationFunction'");
        checkNotNull(countBySpecificationFunction, "Please pass a non-null 'countBySpecificationFunction'");
    }
}
