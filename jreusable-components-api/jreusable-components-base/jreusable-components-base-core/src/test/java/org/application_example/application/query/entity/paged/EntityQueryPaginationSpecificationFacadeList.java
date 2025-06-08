package org.application_example.application.query.entity.paged;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.application_example.infra.DummySecurityService;
import org.application_example.infra.ExceptionAdapterListService;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.QueryPaginationSpecificationFacade;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.QueryPaginationSpecificationFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.translation.JavaSEI18nService;

public class EntityQueryPaginationSpecificationFacadeList<Entity extends AbstractEntity<Id>, Id>
        extends
        QueryPaginationSpecificationFacade<Entity, Id, Entity, List<Entity>, PageList<Entity>, Comparator<Entity>, Predicate<Entity>> {

    private static final void validate(final Object... directives) {

        final var errorString = Arrays.stream(directives)
                .map(Object::toString)
                .anyMatch("error"::equalsIgnoreCase);

        if (errorString) {
            throw new IllegalStateException("Error!");
        }
    }

    private final List<Entity> repository;

    public EntityQueryPaginationSpecificationFacadeList(final List<Entity> repository) {
        super(new QueryPaginationSpecificationFacadeBuilder<>($ -> {

            $.findBySpecificationFunction = (pageable, specification, directives) -> {
                validate(directives);
                return pageable.getData()
                        .stream()
                        .filter(specification)
                        .toList();
            };

            $.findOneByFunctionWithOrder = (sort, specification, directives) -> {
                validate(directives);
                return repository
                        .stream()
                        .filter(specification)
                        .sorted(sort)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Id not found: " + sort));
            };

            // others --------------------------------
            $.securityService = new DummySecurityService();
            $.exceptionAdapterService = new ExceptionAdapterListService();
            $.i18nService = new JavaSEI18nService();
        }));

        this.repository = repository;
    }

}
