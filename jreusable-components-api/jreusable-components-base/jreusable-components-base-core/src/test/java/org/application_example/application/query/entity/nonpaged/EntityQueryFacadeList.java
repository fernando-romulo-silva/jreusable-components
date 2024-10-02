package org.application_example.application.query.entity.nonpaged;

import java.util.List;
import java.util.Objects;

import org.application_example.infra.DummySecurityService;
import org.application_example.infra.ExceptionAdapterListService;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.EntityQueryFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.EntityQueryFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.messaging.logger.LoggerPublisherSerice;
import org.reusablecomponents.base.translation.JavaSEI18nService;

public class EntityQueryFacadeList<Entity extends AbstractEntity<Id>, Id>
        extends EntityQueryFacade<Entity, Id, Id, Entity, List<Entity>, Long, Boolean> {

    private final List<Entity> repository;

    public EntityQueryFacadeList(final List<Entity> repository) {

        super(new EntityQueryFacadeBuilder<>($ -> {

            $.existsByIdFunction = id -> repository.stream().anyMatch(entity -> Objects.equals(entity.getId(), id));

            $.findByIdFunction = (id, params) -> repository.stream()
                    .filter(entity -> Objects.equals(entity.getId(), id))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Id not found: " + id));

            $.findAllFunction = params -> repository.stream().toList();

            $.countAllFunction = () -> repository.stream().count();
            $.existsAllFunction = () -> repository.stream().count() > 0;

            // others --------------------------------
            $.securityService = new DummySecurityService();
            $.publisherService = new LoggerPublisherSerice();
            $.exceptionAdapterService = new ExceptionAdapterListService();
            $.i18nService = new JavaSEI18nService();
        }));

        this.repository = repository;
    }

    public List<Entity> getData() {
        return repository;
    }
}
