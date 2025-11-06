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
		extends AbstractQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>
		// Interface QueryFacade
		implements InterfaceQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> {

	private static final String NON_NULL_ID_MSG = "Please pass a non-null %s id";

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryFacade.class);

	/**
	 * Function that executes the find by id operation in the
	 * {@link #findById(Object, Object...) findById} method
	 */
	protected final FindByIdFunction<QueryIdIn, OneResult> findByIdFunction;

	/**
	 * Function that executes the find all operation in the
	 * {@link #findAll(Object...) findAll} method
	 */
	protected final FindAllFunction<MultipleResult> findAllFunction;

	/**
	 * Function that executes the count all operation in the
	 * {@link #countAll(Object...) countAll} method
	 */
	protected final CountAllFunction<CountResult> countAllFunction;

	/**
	 * Function that executes the exists all operation in the
	 * {@link #existsAll(Object...) existsAll} method
	 */
	protected final ExistsAllFunction<ExistsResult> existsAllFunction;

	/**
	 * Function that executes the exists by id operation in the
	 * {@link #existsById(Object, Object...) existsById} method
	 */
	protected final ExistsByIdFunction<QueryIdIn, ExistsResult> existsByIdFunction;

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
	 * {@inheritDoc}
	 */
	@Override
	public MultipleResult findAll(final Object... directives) {
		LOGGER.debug("Executing default findAll, directives {}", directives);

		final var multipleResult = execute(
				getPreFindAllFunction(), getFindAllFunction(),
				getPosFindAllFunction(), getErrorFindAllFunction(), directives);

		LOGGER.debug("Default findAll, multipleResult {}, directives {}", directives, multipleResult);
		return multipleResult;
	}

	@NotNull
	protected FindAllFunction<MultipleResult> getFindAllFunction() {
		LOGGER.debug("Returning findAll function {}", findAllFunction.getName());
		return findAllFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OneResult findById(final QueryIdIn queryIdIn, final Object... directives) {
		LOGGER.debug("Executing default findById, queryIdIn {}, directives {}", queryIdIn, directives);

		checkNotNull(queryIdIn, NON_NULL_ID_MSG, getEntityClazz().getSimpleName());

		final var oneResult = execute(queryIdIn, getPreFindByIdFunction(),
				getFindByIdFunction(), getPosFindByIdFunction(), getErrorFindByIdFunction(), directives);

		LOGGER.debug("Default findById executed, oneResult {}, directives {}", oneResult, directives);
		return oneResult;
	}

	@NotNull
	protected FindByIdFunction<QueryIdIn, OneResult> getFindByIdFunction() {
		LOGGER.debug("Returning findById function {}", findByIdFunction.getName());
		return findByIdFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CountResult countAll(final Object... directives) {
		LOGGER.debug("Executing default countAll, directives {}", directives);

		final var countResult = execute(
				getPreCountAllFunction(), getCountAllFunction(),
				getPosCountAllFunction(), getErrorCountAllFunction(), directives);

		LOGGER.debug("Default countAll executed, countResult {}, directives {}", countResult, directives);
		return countResult;
	}

	@NotNull
	protected CountAllFunction<CountResult> getCountAllFunction() {
		LOGGER.debug("Returning countAll function {}", countAllFunction.getName());
		return countAllFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExistsResult existsAll(final Object... directives) {
		LOGGER.debug("Executing default existsAll, directives {}", directives);

		final var existsResult = execute(
				getPreExistsAllFunction(), getExistsAllFunction(),
				getPosExistsAllFunction(), getErrorExistsAllFunction(), directives);

		LOGGER.debug("Default existsAll executed, existsResult {}, directives {}", existsResult, directives);
		return existsResult;
	}

	@NotNull
	protected ExistsAllFunction<ExistsResult> getExistsAllFunction() {
		LOGGER.debug("Returning existsAll function {}", existsAllFunction.getName());
		return existsAllFunction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExistsResult existsById(final QueryIdIn queryIdIn, final Object... directives) {
		LOGGER.debug("Executing default existsById, queryIdIn {}, directives {} ", queryIdIn, directives);

		checkNotNull(queryIdIn, NON_NULL_ID_MSG, getEntityClazz().getSimpleName());

		final var existsResult = execute(
				queryIdIn, getPreExistsByIdFunction(),
				getExistsByIdFunction(), getPosExistsByIdFunction(),
				getErrorExistsByIdFunction(), directives);

		LOGGER.debug("Default existsById executed, existsResult {}, directives {} ", existsResult, directives);
		return existsResult;
	}

	@NotNull
	protected ExistsByIdFunction<QueryIdIn, ExistsResult> getExistsByIdFunction() {
		LOGGER.debug("Returning existsAll function {}", existsByIdFunction.getName());
		return existsByIdFunction;
	}

	@NotNull
	protected Class<QueryIdIn> getQueryIdInClazz() {
		return queryIdInClazz;
	}
}