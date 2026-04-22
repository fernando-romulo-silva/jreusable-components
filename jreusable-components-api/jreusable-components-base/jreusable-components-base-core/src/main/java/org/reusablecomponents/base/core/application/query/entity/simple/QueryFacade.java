package org.reusablecomponents.base.core.application.query.entity.simple;

import static com.google.common.base.Preconditions.checkNotNull;

import org.reusablecomponents.base.core.application.query.entity.simple.function.count_all.CountAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_all.ExistsAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_by_id.ExistsByIdFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_all.FindAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_by_id.FindByIdFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.TypeToken;

import jakarta.validation.constraints.NotNull;

/**
 * The default <code>InterfaceQueryFacade</code>'s implementation.
 * 
 * <p>
 * This class provides default implementations for the basic query operations:
 * <ul>
 * <li>find by id</li>
 * <li>find all</li>
 * <li>count all</li>
 * <li>exists all</li>
 * <li>exists by id</li>
 * </ul>
 * <p>
 * 
 * @param <Entity>         The entity type
 * @param <Id>             The entity id type
 * @param <QueryIdIn>      The input id type for the find by id and exists by id
 * @param <OneResult>      The one-result type, like the entity or wrap type
 *                         like Mono<Entity>
 * @param <MultipleResult> The multiple-result type, like List<Entity>,
 *                         Iterable<Entity>, or a wrap type like
 *                         Mono<List<Entity>>
 * @param <CountResult>    The count-result type, like Long, Integer, or a wrap
 *                         type like Mono<Long>
 * @param <ExistsResult>   The exist-result type, like Boolean or a wrap type
 *                         like Mono<Boolean>
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 * 
 * @see AbstractQueryFacade
 * @see InterfaceQueryFacade
 */
public non-sealed class QueryFacade<Entity extends AbstractEntity<Id>, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>
		extends AbstractQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>
		implements InterfaceQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> {

	private static final String NON_NULL_ID_MSG = "Please pass a non-null %s id";

	/**
	 * Logger instance for this class.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryFacade.class);

	/**
	 * Function that executes the find by id operation in the
	 * {@link #findById(Object, Object...) findById} method
	 * 
	 * @see FindByIdFunction
	 */
	protected final FindByIdFunction<QueryIdIn, OneResult> findByIdFunction;

	/**
	 * Function that executes the find all operation in the
	 * {@link #findAll(Object...) findAll} method
	 * 
	 * @see FindAllFunction
	 */
	protected final FindAllFunction<MultipleResult> findAllFunction;

	/**
	 * Function that executes the count all operation in the
	 * {@link #countAll(Object...) countAll} method
	 * 
	 * @see CountAllFunction
	 */
	protected final CountAllFunction<CountResult> countAllFunction;

	/**
	 * Function that executes the exists all operation in the
	 * {@link #existsAll(Object...) existsAll} method
	 * 
	 * @see ExistsAllFunction
	 */
	protected final ExistsAllFunction<ExistsResult> existsAllFunction;

	/**
	 * Function that executes the exists by id operation in the
	 * {@link #existsById(Object, Object...) existsById} method
	 * 
	 * @see ExistsByIdFunction
	 */
	protected final ExistsByIdFunction<QueryIdIn, ExistsResult> existsByIdFunction;

	/**
	 * QueryIdIn class used on find by id {@link #findById(Object, Object...)
	 * findById} method and exists by id {@link #existsById(Object, Object...)
	 * existsById} method.
	 */
	protected final Class<QueryIdIn> queryIdInClazz;

	/**
	 * Default constructor, used by the builder to construct this class.
	 * 
	 * @param builder Object in charge to construct this one, can't be null
	 * 
	 * @throws NullPointerException if the builder is null
	 * 
	 * @see QueryFacadeBuilder
	 */
	protected QueryFacade(
			final QueryFacadeBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> builder) {
		LOGGER.atDebug().log("Creating Query Facade with builder {}", builder);
		super(builder);
		this.existsByIdFunction = builder.existsByIdFunction;
		this.findByIdFunction = builder.findByIdFunction;
		this.findAllFunction = builder.findAllFunction;
		this.countAllFunction = builder.countAllFunction;
		this.existsAllFunction = builder.existsAllFunction;
		this.queryIdInClazz = retrieveQueryIdClazz();
		LOGGER.atDebug().log("Query Facade created with builder {}", builder);
	}

	/**
	 * Retrieves the QueryIdIn class using reflection and TypeToken from Guava.
	 * This is used to populate the queryIdInClazz attribute, which is used on find
	 * by id and exists by id methods.
	 * 
	 * @return the QueryIdIn class
	 */
	@SuppressWarnings("unchecked")
	private Class<QueryIdIn> retrieveQueryIdClazz() {
		final var entityTypeToken = new TypeToken<QueryIdIn>(getClass()) {
			private static final long serialVersionUID = 1L;
		};
		final var rawType = (Class<QueryIdIn>) entityTypeToken.getRawType();
		LOGGER.atDebug().log("Class QueryIdIn '{}'", rawType);
		return rawType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MultipleResult findAll(final Object... directives) {
		LOGGER.atDebug().log("Executing default findAll, directives {}", directives);

		final var multipleResult = execute(
				getPreFindAllFunction(), getFindAllFunction(),
				getPosFindAllFunction(), getErrorFindAllFunction(), directives);

		LOGGER.atDebug().log("Default findAll, multipleResult {}, directives {}", directives, multipleResult);
		return multipleResult;
	}

	/**
	 * Gets the find all function {@link #findAllFunction}, provided by the builder.
	 * 
	 * @return The find all function.
	 * 
	 * @see FindAllFunction
	 */
	@NotNull
	protected FindAllFunction<MultipleResult> getFindAllFunction() {
		LOGGER.atDebug().log("Returning findAll function {}", findAllFunction.getName());
		return findAllFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OneResult findById(final QueryIdIn queryIdIn, final Object... directives) {
		LOGGER.atDebug().log("Executing default findById, queryIdIn {}, directives {}", queryIdIn, directives);

		checkNotNull(queryIdIn, NON_NULL_ID_MSG, getEntityClazz().getSimpleName());

		final var oneResult = execute(queryIdIn, getPreFindByIdFunction(),
				getFindByIdFunction(), getPosFindByIdFunction(), getErrorFindByIdFunction(), directives);

		LOGGER.atDebug().log("Default findById executed, oneResult {}, directives {}", oneResult, directives);
		return oneResult;
	}

	/**
	 * Gets the find by id function {@link #findByIdFunction}, provided by the
	 * builder.
	 * 
	 * @return The find by id function.
	 * 
	 * @see FindByIdFunction
	 */
	@NotNull
	protected FindByIdFunction<QueryIdIn, OneResult> getFindByIdFunction() {
		LOGGER.atDebug().log("Returning findById function {}", findByIdFunction.getName());
		return findByIdFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CountResult countAll(final Object... directives) {
		LOGGER.atDebug().log("Executing default countAll, directives {}", directives);

		final var countResult = execute(
				getPreCountAllFunction(), getCountAllFunction(),
				getPosCountAllFunction(), getErrorCountAllFunction(), directives);

		LOGGER.atDebug().log("Default countAll executed, countResult {}, directives {}", countResult, directives);
		return countResult;
	}

	/**
	 * Gets the count all function {@link #countAllFunction}, provided by the
	 * builder.
	 * 
	 * @return The count all function.
	 * 
	 * @see CountAllFunction
	 */
	@NotNull
	protected CountAllFunction<CountResult> getCountAllFunction() {
		LOGGER.atDebug().log("Returning countAll function {}", countAllFunction.getName());
		return countAllFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExistsResult existsAll(final Object... directives) {
		LOGGER.atDebug().log("Executing default existsAll, directives {}", directives);

		final var existsResult = execute(
				getPreExistsAllFunction(), getExistsAllFunction(),
				getPosExistsAllFunction(), getErrorExistsAllFunction(), directives);

		LOGGER.atDebug().log("Default existsAll executed, existsResult {}, directives {}", existsResult, directives);
		return existsResult;
	}

	/**
	 * Gets the exists all function {@link #existsAllFunction}, provided by the
	 * builder.
	 * 
	 * @return The exists all function.
	 * 
	 * @see ExistsAllFunction
	 */
	@NotNull
	protected ExistsAllFunction<ExistsResult> getExistsAllFunction() {
		LOGGER.atDebug().log("Returning existsAll function {}", existsAllFunction.getName());
		return existsAllFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExistsResult existsById(final QueryIdIn queryIdIn, final Object... directives) {
		LOGGER.atDebug().log("Executing default existsById, queryIdIn {}, directives {} ", queryIdIn, directives);

		checkNotNull(queryIdIn, NON_NULL_ID_MSG, getEntityClazz().getSimpleName());

		final var existsResult = execute(
				queryIdIn, getPreExistsByIdFunction(),
				getExistsByIdFunction(), getPosExistsByIdFunction(),
				getErrorExistsByIdFunction(), directives);

		LOGGER.atDebug().log("Default existsById executed, existsResult {}, directives {} ", existsResult, directives);
		return existsResult;
	}

	/**
	 * Gets the exists by id function {@link #existsByIdFunction}, provided by the
	 * builder.
	 * 
	 * @return The exists by id function.
	 * 
	 * @see ExistsByIdFunction
	 */
	@NotNull
	protected ExistsByIdFunction<QueryIdIn, ExistsResult> getExistsByIdFunction() {
		LOGGER.atDebug().log("Returning existsById function {}", existsByIdFunction.getName());
		return existsByIdFunction;
	}

	/**
	 * Gets the query id in class {@link #queryIdInClazz}, used on find by id and
	 * exists by id methods.
	 * It is populated by the constructor using reflection to retrieve the generic
	 * type QueryIdIn.
	 * 
	 * @return The query id in class.
	 */
	@NotNull
	protected Class<QueryIdIn> getQueryIdInClazz() {
		return queryIdInClazz;
	}
}