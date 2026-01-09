package org.reusablecomponents.base.core.application.query.entity.specification;

import org.reusablecomponents.base.core.application.query.entity.specification.function.count_by_spec.CountBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.exists_by_spec.ExistsBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_by_spec.FindBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_one_by_spec.FindOneBySpecFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * The default <code>InterfaceEntityQuerySpecificationFacade</code>'s
 * implementation.
 */
public non-sealed class QuerySpecificationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification>
		extends
		AbstractQuerySpecificationFacade<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification>
		implements
		InterfaceQuerySpecificationFacade<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification> {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuerySpecificationFacade.class);

	/**
	 * Function that executes the find by an spec operation in the
	 * {@link #findBySpecification(Object, Object...) findBySpec} method
	 */
	protected final FindBySpecificationFunction<Specification, MultipleResult> findBySpecificationFunction;

	/**
	 * Function that executes the find all operation in the
	 * {@link #findOneBySpecification(Object, Object...) findOneBySpec} method
	 */
	protected final FindOneBySpecFunction<Specification, OneResult> findOneBySpecFunction;

	/**
	 * Function that executes the find all operation in the
	 * {@link #existsBySpecification(Object, Object...) existsBySpec} method
	 */
	protected final ExistsBySpecificationFunction<Specification, ExistsResult> existsBySpecFunction;

	/**
	 * Function that executes the find all operation in the
	 * {@link #countBySpecification(Object, Object...) countBySpec} method
	 */
	protected final CountBySpecificationFunction<Specification, CountResult> countBySpecFunction;

	/**
	 * Default constructor
	 * 
	 * @param builder Object in charge to construct this one
	 */
	protected QuerySpecificationFacade(
			@NotNull final QuerySpecificationFacadeBuilder<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification> builder) {
		super(builder);
		this.findBySpecificationFunction = builder.findBySpecificationFunction;
		this.findOneBySpecFunction = builder.findOneBySpecificationFunction;
		this.existsBySpecFunction = builder.existsBySpecificationFunction;
		this.countBySpecFunction = builder.countBySpecificationFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MultipleResult findBySpecification(
			final Specification specification,
			final Object... directives) {
		LOGGER.debug("Executing default findBySpecification, specification {}, directives {}",
				specification, directives);

		final var multipleResult = execute(
				specification, getPreFindBySpecificationFunction(), getFindBySpecificationFunction(),
				getPosFindBySpecificationFunction(), getErrorFindBySpecificationFunction(), directives);

		LOGGER.debug("Default findBySpecification executed, multipleResult {}, directives {}",
				multipleResult, directives);
		return multipleResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OneResult findOneBySpecification(
			final Specification specification,
			final Object... directives) {
		LOGGER.debug("Executing default findOneBySpecification, specification {}, directives {}",
				specification, directives);

		final var oneResult = execute(
				specification, getPreFindOneBySpecificationFunction(), getFindOneBySpecFunction(),
				getPosFindOneBySpecificationFunction(), getErrorFindOneBySpecificationFunction(), directives);

		LOGGER.debug("Default findOneBySpecification executed, oneResult {}, directives {}",
				oneResult, directives);
		return oneResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final ExistsResult existsBySpecification(final Specification specification, final Object... directives) {
		LOGGER.debug("Executing default existsBySpec, specification {}, directives {}", specification, directives);

		final var existsResult = execute(
				specification, getPreExistsBySpecificationFunction(), getExistsBySpecFunction(),
				getPosExistsBySpecificationFunction(), getErrorExistsBySpecificationFunction(), directives);

		LOGGER.debug("Default existsBySpec executed, existsResult {}, directives {}",
				existsResult, directives);
		return existsResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final CountResult countBySpecification(final Specification specification, final Object... directives) {
		LOGGER.debug("Executing default countBySpec, specification {}, directives {}", specification, directives);

		final var countResult = execute(
				specification, getPreCountBySpecificationFunction(), getCountBySpecFunction(),
				getPosCountBySpecificationFunction(), getErrorCountBySpecificationFunction(), directives);

		LOGGER.debug("Default countBySpec executed, countResult {}, directives {}", countResult, directives);
		return countResult;
	}

	@NotNull
	public FindBySpecificationFunction<Specification, MultipleResult> getFindBySpecificationFunction() {
		LOGGER.debug("Returning findBySpecFunction function {}", findBySpecificationFunction.getName());
		return findBySpecificationFunction;
	}

	@NotNull
	public FindOneBySpecFunction<Specification, OneResult> getFindOneBySpecFunction() {
		LOGGER.debug("Returning findOneBySpecFunction function {}", findOneBySpecFunction.getName());
		return findOneBySpecFunction;
	}

	@NotNull
	public ExistsBySpecificationFunction<Specification, ExistsResult> getExistsBySpecFunction() {
		LOGGER.debug("Returning existsBySpecFunction function {}", existsBySpecFunction.getName());
		return existsBySpecFunction;
	}

	@NotNull
	public CountBySpecificationFunction<Specification, CountResult> getCountBySpecFunction() {
		LOGGER.debug("Returning countBySpecFunction function {}", countBySpecFunction.getName());
		return countBySpecFunction;
	}
}
