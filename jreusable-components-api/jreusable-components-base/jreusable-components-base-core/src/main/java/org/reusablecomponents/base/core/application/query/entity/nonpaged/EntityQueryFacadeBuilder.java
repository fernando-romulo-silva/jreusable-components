package org.reusablecomponents.base.core.application.query.entity.nonpaged;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.reusablecomponents.base.core.application.base.EntiyBaseFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;

public class EntityQueryFacadeBuilder<Entity extends AbstractEntity<Id>, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>
        extends EntiyBaseFacadeBuilder {

    public Function<QueryIdIn, ExistsResult> existsByIdFunction;

    public BiFunction<QueryIdIn, Object[], OneResult> findByIdFunction;

    public Function<Object[], MultipleResult> findAllFunction;

    public Supplier<CountResult> countAllFunction;

    public Supplier<ExistsResult> existsAllFunction;

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
