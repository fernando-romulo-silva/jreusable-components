package org.application_example.application.query.entity.nonpaged;

import java.util.List;
import java.util.Objects;

import org.application_example.infra.DummySecurityService;
import org.application_example.infra.ExceptionAdapterListService;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.QueryFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.QueryFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.translation.JavaSEI18nService;

public class EntityQueryFacadeList<Entity extends AbstractEntity<Id>, Id>
        extends QueryFacade<Entity, Id, Id, Entity, List<Entity>, Long, Boolean> {

    private final List<Entity> repository;

    public EntityQueryFacadeList(final List<Entity> repository) {

        super(new QueryFacadeBuilder<>($ -> {

            $.existsByIdFunction = (id, directives) -> repository.stream()
                    .anyMatch(entity -> Objects.equals(entity.getId(), id));

            $.findByIdFunction = (id, params) -> repository.stream()
                    .filter(entity -> Objects.equals(entity.getId(), id))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Id not found: " + id));

            $.findAllFunction = params -> repository.stream().toList();

            $.countAllFunction = directives -> repository.stream().count();
            $.existsAllFunction = directives -> repository.stream().count() > 0;

            // others --------------------------------
            $.securityService = new DummySecurityService();
            $.exceptionAdapterService = new ExceptionAdapterListService();
            $.i18nService = new JavaSEI18nService();
        }));

        this.repository = repository;
    }

    public List<Entity> getData() {
        return repository;
    }
}
