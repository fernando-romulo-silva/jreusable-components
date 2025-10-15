package org.reusablecomponents.base.core.application.query.entity.pagination;

import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ALL_ENTITIES_PAGEABLE;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITY_SORTED;

import java.util.List;
import java.util.function.BiFunction;

import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.util.function.compose.ComposeFunction2Args;
import org.reusablecomponents.base.core.infra.util.function.compose.ComposeFunction3Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default <code>InterfaceEntityQueryPaginationFacade</code>'s
 * implementation.
 */
public non-sealed class QueryPaginationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort>
		extends BaseFacade<Entity, Id>
		implements InterfaceQueryPaginationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryPaginationFacade.class);

	/**
	 * Function that executes the find all operation in the
	 * {@link #findAll(Object, Object...) findAll} method
	 */
	protected final BiFunction<Pageable, Object[], MultiplePagedResult> findAllFunction;

	/**
	 * Function that executes the find all operation in the
	 * {@link #findOne(Object, Object...) findAll} method
	 */
	protected final BiFunction<Sort, Object[], OneResult> findOneFunction;

	/**
	 * Default constructor
	 * 
	 * @param builder Object in charge to construct this one
	 */
	protected QueryPaginationFacade(
			final QueryPaginationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> builder) {
		super(builder);
		this.findAllFunction = builder.findAllFunction;
		this.findOneFunction = builder.findOneFunction;
	}

	/**
	 * Method executed in {@link #findAll(Object, Object...) findAll} method before
	 * the {@link #findAllFunction findAllFunction}, use it to configure, change,
	 * etc. the input.
	 * 
	 * @param pageable   The query result controll
	 * @param directives Objects used to configure the find all operation
	 * 
	 * @return A {@code Pageable} object
	 */
	protected Pageable preFindAll(final Pageable pageable, final Object... directives) {
		LOGGER.debug("Executing default preFindAll, pageable {}, directives {}", pageable, directives);

		final var finalPageable = compose(pageable, getFindAllPreFunctions(), directives);

		LOGGER.debug("Default preFindAll executed, finalPageable {}, directives {}", finalPageable, directives);
		return finalPageable;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #preFindAll(Object, Object...) preFindAll} method
	 */
	protected List<ComposeFunction2Args<Pageable>> getFindAllPreFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #findAll(Object, Object...) findAll} method after
	 * the {@link #findAllFunction findAllFunction}, use it to configure, change,
	 * etc. the result.
	 * 
	 * @param multiplePagedResult The query paged result
	 * @param directives          Objects used to configure the find all operation
	 * 
	 * @return A {@code MultiplePagedResult} object
	 */
	protected MultiplePagedResult posFindAll(
			final MultiplePagedResult multiplePagedResult,
			final Object... directives) {
		LOGGER.debug("Executing default posFindAll, multiplePagedResult {}, directives {}",
				multiplePagedResult, directives);

		final var finalMultiplePagedResult = compose(multiplePagedResult, getFindAllPosFunctions(), directives);

		LOGGER.debug("Default posFindAll executed, finalMultiplePagedResult {}, directives {}",
				finalMultiplePagedResult, directives);
		return finalMultiplePagedResult;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #posFindAll(Object, Object...) posFindAll} method
	 */
	protected List<ComposeFunction2Args<MultiplePagedResult>> getFindAllPosFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #findAll(Object, Object...) findAll} method to
	 * handle {@link #findAllFunction findAllFunction} errors.
	 * 
	 * @param pageable   The query result controll
	 * @param exception  Exception thrown by findAll operation
	 * @param directives Objects used to configure the findAll operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorFindAll(
			final Pageable pageable,
			final Exception exception,
			final Object... directives) {
		LOGGER.debug("Executing default errorFindAll, pageable {}, exception {}, directives {} ",
				pageable, exception, directives);

		final var finalException = compose(exception, pageable, getFindAllErrorFunctions(), directives);

		LOGGER.debug("Default errorFindAll executed, pageable {}, finalException {}, directives {} ",
				pageable, finalException, directives);
		return finalException;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #errorFindAll(Object, Object, Object...) errorFindAll} method
	 */
	protected List<ComposeFunction3Args<Exception, Pageable>> getFindAllErrorFunctions() {
		return List.of();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SafeVarargs
	public final MultiplePagedResult findAll(final Pageable pageable, final Object... directives) {
		LOGGER.debug("Executing default findAll, pageable {}, directives {}", pageable, directives);

		final var multipleResult = execute(
				pageable, FIND_ALL_ENTITIES_PAGEABLE, this::preFindAll,
				this::posFindAll, findAllFunction::apply, this::errorFindAll, directives);

		LOGGER.debug("Default findAll executed, multipleResult {}, directives {}", multipleResult, directives);
		return multipleResult;
	}

	/**
	 * Method executed in {@link #findOne(Object, Object...) findOne} method before
	 * the {@link #findOneFunction findOneFunction}, use it to configure, change,
	 * etc. the input.
	 * 
	 * @param sort       Object used to sort the query
	 * @param directives Objects used to configure the find all operation
	 * 
	 * @return A {@code Object[]} object
	 */
	protected Sort preFindOne(final Sort sort, final Object... directives) {
		LOGGER.debug("Executing default preFindOne, sort {}, directives {}", sort, directives);

		final var finalSort = compose(sort, getFindOnePreFunctions(), directives);

		LOGGER.debug("Default preFindOne executed, finalSort {}, directives {}", finalSort, directives);
		return finalSort;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #preFindOne(Object, Object...) preFindOne} method
	 */
	protected List<ComposeFunction2Args<Sort>> getFindOnePreFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #findOne(Object, Object...) findOne} method after
	 * the {@link #findOneFunction findOneFunction}, use it to configure, change,
	 * etc. the result.
	 * 
	 * @param oneResult  The query paged result
	 * @param directives Objects used to configure the find all operation
	 * 
	 * @return A {@code OneResult} object
	 */
	protected OneResult posFindOne(final OneResult oneResult, final Object... directives) {
		LOGGER.debug("Executing default oneResult, multiplePagedResult {}, directives {}", oneResult, directives);

		final var finalOneResult = compose(oneResult, getFindOnePosFunctions(), directives);

		LOGGER.debug("Default posFindOne executed, finalOneResult {}, directives {}", finalOneResult, directives);
		return finalOneResult;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #posFindOne(Object, Object...) posFindOne} method
	 */
	protected List<ComposeFunction2Args<OneResult>> getFindOnePosFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #findOne(Object, Object...) findOne} method to
	 * handle {@link #findOneFunction findOneFunction} errors.
	 * 
	 * @param sort       The query result order
	 * @param exception  Exception thrown by findOne operation
	 * @param directives Objects used to configure the findOne operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorFindOne(
			final Sort sort,
			final Exception exception,
			final Object... directives) {
		LOGGER.debug("Executing default errorFindOne, pageable {}, exception {}, directives {} ",
				sort, exception, directives);

		final var finalException = compose(exception, sort, getFindOneErrorFunctions(), directives);

		LOGGER.debug("Default errorFindOne executed, pageable {}, finalException {}, directives {} ",
				sort, finalException, directives);
		return finalException;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #errorFindOne(Object, Object, Object...) errorFindOne} method
	 */
	protected List<ComposeFunction3Args<Exception, Sort>> getFindOneErrorFunctions() {
		return List.of();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OneResult findOne(final Sort sort, final Object... directives) {
		LOGGER.debug("Executing default findOne, pageable {}, directives {}", sort, directives);

		final var oneResult = execute(
				sort, FIND_ENTITY_SORTED, this::preFindOne,
				this::posFindOne, findOneFunction::apply, this::errorFindOne, directives);

		LOGGER.debug("Default findOne executed, oneResult {}, directives {}", oneResult, directives);
		return oneResult;
	}
}
