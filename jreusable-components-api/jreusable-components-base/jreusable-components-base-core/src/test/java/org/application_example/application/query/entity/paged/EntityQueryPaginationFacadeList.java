package org.application_example.application.query.entity.paged;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.application_example.infra.DummySecurityService;
import org.application_example.infra.ListExceptionAdapterService;
import org.reusablecomponents.base.core.application.query.entity.pagination.QueryPaginationFacade;
import org.reusablecomponents.base.core.application.query.entity.pagination.QueryPaginationFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.translation.JavaSEI18nService;

public class EntityQueryPaginationFacadeList<Entity extends AbstractEntity<Id>, Id>
        extends QueryPaginationFacade<Entity, Id, Entity, List<Entity>, PageList<Entity>, Comparator<Entity>> {

    private static final void validate(final Object... directives) {

        final var errorString = Arrays.stream(directives)
                .map(Object::toString)
                .anyMatch("error"::equalsIgnoreCase);

        if (errorString) {
            throw new IllegalStateException("Error!");
        }
    }

    private final List<Entity> repository;

    public EntityQueryPaginationFacadeList(final List<Entity> repository) {
        super(new QueryPaginationFacadeBuilder<>($ -> {

            $.findOneSortedFunction = (sort, directives) -> {
                validate(directives);
                return repository
                        .stream()
                        .sorted(sort)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Id not found: " + sort));
            };

            $.findAllPagedFunction = (pageable, directives) -> {
                validate(directives);
                return pageable.getData();
            };

            // others --------------------------------
            $.securityService = new DummySecurityService();
            $.exceptionAdapterService = new ListExceptionAdapterService();
            $.i18nService = new JavaSEI18nService();
        }));

        this.repository = repository;
    }

}
