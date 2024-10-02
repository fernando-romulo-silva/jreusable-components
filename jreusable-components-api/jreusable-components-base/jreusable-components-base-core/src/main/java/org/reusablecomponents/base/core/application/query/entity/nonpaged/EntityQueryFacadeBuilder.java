package org.reusablecomponents.base.core.application.query.entity.nonpaged;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.messaging.InterfaceEventPublisherSerice;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;

public class EntityQueryFacadeBuilder<Entity extends AbstractEntity<Id>, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> {

    public InterfaceEventPublisherSerice<?> publisherService;

    public InterfaceSecurityService securityService;

    public InterfaceI18nService i18nService;

    public InterfaceExceptionAdapterService exceptionAdapterService;

    public Function<QueryIdIn, ExistsResult> existsByIdFunction;

    public BiFunction<QueryIdIn, Object[], OneResult> findByIdFunction;

    public Function<Object[], MultipleResult> findAllFunction;

    public Supplier<CountResult> countAllFunction;

    public Supplier<ExistsResult> existsAllFunction;

    public EntityQueryFacadeBuilder(
            final Consumer<EntityQueryFacadeBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>> function) {

        // load the functions
        function.accept(this);

        checkNotNull(publisherService, "Please pass a non-null 'publisherService'");
        checkNotNull(securityService, "Please pass a non-null 'securityService'");
        checkNotNull(i18nService, "Please pass a non-null 'i18nService'");
        checkNotNull(exceptionAdapterService, "Please pass a non-null 'exceptionTranslatorService'");

        checkNotNull(existsByIdFunction, "Please pass a non-null 'existsByIdFunction'");
        checkNotNull(findByIdFunction, "Please pass a non-null 'findByIdFunction'");

        checkNotNull(findAllFunction, "Please pass a non-null 'findAllFunction'");
        checkNotNull(countAllFunction, "Please pass a non-null 'countAllFunction'");

        checkNotNull(existsAllFunction, "Please pass a non-null 'existsAllFunction'");
    }
}
