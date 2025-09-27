package org.reusablecomponents.base.core.application.query.entity.simple;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.COUNT_ALL;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.EXISTS_ALL;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.EXISTS_BY_ID;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ALL_ENTITIES;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITY_BY_ID;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.application.base.functions.FacadeFunctionNoArgs;
import org.reusablecomponents.base.core.application.base.functions.FacadeFunctionOneArg;
import org.reusablecomponents.base.core.application.base.functions.FacadeFunctionTwoArgs;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.TypeToken;

/**
 * The default <code>InterfaceEntityQueryFacade</code>'s implementation.
 */
public non-sealed class QueryFacade< // generics
		// default
		Entity extends AbstractEntity<Id>, Id, // basic
		// input id
		QueryIdIn, //
		// results
		OneResult, MultipleResult, CountResult, ExistsResult>
		// Base Facade
		extends BaseFacade<Entity, Id>
		// Interface QueryFacade
		implements InterfaceQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> {

	private static final String NON_NULL_ID_MSG = "Please pass a non-null %s id";

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryFacade.class);

	/**
	 * Function that executes the find by id operation in the
	 * {@link #findById(Object, Object...) findById} method
	 */
	protected final BiFunction<QueryIdIn, Object[], OneResult> findByIdFunction;

	/**
	 * Function that executes the find all operation in the
	 * {@link #findAll(Object...) findAll} method
	 */
	protected final Function<Object[], MultipleResult> findAllFunction;

	/**
	 * Function that executes the count all operation in the
	 * {@link #countAll(Object...) countAll} method
	 */
	protected final Function<Object[], CountResult> countAllFunction;

	/**
	 * Function that executes the exists all operation in the
	 * {@link #existsAll(Object...) existsAll} method
	 */
	protected final Function<Object[], ExistsResult> existsAllFunction;

	/**
	 * Function that executes the exists by id operation in the
	 * {@link #existsById(Object, Object...) existsById} method
	 */
	protected final BiFunction<QueryIdIn, Object[], ExistsResult> existsByIdFunction;

	/**
	 * QueryIdIn class used on find by id {@link #findById(Object, Object...)
	 * findById} method
	 */
	protected final Class<QueryIdIn> queryIdInClazz;

	/**
	 * Default constructor
	 * 
	 * @param builder Object in charge to construct this one
	 */
	protected QueryFacade(
			final QueryFacadeBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> builder) {
		super(builder);
		this.existsByIdFunction = builder.existsByIdFunction;
		this.findByIdFunction = builder.findByIdFunction;
		this.findAllFunction = builder.findAllFunction;
		this.countAllFunction = builder.countAllFunction;
		this.existsAllFunction = builder.existsAllFunction;
		this.queryIdInClazz = retrieveQueryIdClazz();
	}

	@SuppressWarnings("unchecked")
	private Class<QueryIdIn> retrieveQueryIdClazz() {
		final var entityTypeToken = new TypeToken<QueryIdIn>(getClass()) {
			private static final long serialVersionUID = 1L;
		};
		final var rawType = (Class<QueryIdIn>) entityTypeToken.getRawType();
		LOGGER.debug("Class QueryIdIn '{}'", rawType);
		return rawType;
	}

	/**
	 * Method executed in {@link #findAll(Object...) findAll} method before
	 * the {@link #findAllFunction findAllFunction}, use it to configure, change,
	 * etc. the input.
	 * 
	 * @param directives Objects used to configure the find all operation
	 * 
	 * @return A {@code Object[]} object
	 */
	protected Object[] preFindAll(final Object... directives) {
		// final var formatDirectives = Optional.ofNullable(directives)
		// .map(params -> params.get("format"))
		// .stream()
		// .flatMap(Arrays::stream)
		// .collect(Collectors.toList());
		// .anyMatch("full"::equalsIgnoreCase);
		LOGGER.debug("Executing default preFindAll, directives {}", directives);

		compose(getFindAllPreFunctions(), directives);

		LOGGER.debug("Default preFindAll executed, directives {}", directives);
		return directives;
	}

	/**
	 * Get functions executed in sequence in the {@link #preFindAll(Object...)
	 * preFindAll} method
	 */
	protected List<FacadeFunctionNoArgs> getFindAllPreFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #findAll(Object...) findAll} method after
	 * {@link #findAllFunction findAllFunction}, use it to configure, change, etc.
	 * the output.
	 * 
	 * @param multipleResult The findAll result object
	 * @param directives     Objects used to configure the findAll operation
	 * 
	 * @return A {@code MultipleResult} object
	 */
	protected MultipleResult posFindAll(final MultipleResult multipleResult, final Object... directives) {
		LOGGER.debug("Executing default posFindAll, multipleResult {}, directives {} ", multipleResult, directives);

		final var finalMultipleResult = compose(multipleResult, getFindAllPosFunctions(), directives);

		LOGGER.debug("Default preFindAll executed, finalMultipleResult {}, directives {}",
				finalMultipleResult, directives);
		return multipleResult;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #posFindById(Object, Object...) posFindById} method
	 */
	protected List<FacadeFunctionOneArg<MultipleResult>> getFindAllPosFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #findAll(Object...) findAll} method to handle
	 * {@link #findAllFunction findAllFunction} errors.
	 * 
	 * @param exception  Exception thrown by findAll operation
	 * @param directives Objects used to configure the findAll operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorFindAll(
			final Exception exception,
			final Object... directives) {
		LOGGER.debug("Executing default errorFindAll, exception {}, directives {}",
				getRootCause(exception), directives);

		final var finalException = compose(exception, getFindAllErrorFunctions(), directives);

		LOGGER.debug("Default errorFindAll executed, finalException {}, directives {}",
				finalException, directives);
		return exception;
	}

	/**
	 * Get functions executed in sequence in the {@link #findAll(Object...) findAll}
	 * method
	 */
	protected List<FacadeFunctionOneArg<Exception>> getFindAllErrorFunctions() {
		return List.of();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MultipleResult findAll(final Object... directives) {
		LOGGER.debug("Executing default findAll, directives {} ", directives);

		final var multipleResult = execute(
				FIND_ALL_ENTITIES, this::preFindAll, this::posFindAll,
				findAllFunction::apply, this::errorFindAll, directives);

		LOGGER.debug("Default findAll, multipleResult {}, directives {}", directives, multipleResult);
		return multipleResult;
	}

	/**
	 * Method executed in {@link #findById(Object, Object...) findById} method
	 * before the {@link #findByIdFunction findByIdFunction}, use it to configure,
	 * change, etc. the input.
	 * 
	 * @param queryIdIn  The object id you want to use to retrieve on the
	 *                   persistence mechanism
	 * @param directives Objects used to configure the findBy operation
	 * 
	 * @return A new {@code QueryIdIn} object
	 */
	protected QueryIdIn preFindById(final QueryIdIn queryIdIn, final Object... directives) {
		LOGGER.debug("Executing default preFindBy, queryIdIn {}, directives {} ", queryIdIn, directives);

		final var finalQueryIdIn = compose(queryIdIn, getFindByIdPreFunctions(), directives);

		LOGGER.debug("Default preFindBy executed, finalQueryIdIn {}, directives {} ", finalQueryIdIn, directives);
		return finalQueryIdIn;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #preFindById(Object, Object...) preFindById} method
	 */
	protected List<FacadeFunctionOneArg<QueryIdIn>> getFindByIdPreFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #findById(Object, Object...) findById} method after
	 * the {@link #findByIdFunction findByIdFunction}, use it to configure, change,
	 * etc. the result.
	 * 
	 * 
	 * @param oneResult  The findBy result object
	 * @param directives Objects used to configure the findBy operation
	 * 
	 * @return A new {@code OneResult} object
	 */
	protected OneResult posFindById(final OneResult oneResult, final Object... directives) {
		LOGGER.debug("Executing default posFindBy, oneResult {}, directives {} ", oneResult, directives);

		final var finalOneResult = compose(oneResult, getFindByIdPosFunctions(), directives);

		LOGGER.debug("Default posFindBy executed, finalOneResult {}, directives {} ", finalOneResult, directives);
		return finalOneResult;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #posFindById(Object, Object...) posFindById} method
	 */
	protected List<FacadeFunctionOneArg<OneResult>> getFindByIdPosFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #findById(Object, Object...) findById} method to
	 * handle {@link #findByIdFunction findByIdFunction} errors.
	 * 
	 * @param queryIdIn  The object used to find by id
	 * @param exception  Exception thrown by findBy operation
	 * @param directives Objects used to configure the findById operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorFindById(
			final QueryIdIn queryIdIn,
			final Exception exception,
			final Object... directives) {
		LOGGER.debug("Executing default errorFindById, queryIdIn {}, exception {}, directives {} ",
				queryIdIn, exception, directives);

		final var finalException = compose(exception, queryIdIn, getFindByIdErrorFunctions(), directives);

		LOGGER.debug("Default errorFindById executed, queryIdIn {}, finalException {}, directives {} ",
				queryIdIn, finalException, directives);
		return exception;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #errorFindById(Object, Object, Object...) errorFindById} method
	 */
	protected List<FacadeFunctionTwoArgs<Exception, QueryIdIn>> getFindByIdErrorFunctions() {
		return List.of();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OneResult findById(final QueryIdIn queryIdIn, final Object... directives) {
		LOGGER.debug("Executing default findById, queryIdIn {}, directives {}", queryIdIn, directives);

		checkNotNull(queryIdIn, NON_NULL_ID_MSG, getEntityClazz().getSimpleName());

		final var oneResult = execute(
				queryIdIn, FIND_ENTITY_BY_ID, this::preFindById, this::posFindById,
				findByIdFunction::apply, this::errorFindById, directives);

		LOGGER.debug("Default findById executed, oneResult {}, directives {}", oneResult, directives);
		return oneResult;
	}

	/**
	 * Method executed in {@link #countAll(Object...) countAll} method before
	 * the {@link #countAllFunction countAllFunction}, use it to configure, change,
	 * etc. the input.
	 * 
	 * @param directives Objects used to configure the find all operation
	 * 
	 * @return A {@code Object[]} object
	 */
	protected Object[] preCountAll(final Object... directives) {
		LOGGER.debug("Executing default preCountAll, directives {}", directives);

		compose(getPreCountAllFunctions(), directives);

		LOGGER.debug("Default preCountAll executed, directives {}", directives);
		return directives;
	}

	/**
	 * Get functions executed in sequence in the {@link #preCountAll(Object...)
	 * preCountAll} method
	 */
	protected List<FacadeFunctionNoArgs> getPreCountAllFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #countAll(Object...) countAll} method after
	 * {@link #countAllFunction countAllFunction}, use it to configure, change, etc.
	 * the output.
	 * 
	 * @param countResult The count all result object
	 * @param directives  Objects used to configure the findAll operation
	 * 
	 * @return A {@code MultipleResult} object
	 */
	protected CountResult posCountAll(final CountResult countResult, final Object... directives) {
		LOGGER.debug("Executing default posCountAll, countResult {}, directives {} ", countResult, directives);

		final var finalCountResult = compose(countResult, getPosCountAllFunctions(), directives);

		LOGGER.debug("Default posCountAll executed, finalCountResult {}, directives {}", finalCountResult, directives);
		return finalCountResult;
	}

	/**
	 * Get functions executed in sequence in the {@link #posCountAll(Object...)
	 * posCountAll} method
	 */
	protected List<FacadeFunctionOneArg<CountResult>> getPosCountAllFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #countAll(Object...) countAll} method to handle
	 * {@link #countAllFunction countAllFunction} errors.
	 * 
	 * @param exception  Exception thrown by count all operation
	 * @param directives Objects used to configure the count all operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorCountAll(final Exception exception, final Object... directives) {
		LOGGER.debug("Executing default errorCountAll, exception {}, directives {}",
				getRootCause(exception), directives);

		final var finalException = compose(exception, getCountAllErrorFunctions(), directives);

		LOGGER.debug("Default errorCountAll executed, finalException {}, directives {}", finalException, directives);
		return finalException;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #errorCountAll(Object...) errorCountAll} method
	 */
	protected List<FacadeFunctionOneArg<Exception>> getCountAllErrorFunctions() {
		return List.of();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CountResult countAll(final Object... directives) {
		LOGGER.debug("Executing default countAll, directives {} ", directives);

		final var countResult = execute(
				COUNT_ALL, this::preCountAll, this::posCountAll,
				countAllFunction::apply, this::errorCountAll, directives);

		LOGGER.debug("Default countAll executed, countResult {}, directives {}", countResult, directives);
		return countResult;
	}

	/**
	 * Method executed in {@link #existsAll(Object...) #existsAll} method
	 * before the {@link #existsAllFunction existsAllFunction}, use it to
	 * configure, change, etc. the input.
	 * 
	 * @param directives Objects used to configure the findBy operation
	 * 
	 * @return A new {@code Object[]} object
	 */
	protected Object[] preExistsAll(final Object... directives) {
		LOGGER.debug("Executing default preExistsAll, directives {}", directives);

		compose(getPreExistsAllFunctions(), directives);

		LOGGER.debug("Default preExistsAll executed, directives {}", directives);
		return directives;
	}

	/**
	 * Get functions executed in sequence in the {@link #preExistsAll(Object...)
	 * preExistsAll} method
	 */
	protected List<FacadeFunctionNoArgs> getPreExistsAllFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #existsAll(Object...) existsAll} method after
	 * {@link #existsAllFunction existsAllFunction}, use it to configure, change,
	 * etc. the output.
	 * 
	 * @param existsResult The exists all result object
	 * @param directives   Objects used to configure the findAll operation
	 * 
	 * @return A {@code ExistsResult} object
	 */
	protected ExistsResult posExistsAll(final ExistsResult existsResult, final Object... directives) {
		LOGGER.debug("Executing default posExistsAll, countResult {}, directives {} ", existsResult, directives);

		final var finalExistsResult = compose(existsResult, getPosExistsAllFunctions(), directives);

		LOGGER.debug("Default posExistsAll executed, finalExistsResult {}, directives {}",
				finalExistsResult, directives);
		return finalExistsResult;
	}

	/**
	 * Get functions executed in sequence in the {@link #posExistsAll(Object...)
	 * posExistsAll} method
	 */
	protected List<FacadeFunctionOneArg<ExistsResult>> getPosExistsAllFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #existsAll(Object...) existsAll} method to handle
	 * {@link #existsAllFunction existsAllFunction} errors.
	 * 
	 * @param exception  Exception thrown by exists all operation
	 * @param directives Objects used to configure the exists all operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorExistsAll(final Exception exception, final Object... directives) {
		LOGGER.debug("Executing default errorCountAll, exception {}, directives {}",
				getRootCause(exception), directives);

		final var finalException = compose(exception, getExistsAllErrorFunctions(), directives);

		LOGGER.debug("Default errorCountAll executed, finalException {}, directives {}", finalException, directives);
		return finalException;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #existsAll(Object...) existsAll} method
	 */
	protected List<FacadeFunctionOneArg<Exception>> getExistsAllErrorFunctions() {
		return List.of();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExistsResult existsAll(final Object... directives) {
		LOGGER.debug("Executing default existsAll, directives {} ", directives);

		final var existsResult = execute(
				EXISTS_ALL, this::preExistsAll, this::posExistsAll,
				existsAllFunction::apply, this::errorExistsAll, directives);

		LOGGER.debug("Default existsAll executed, existsResult {}, directives {}", existsResult, directives);
		return existsResult;
	}

	/**
	 * Method executed in {@link #existsById(Object, Object...) #existsById} method
	 * before the {@link #existsByIdFunction existsByIdFunction}, use it to
	 * configure, change, etc. the input.
	 * 
	 * This method execute {@link #existsByIdPreFunctions existsByIdPreFunctions} in
	 * sequence
	 * 
	 * @param queryIdIn  The object id you want to use to retrieve on the
	 *                   persistence mechanism
	 * @param directives Objects used to configure the findBy operation
	 * 
	 * @return A new {@code QueryIdIn} object
	 */
	protected QueryIdIn preExistsById(final QueryIdIn queryIdIn, final Object... directives) {
		LOGGER.debug("Executing default preExistsById, queryIdIn {}, directives {} ", queryIdIn, directives);

		final var finalQueryIdIn = compose(queryIdIn, getExistsByIdPreFunctions(), directives);

		LOGGER.debug("Default preExistsById executed, finalQueryIdIn {}, directives {} ", finalQueryIdIn, directives);
		return finalQueryIdIn;
	}

	/**
	 * Return functions executed in sequence in the
	 * {@link #preExistsById(Object, Object...) preExistsById} method
	 */
	protected List<FacadeFunctionOneArg<QueryIdIn>> getExistsByIdPreFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #existsById(Object, Object...) #existsById} method
	 * after the {@link #existsByIdFunction existsByIdFunction}, use it to
	 * configure, change, etc. the output.
	 * 
	 * @param existsResult The result of existsByIdFunction
	 * @param directives   Objects used to configure the findById operation
	 * 
	 * @return A new {@code ExistsResult} object
	 */
	protected ExistsResult posExistsById(final ExistsResult existsResult, final Object... directives) {
		LOGGER.debug("Executing default posExistsById, existsResult {}, directives {} ", existsResult, directives);

		final var finalExistsResult = compose(existsResult, getExistsByIdPosFunctions(), directives);

		LOGGER.debug("Default posExistsById executed, finalExistsResult {}, directives {} ",
				finalExistsResult, directives);
		return finalExistsResult;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #posExistsById(Object, Object...) posExistsById} method
	 */
	protected List<FacadeFunctionOneArg<ExistsResult>> getExistsByIdPosFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #existsById(Object, Object...) existsById} method
	 * to handle {@link #existsByIdFunction existsByIdFunction} errors. <br />
	 * 
	 * This method execute {@link #existsByIdErrorFunctions
	 * existsByIdErrorFunctions} in sequence
	 * 
	 * @param queryIdIn  The object you tried to use on query by id operation
	 * @param exception  Exception thrown by existsByIdFunction
	 * @param directives Objects used to configure the save operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorExistsById(
			final QueryIdIn queryIdIn,
			final Exception exception,
			final Object... directives) {
		LOGGER.debug("Executing default errorExistsById, queryIdIn {}, exception {}, directives {} ",
				queryIdIn, exception, directives);

		final var finalException = compose(exception, queryIdIn, getExistsByIdErrorFunctions(), directives);

		LOGGER.debug("Default errorExistsById executed, queryIdIn {}, finalException {}, directives {} ",
				queryIdIn, finalException, directives);
		return finalException;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #errorExistsById(Object, Object, Object...) errorExistsById} method
	 */
	protected List<FacadeFunctionTwoArgs<Exception, QueryIdIn>> getExistsByIdErrorFunctions() {
		return List.of();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExistsResult existsById(final QueryIdIn queryIdIn, final Object... directives) {
		LOGGER.debug("Executing default existsById, queryIdIn {}, directives {} ", queryIdIn, directives);

		checkNotNull(queryIdIn, NON_NULL_ID_MSG, getEntityClazz().getSimpleName());

		final var existsResult = execute(
				queryIdIn, EXISTS_BY_ID, this::preExistsById,
				this::posExistsById, existsByIdFunction::apply, this::errorExistsById, directives);

		LOGGER.debug("Default existsById executed, existsResult {}, directives {} ", existsResult, directives);
		return existsResult;
	}
}