package org.application_example.application.query.entity.nonpaged;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.application_example.infra.DummySecurityService;
import org.application_example.infra.ListExceptionAdapterService;
import org.reusablecomponents.base.core.application.query.entity.specification.QuerySpecificationFacade;
import org.reusablecomponents.base.core.application.query.entity.specification.QuerySpecificationFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.translation.JavaSEI18nService;

public class EntityQuerySpecificationFacadeList<Entity extends AbstractEntity<Id>, Id>
		extends QuerySpecificationFacade<Entity, Id, Entity, List<Entity>, Long, Boolean, Predicate<Entity>> {

	private static final void validate(final Object... directives) {

		final var errorString = Arrays.stream(directives)
				.map(Object::toString)
				.anyMatch("error"::equalsIgnoreCase);

		if (errorString) {
			throw new IllegalStateException("Error!");
		}
	}

	private final List<Entity> repository;

	public EntityQuerySpecificationFacadeList(final List<Entity> repository) {

		super(new QuerySpecificationFacadeBuilder<>($ -> {

			$.findBySpecificationFunction = (specification, directives) -> {
				validate(directives);
				return repository.stream()
						.filter(specification)
						.toList();
			};

			$.findOneByFunction = (specification, directives) -> {
				validate(directives);
				return repository.stream()
						.filter(specification)
						.findFirst()
						.orElseThrow(() -> new IllegalArgumentException(
								"Id not found: " + specification));
			};

			$.existsBySpecificationFunction = (specification, directives) -> {
				validate(directives);
				return repository.stream()
						.filter(specification)
						.findAny()
						.isPresent();
			};

			$.countBySpecificationFunction = (specification, directives) -> {
				validate(directives);
				return repository.stream()
						.filter(specification)
						.count();
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
