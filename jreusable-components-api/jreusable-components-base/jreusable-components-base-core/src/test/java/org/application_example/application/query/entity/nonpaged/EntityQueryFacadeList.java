package org.application_example.application.query.entity.nonpaged;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.application_example.infra.DummySecurityService;
import org.application_example.infra.ListExceptionAdapterService;
import org.reusablecomponents.base.core.application.query.entity.simple.QueryFacade;
import org.reusablecomponents.base.core.application.query.entity.simple.QueryFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.translation.JavaSEI18nService;

public class EntityQueryFacadeList<Entity extends AbstractEntity<Id>, Id>
        extends QueryFacade<Entity, Id, Id, Entity, List<Entity>, Long, Boolean> {

    private static final void validate(final Object... directives) {

        final var errorString = Arrays.stream(directives)
                .map(Object::toString)
                .anyMatch("error"::equalsIgnoreCase);

        if (errorString) {
            throw new IllegalStateException("Error!");
        }
    }

    private final List<Entity> repository;

    public EntityQueryFacadeList(final List<Entity> repository) {

        super(new QueryFacadeBuilder<>($ -> {

            $.existsByIdFunction = (id, directives) -> {
                validate(directives);
                return repository.stream()
                        .anyMatch(entity -> Objects.equals(entity.getId(), id));
            };

            $.findByIdFunction = (id, directives) -> {
                validate(directives);
                return repository.stream()
                        .filter(entity -> Objects.equals(entity.getId(), id))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Id not found: " + id));
            };

            $.findAllFunction = directives -> {
                validate(directives);
                return repository.stream().toList();
            };

            $.countAllFunction = directives -> {
                validate(directives);
                return repository.stream().count();
            };

            $.existsAllFunction = directives -> {
                validate(directives);
                return repository.stream().count() > 0;
            };

            // others --------------------------------
            $.securityService = new DummySecurityService();
            $.exceptionAdapterService = new ListExceptionAdapterService();
            $.i18nService = new JavaSEI18nService();
        }));

        this.repository = repository;
    }

    public List<Entity> getData() {
        return repository;
    }
}
