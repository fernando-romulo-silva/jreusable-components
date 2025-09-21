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
	protected final BiFunction<Specification, Object[], MultipleResult> findBySpecFunction;

	/**
	 * Function that executes the find all operation in the
	 * {@link #findOneBySpec(Object, Object...) findOneBySpec} method
	 */
	protected final BiFunction<Specification, Object[], OneResult> findOneBySpecFunction;

	/**
	 * Function that executes the find all operation in the
	 * {@link #existsBySpec(Object, Object...) existsBySpec} method
	 */
	protected final BiFunction<Specification, Object[], ExistsResult> existsBySpecFunction;

	/**
	 * Function that executes the find all operation in the
	 * {@link #countBySpec(Object, Object...) countBySpec} method
	 */
	protected final BiFunction<Specification, Object[], CountResult> countBySpecFunction;

	/**
	 * Default constructor
	 * 
	 * @param builder Object in charge to construct this one
	 */
	protected QuerySpecificationFacade(
			final QuerySpecificationFacadeBuilder<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification> builder) {
		super(builder);
		this.findBySpecFunction = builder.findBySpecificationFunction;
		this.findOneBySpecFunction = builder.findOneByFunction;
		this.existsBySpecFunction = builder.existsBySpecificationFunction;
		this.countBySpecFunction = builder.countBySpecificationFunction;
	}

	/**
	 * Method executed in {@link #findBySpec(Object, Object...) findBySpec} method
	 * before the {@link #findBySpecFunction findBySpecFunction}, use it to
	 * configure, change, etc. the input.
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
	 * after the {@link #findBySpecFunction findBySpecFunction}, use it to
	 * configure, change, etc. the result.
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
	 * to handle {@link #findBySpecFunction findBySpecFunction} errors.
	 * 
	 * @param specification The object used to find by specification
	 * @param exception     Exception thrown by find specification operation
	 * @param directives    Objects used to configure the findAll operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorFindBySpec(
			final Specification specification,
			final Exception exception,
			final Object... directives) {
		LOGGER.debug("Executing default errorFindBySpec, specification {}, exception {}, directives {} ",
				specification, exception, directives);

		final var finalException = compose(exception, specification, getFindBySpecErrorFunctions(), directives);

		LOGGER.debug("Default errorFindBySpec executed, specification {}, finalException {}, directives {} ",
				specification, finalException, directives);
		return finalException;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #errorFindBySpec(Object, Object, Object...) errorFindBySpec} method
	 */
	protected List<FacadeTriFunction<Exception, Specification>> getFindBySpecErrorFunctions() {
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
				findBySpecFunction::apply, this::errorFindBySpec, directives);

		LOGGER.debug("Default findBySpec executed, multipleResult {}, directives {}",
				multipleResult, directives);
		return multipleResult;
	}

	/**
	 * Method executed in {@link #findOneBySpec(Object, Object...) findOneBySpec}
	 * method before the {@link #findOneBySpecFunction findOneBySpecFunction}, use
	 * it to configure, change, etc. the input.
	 * 
	 * @param specification The query result controll
	 * @param directives    Objects used to configure the find all operation
	 * 
	 * @return A {@code Specification} object
	 */
	protected Specification preFindOneBySpec(final Specification specification, final Object... directives) {
		LOGGER.debug("Executing default preFindOneBySpec, preFindBySpec {}, directives {}", specification, directives);

		final var finalSpecification = compose(specification, getFindOneBySpecPreFunctions(), directives);

		LOGGER.debug("Default preFindOneBySpec executed, finalSpecification {}, directives {}",
				finalSpecification, directives);
		return finalSpecification;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #preFindOneBySpec(Object, Object...) preFindOneBySpec} method
	 */
	protected List<FacadeBiFunction<Specification>> getFindOneBySpecPreFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #findOneBySpec(Object, Object...) findOneBySpec}
	 * method after the {@link #findOneBySpecFunction findOneBySpecFunction}, use it
	 * to configure, change, etc. the result.
	 * 
	 * @param oneResult  The query result
	 * @param directives Objects used to configure the find all operation
	 * 
	 * @return A {@code oneResult} object
	 */
	protected OneResult posFindOneBySpec(final OneResult oneResult, final Object... directives) {
		LOGGER.debug("Executing default posFindOneBySpec, oneResult {}, directives {}", oneResult, directives);

		final var finalOneResult = compose(oneResult, getFindOneBySpecPosFunctions(), directives);

		LOGGER.debug("Default posFindOneBySpec executed, finalOneResult {}, directives {}",
				finalOneResult, directives);
		return finalOneResult;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #posFindOneBySpec(Object, Object...) posFindOneBySpec} method
	 */
	protected List<FacadeBiFunction<OneResult>> getFindOneBySpecPosFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #findOneBySpec(Object, Object...) findOneBySpec}
	 * method to handle {@link #findBySpecFunction findBySpecFunction} errors.
	 * 
	 * @param specification The object used to find by specification
	 * @param exception     Exception thrown by find specification operation
	 * @param directives    Objects used to configure the findAll operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorFindOneBySpec(
			final Specification specification,
			final Exception exception,
			final Object... directives) {
		LOGGER.debug("Executing default errorFindOneBySpec, specification {}, exception {}, directives {} ",
				specification, exception, directives);

		final var finalException = compose(exception, specification, getFindOneBySpecErrorFunctions(), directives);

		LOGGER.debug("Default errorFindOneBySpec executed, specification {}, finalException {}, directives {} ",
				specification, finalException, directives);
		return finalException;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #errorFindOneBySpec(Object, Object, Object...) errorFindOneBySpec}
	 * method
	 */
	protected List<FacadeTriFunction<Exception, Specification>> getFindOneBySpecErrorFunctions() {
		return List.of();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OneResult findOneBySpec(
			final Specification specification,
			final Object... directives) {
		LOGGER.debug("Executing default findOneBySpec, specification {}, directives {}", specification, directives);

		final var oneResult = execute(
				specification, FIND_ENTITY_BY_SPECIFICATION, this::preFindOneBySpec, this::posFindOneBySpec,
				findOneBySpecFunction::apply, this::errorFindOneBySpec, directives);

		LOGGER.debug("Default findOneBySpec executed, oneResult {}, directives {}",
				oneResult, directives);
		return oneResult;
	}

	/**
	 * Method executed in {@link #existsBySpec(Object, Object...) existsBySpec}
	 * method before the {@link #existsBySpecFunction existsBySpecFunction}, use it
	 * to configure, change, etc. the input.
	 * 
	 * @param specification The query result controll
	 * @param directives    Objects used to configure the find all operation
	 * 
	 * @return A {@code Specification} object
	 */
	protected Specification preExistsBy(final Specification specification, final Object... directives) {
		LOGGER.debug("Executing default preExistsBy, preFindBySpec {}, directives {}", specification, directives);

		final var finalSpecification = compose(specification, getExistsBySpecPreFunctions(), directives);

		LOGGER.debug("Default preExistsBy executed, finalSpecification {}, directives {}",
				finalSpecification, directives);
		return finalSpecification;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #preExistsBy(Object, Object...) preExistsBy} method
	 */
	protected List<FacadeBiFunction<Specification>> getExistsBySpecPreFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #existsBySpec(Object, Object...) existsBySpec}
	 * method after the {@link #existsBySpecFunction existsBySpecFunction}, use it
	 * to configure, change, etc. the result.
	 * 
	 * @param existsResult The query result
	 * @param directives   Objects used to configure the find all operation
	 * 
	 * @return A {@code ExistsResult} object
	 */
	protected ExistsResult posExistsBy(final ExistsResult existsResult, final Object... directives) {
		LOGGER.debug("Executing default posExistsBy, existsResult {}, directives {}",
				existsResult, directives);

		final var finalExistsResult = compose(existsResult, getExistsBySpecPosFunctions(), directives);

		LOGGER.debug("Default posExistsBy executed, finalExistsResult {}, directives {}",
				finalExistsResult, directives);
		return finalExistsResult;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #posExistsBy(Object, Object...) posExistsBy} method
	 */
	protected List<FacadeBiFunction<ExistsResult>> getExistsBySpecPosFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #existsBySpec(Object, Object...) existsBySpec}
	 * method to handle {@link #existsBySpecFunction existsBySpecFunction} errors.
	 * 
	 * @param specification The object used to find by specification
	 * @param exception     Exception thrown by find specification operation
	 * @param directives    Objects used to configure the findAll operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorExistsBySpec(
			final Specification specification,
			final Exception exception,
			final Object... directives) {
		LOGGER.debug("Executing default errorExistsBySpec, specification {}, exception {}, directives {} ",
				specification, exception, directives);

		final var finalException = compose(exception, specification, getExistsBySpecErrorFunctions(), directives);

		LOGGER.debug("Default errorExistsBySpec executed, specification {}, finalException {}, directives {} ",
				specification, finalException, directives);
		return finalException;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #errorExistsBySpec(Object, Object, Object...) errorExistsBySpec}
	 * method
	 */
	protected List<FacadeTriFunction<Exception, Specification>> getExistsBySpecErrorFunctions() {
		return List.of();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final ExistsResult existsBySpec(final Specification specification, final Object... directives) {
		LOGGER.debug("Executing default existsBySpec, specification {}, directives {}", specification, directives);

		final var existsResult = execute(
				specification, EXISTS_BY_SPECIFICATION, this::preExistsBy, this::posExistsBy,
				existsBySpecFunction::apply, this::errorExistsBySpec, directives);

		LOGGER.debug("Default existsBySpec executed, existsResult {}, directives {}",
				existsResult, directives);
		return existsResult;
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
				countBySpecFunction::apply, this::errorCountBySpecification, directives);
	}
}
