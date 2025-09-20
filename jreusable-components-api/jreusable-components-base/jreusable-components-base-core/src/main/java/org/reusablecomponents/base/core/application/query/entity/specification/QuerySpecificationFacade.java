package org.reusablecomponents.base.core.application.query.entity.specification;

import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.COUNT_BY_SPECIFICATION;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.EXISTS_BY_SPECIFICATION;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITIES_BY_SPECIFICATION;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITY_BY_SPECIFICATION;

import java.util.List;
import java.util.function.BiFunction;

import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.application.base.FacadeBiFunction;
import org.reusablecomponents.base.core.application.base.FacadeTriFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default <code>InterfaceEntityQuerySpecificationFacade</code>'s
 * implementation.
 */
public non-sealed class QuerySpecificationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification>
		extends BaseFacade<Entity, Id>
		implements
		InterfaceQuerySpecificationFacade<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification> {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuerySpecificationFacade.class);

	/**
	 * Function that executes the find by an spec operation in the
	 * {@link #findBySpec(Object, Object...) findBySpec} method
	 */
	protected final BiFunction<Specification, Object[], MultipleResult> findBySpecificationFunction;

	/**
	 * Function that executes the find all operation in the
	 * {@link #findOneBySpec(Object, Object...) findOneBySpec} method
	 */
	protected final BiFunction<Specification, Object[], OneResult> findOneBySpecFunction;

	/**
	 * Function that executes the find all operation in the
	 * {@link #existsBySpec(Object, Object...) existsBySpec} method
	 */
	protected final BiFunction<Specification, Object[], ExistsResult> existsBySpecificationFunction;

	/**
	 * Function that executes the find all operation in the
	 * {@link #countBySpec(Object, Object...) countBySpec} method
	 */
	protected final BiFunction<Specification, Object[], CountResult> countBySpecificationFunction;

	/**
	 * Default constructor
	 * 
	 * @param builder Object in charge to construct this one
	 */
	protected QuerySpecificationFacade(
			final QuerySpecificationFacadeBuilder<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification> builder) {
		super(builder);
		this.findBySpecificationFunction = builder.findBySpecificationFunction;
		this.findOneBySpecFunction = builder.findOneByFunction;
		this.existsBySpecificationFunction = builder.existsBySpecificationFunction;
		this.countBySpecificationFunction = builder.countBySpecificationFunction;
	}

	/**
	 * Method executed in {@link #findBySpec(Object, Object...) findBySpec} method
	 * before the {@link #findBySpecificationFunction findBySpecificationFunction},
	 * use it to configure, change, etc. the input.
	 * 
	 * @param specification The query result controll
	 * @param directives    Objects used to configure the find all operation
	 * 
	 * @return A {@code Specification} object
	 */
	protected Specification preFindBySpec(final Specification specification, final Object... directives) {
		LOGGER.debug("Executing default preFindBySpec, preFindBySpec {}, directives {}", specification, directives);

		final var finalSpecification = compose(specification, getFindBySpecPreFunctions(), directives);

		LOGGER.debug("Default preFindBySpec executed, finalSpecification {}, directives {}",
				finalSpecification, directives);
		return finalSpecification;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #preFindBySpec(Object, Object...) preFindBySpec} method
	 */
	protected List<FacadeBiFunction<Specification>> getFindBySpecPreFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #findBySpec(Object, Object...) findBySpec} method
	 * after the {@link #findBySpecificationFunction findBySpecificationFunction},
	 * use it to configure, change, etc. the result.
	 * 
	 * @param multipleResult The query result
	 * @param directives     Objects used to configure the find all operation
	 * 
	 * @return A {@code MultipleResult} object
	 */
	protected MultipleResult posFindBySpec(final MultipleResult multipleResult, final Object... directives) {
		LOGGER.debug("Executing default posFindBySpec, multipleResult {}, directives {}",
				multipleResult,
				directives);

		final var finalMultipleResult = compose(multipleResult, getFindBySpecPosFunctions(), directives);

		LOGGER.debug("Default posFindBySpec executed, finalMultipleResult {}, directives {}",
				finalMultipleResult, directives);
		return finalMultipleResult;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #posFindBySpec(Object, Object...) posFindBySpec} method
	 */
	protected List<FacadeBiFunction<MultipleResult>> getFindBySpecPosFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #findBySpec(Object, Object...) findBySpec} method
	 * to handle {@link #findBySpecificationFunction findBySpecificationFunction}
	 * errors.
	 * 
	 * @param specification The object used to find by specification
	 * @param exception     Exception thrown by find specification operation
	 * @param directives    Objects used to configure the findAll operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorFindBySpecification(
			final Specification specification,
			final Exception exception,
			final Object... directives) {
		LOGGER.debug("Executing default errorFindBySpecification, specification {}, exception {}, directives {} ",
				specification,
				exception,
				directives);

		final var finalException = compose(exception, specification,
				getFindBySpecificationErrorFunctions(),
				directives);

		LOGGER.debug("Default errorFindBySpecification executed, specification {}, finalException {}, directives {} ",
				specification,
				finalException,
				directives);
		return finalException;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #errorFindBySpecification(Object, Object, Object...)
	 * errorFindBySpecification} method
	 */
	protected List<FacadeTriFunction<Exception, Specification>> getFindBySpecificationErrorFunctions() {
		return List.of();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MultipleResult findBySpec(
			final Specification specification,
			final Object... directives) {
		LOGGER.debug("Executing default findBySpec, specification {}, directives {}", specification, directives);

		final var multipleResult = execute(
				specification, FIND_ENTITIES_BY_SPECIFICATION, this::preFindBySpec, this::posFindBySpec,
				findBySpecificationFunction::apply, this::errorFindBySpecification, directives);

		LOGGER.debug("Default findBySpec executed, multipleResult {}, directives {}",
				multipleResult,
				directives);
		return multipleResult;
	}

	// ---------------------------------------------------------------------------

	/**
	 * Method used to change specification object before use it (findOneBy
	 * method).
	 * 
	 * @param specification The object to be changed
	 * 
	 * @return A new {@code Specification} object
	 */
	protected Specification preFindOneBy(final Specification specification, final Object... directives) {
		return specification;
	}

	/**
	 * Method used to change OneResult object after use it (findOneBy method).
	 * 
	 * @param oneResult The object to be changed
	 * 
	 * @return A new {@code OneResult} object
	 */
	protected OneResult posFindOneBy(final OneResult oneResult, final Object... directives) {
		return oneResult;
	}

	/**
	 * Method used to handle find one by specification errors.
	 * 
	 * @param specification The object used to find one by specification
	 * @param exception     Exception thrown by find one specification operation
	 * @param directives    Objects used to configure the find one by specification
	 *                      operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorFindOneBySpecification(
			final Specification specification,
			final Exception exception,
			final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OneResult findOneBySpec(
			final Specification specification,
			final Object... directives) {
		return execute(
				specification, FIND_ENTITY_BY_SPECIFICATION, this::preFindOneBy, this::posFindOneBy,
				findOneBySpecFunction::apply, this::errorFindOneBySpecification, directives);
	}

	// ---------------------------------------------------------------------------

	/**
	 * Method used to change specification object before use it (existsBy method).
	 * 
	 * @param specification The object to be changed
	 * 
	 * @return A new {@code Specification} object
	 */
	protected Specification preExistsBy(final Specification specification, final Object... directives) {
		return specification;
	}

	/**
	 * Method used to change existsResult object after use it (existsBy method).
	 * 
	 * @param existsResult The object to be changed
	 * 
	 * @return A new {@code ExistsResult} object
	 */
	protected ExistsResult posExistsBy(final ExistsResult existsResult, final Object... directives) {
		return existsResult;
	}

	/**
	 * Method used to handle exists by specification errors.
	 * 
	 * @param specification The object used to exists by specification
	 * @param exception     Exception thrown by exists by specification operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorExistsBySpecification(
			final Specification specification,
			final Exception exception,
			final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final ExistsResult existsBySpec(final Specification specification, final Object... directives) {
		return execute(
				specification, EXISTS_BY_SPECIFICATION, this::preExistsBy, this::posExistsBy,
				existsBySpecificationFunction::apply, this::errorExistsBySpecification, directives);
	}

	// ---------------------------------------------------------------------------

	/**
	 * Method used to change specification object before use it (countBy method).
	 * 
	 * @param specification The object to be changed
	 * 
	 * @return A new {@code Specification} object
	 */
	protected Specification preCountBy(final Specification specification, final Object... directives) {
		return specification;
	}

	/**
	 * Method used to change countResult object after use it (countBy method).
	 * 
	 * @param countResult The object to be changed
	 * 
	 * @return A new {@code CountResult} object
	 */
	protected CountResult posCountBy(final CountResult countResult, final Object... directives) {
		return countResult;
	}

	/**
	 * Method used to handle count by specification errors.
	 * 
	 * @param specification The object used to count by specification
	 * @param exception     Exception thrown by count specification operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorCountBySpecification(
			final Specification specification,
			final Exception exception,
			final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final CountResult countBySpec(final Specification specification, final Object... directives) {
		return execute(
				specification, COUNT_BY_SPECIFICATION, this::preCountBy, this::posCountBy,
				countBySpecificationFunction::apply, this::errorCountBySpecification, directives);
	}
}
