package org.reusablecomponents.base.core.application.query.entity.specification;

import org.reusablecomponents.base.core.application.query.entity.specification.function.count_by_spec.CountBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.exists_by_spec.ExistsBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_by_spec.FindBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_one_by_spec.FindOneBySpecFunction;
import org.reusablecomponents.base.core.domain.InterfaceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * The default <code>InterfaceEntityQuerySpecificationFacade</code>'s
 * implementation.
 * 
 * <p>
 * This class provides default implementations for the basic query operations:
 * <ul>
 * <li>Find by specification</li>
 * <li>Find one by specification</li>
 * <li>Exists by specification</li>
 * <li>Count by specification</li>
 * </ul>
 * <p>
 * 
 * @param <Entity>         The facade entity type
 * @param <Id>             The facade entity id type
 * 
 * @param <OneResult>      The one-result type, like the entity or wrap type
 * @param <MultipleResult> The multiple-result type, like List<Entity>,
 *                         Iterable<Entity>, or a wrap type like
 *                         Mono<List<Entity>>
 * @param <CountResult>    The count-result type, like Long or Integer
 * @param <ExistsResult>   The exists-result type, like Boolean
 * @param <Specification>  The specification type, used to filter queries
 * 
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 * 
 * @see InterfaceQuerySpecificationFacade
 * @see AbstractQuerySpecificationFacade
 */
public non-sealed class QuerySpecificationFacade<Entity extends InterfaceEntity<Id>, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification>
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
	 * Function that executes the find one operation in the
	 * {@link #findOneBySpecification(Object, Object...) findOneBySpec} method
	 */
	protected final FindOneBySpecFunction<Specification, OneResult> findOneBySpecFunction;

	/**
	 * Function that executes the exists by specification operation in the
	 * {@link #existsBySpecification(Object, Object...) existsBySpec} method
	 */
	protected final ExistsBySpecificationFunction<Specification, ExistsResult> existsBySpecFunction;

	/**
	 * Function that executes the count by specification operation in the
	 * {@link #countBySpecification(Object, Object...) countBySpec} method
	 */
	protected final CountBySpecificationFunction<Specification, CountResult> countBySpecFunction;

	/**
	 * Default constructor
	 * 
	 * @param builder Object in charge to construct this one, It can't be null, and
	 *                must provide the functions to execute the operations
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
		LOGGER.atDebug().log("Executing default findBySpecification, specification {}, directives {}",
				specification, directives);

		final var multipleResult = execute(
				specification, getPreFindBySpecificationFunction(), getFindBySpecificationFunction(),
				getPosFindBySpecificationFunction(), getErrorFindBySpecificationFunction(), directives);

		LOGGER.atDebug().log("Default findBySpecification executed, multipleResult {}, directives {}",
				multipleResult, directives);
		return multipleResult;
	}

	/**
	 * Gets the find by specification function {@link #findBySpecificationFunction},
	 * provided by the builder.
	 * 
	 * @return The find by specification function.
	 * 
	 * @see FindBySpecificationFunction
	 */
	@NotNull
	protected FindBySpecificationFunction<Specification, MultipleResult> getFindBySpecificationFunction() {
		LOGGER.atDebug().log("Returning findBySpecificationFunction function {}",
				findBySpecificationFunction.getName());
		return findBySpecificationFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OneResult findOneBySpecification(
			final Specification specification,
			final Object... directives) {
		LOGGER.atDebug().log("Executing default findOneBySpecification, specification {}, directives {}",
				specification, directives);

		final var oneResult = execute(
				specification, getPreFindOneBySpecificationFunction(), getFindOneBySpecFunction(),
				getPosFindOneBySpecificationFunction(), getErrorFindOneBySpecificationFunction(), directives);

		LOGGER.atDebug().log("Default findOneBySpecification executed, oneResult {}, directives {}",
				oneResult, directives);
		return oneResult;
	}

	/**
	 * Gets the find one by specification function {@link #findOneBySpecFunction},
	 * provided by the builder.
	 * 
	 * @return The find one by specification function.
	 * 
	 * @see FindOneBySpecFunction
	 */
	@NotNull
	protected FindOneBySpecFunction<Specification, OneResult> getFindOneBySpecFunction() {
		LOGGER.atDebug().log("Returning findOneBySpecFunction function {}", findOneBySpecFunction.getName());
		return findOneBySpecFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final ExistsResult existsBySpecification(final Specification specification, final Object... directives) {
		LOGGER.atDebug().log("Executing default existsBySpec, specification {}, directives {}", specification,
				directives);

		final var existsResult = execute(
				specification, getPreExistsBySpecificationFunction(), getExistsBySpecFunction(),
				getPosExistsBySpecificationFunction(), getErrorExistsBySpecificationFunction(), directives);

		LOGGER.atDebug().log("Default existsBySpec executed, existsResult {}, directives {}",
				existsResult, directives);
		return existsResult;
	}

	/**
	 * Gets the exists by specification function {@link #existsBySpecFunction},
	 * provided by the builder.
	 * 
	 * @return The exists by specification function.
	 * 
	 * @see ExistsBySpecificationFunction
	 */
	@NotNull
	protected ExistsBySpecificationFunction<Specification, ExistsResult> getExistsBySpecFunction() {
		LOGGER.atDebug().log("Returning existsBySpecFunction function {}", existsBySpecFunction.getName());
		return existsBySpecFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final CountResult countBySpecification(final Specification specification, final Object... directives) {
		LOGGER.atDebug().log("Executing default countBySpec, specification {}, directives {}", specification,
				directives);

		final var countResult = execute(
				specification, getPreCountBySpecificationFunction(), getCountBySpecFunction(),
				getPosCountBySpecificationFunction(), getErrorCountBySpecificationFunction(), directives);

		LOGGER.atDebug().log("Default countBySpec executed, countResult {}, directives {}", countResult, directives);
		return countResult;
	}

	/**
	 * Gets the count by specification function {@link #countBySpecFunction},
	 * provided by the builder.
	 * 
	 * @return The count by specification function.
	 * 
	 * @see CountBySpecificationFunction
	 */
	@NotNull
	protected CountBySpecificationFunction<Specification, CountResult> getCountBySpecFunction() {
		LOGGER.atDebug().log("Returning countBySpecFunction function {}", countBySpecFunction.getName());
		return countBySpecFunction;
	}
}
