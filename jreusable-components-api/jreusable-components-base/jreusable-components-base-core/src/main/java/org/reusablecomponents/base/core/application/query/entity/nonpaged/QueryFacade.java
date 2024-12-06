package org.reusablecomponents.base.core.application.query.entity.nonpaged;

import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.COUNT_ALL;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.EXISTS_ALL;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.EXISTS_BY_ID;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ALL_ENTITIES;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITY_BY_ID;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * The default <code>InterfaceEntityQueryFacade</code>'s implementation.
 */
public non-sealed class QueryFacade<Entity extends AbstractEntity<Id>, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>
		extends BaseFacade<Entity, Id>
		implements
		InterfaceQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryFacade.class);

	protected final BiFunction<QueryIdIn, Object[], ExistsResult> existsByIdFunction;

	protected final BiFunction<QueryIdIn, Object[], OneResult> findByIdFunction;

	protected final Function<Object[], MultipleResult> findAllFunction;

	protected final Function<Object[], CountResult> countAllFunction;

	protected final Function<Object[], ExistsResult> existsAllFunction;

	/**
	 * Default constructor
	 * 
	 * @param builder Object in charge to construct this one
	 */
	protected QueryFacade(
			@NotNull final QueryFacadeBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> builder) {

		super(builder);

		this.existsByIdFunction = builder.existsByIdFunction;
		this.findByIdFunction = builder.findByIdFunction;
		this.findAllFunction = builder.findAllFunction;
		this.countAllFunction = builder.countAllFunction;
		this.existsAllFunction = builder.existsAllFunction;
	}

	// ---------------------------------------------------------------------------
	/**
	 * Method used to change directives object before use it (findAll method).
	 * 
	 * @param directives Objects used to configure the find all query
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

		return directives;
	}

	/**
	 * Method used to change multipleResult object after use it (findAll method).
	 * 
	 * @param multipleResult The object to be changed
	 * @param directives     Objects used to configure the find all query
	 * 
	 * @return A new {@code MultipleResult} object
	 */
	protected MultipleResult posFindAll(final MultipleResult multipleResult, final Object... directives) {
		return multipleResult;
	}

	/**
	 * Method used to handle find all errors.
	 * 
	 * @param exception  Exception thrown by find all operation
	 * @param directives Objects used to configure the find all query
	 * 
	 * @return The handled exception
	 */
	protected Exception errorFindAll(
			final Exception exception,
			final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MultipleResult findAll(final Object... directives) {
		return executeOperation(
				FIND_ALL_ENTITIES, this::preFindAll, this::posFindAll,
				findAllFunction::apply, this::errorFindAll, directives);
	}

	/**
	 * Method used to change queryIdIn and directives (interns) object before use it
	 * (findBy method).
	 * 
	 * @param queryIdIn  The object to be changed
	 * 
	 * @param directives The object to be changed
	 * 
	 * @return A new {@code QueryIdIn} object
	 */
	protected QueryIdIn preFindBy(final QueryIdIn queryIdIn, final Object... directives) {
		return queryIdIn;
	}

	/**
	 * Method used to change oneResult object after use it (findBy method).
	 * 
	 * @param oneResult The object to be changed
	 * 
	 * @return A new {@code QueryIdIn} object
	 */
	protected OneResult posFindBy(final OneResult oneResult, final Object... directives) {
		return oneResult;
	}

	/**
	 * Method used to handle find by id operation.
	 * 
	 * @param queryIdIn  The object used to find by id
	 * @param exception  Exception thrown by save operation
	 * @param directives Objects used to configure the save operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorFindBy(
			final QueryIdIn queryIdIn,
			final Exception exception,
			final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OneResult findById(@NotNull final QueryIdIn queryIdIn, final Object... directives) {
		return executeOperation(
				queryIdIn, FIND_ENTITY_BY_ID, this::preFindBy, this::posFindBy,
				findByIdFunction::apply, this::errorFindBy, directives);
	}

	/**
	 * Method used to change queryIdIn object before use it (existsBy method).
	 * 
	 * @param queryIdIn  The entity id
	 * @param directives Params used to configure the query
	 * 
	 * @return A new {@code QueryIdIn} object
	 */
	protected QueryIdIn preExistsBy(final QueryIdIn queryIdIn, final Object... directives) {
		return queryIdIn;
	}

	/**
	 * Method used to change existsResult object after use it (existsBy method).
	 * 
	 * @param existsResult The object to be changed
	 * @param directives   Params used to configure the query
	 * 
	 * @return A new {@code ExistsResult} object
	 */
	protected ExistsResult posExistsBy(final ExistsResult existsResult, final Object... directives) {
		return existsResult;
	}

	/**
	 * Method used to handle exists by id operation.
	 * 
	 * @param queryIdIn  The object used to exists by id
	 * @param exception  Exception thrown by exists by id operation
	 * @param directives Params used to configure the query
	 * 
	 * @return The handled exception
	 */
	protected Exception errorExistsBy(
			final QueryIdIn queryIdIn,
			final Exception exception,
			final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExistsResult existsById(@NotNull final QueryIdIn queryIdIn, final Object... directives) {
		return executeOperation(
				queryIdIn, EXISTS_BY_ID, this::preExistsBy,
				this::posExistsBy, existsByIdFunction::apply, this::errorExistsBy, directives);
	}

	/**
	 * Method used to change directives object before use it (CountAll method).
	 * 
	 * @param directives Objects used to configure the find all query
	 * 
	 * @return A {@code Object[]} object
	 */
	protected Object[] preCountdAll(final Object... directives) {
		return directives;
	}

	/**
	 * Method used to change countResult object after use it (countAll method).
	 * 
	 * @param countResult The object to be changed
	 * 
	 * @return A new {@code CountResult} object
	 */
	protected CountResult posCountAll(final CountResult countResult, final Object... directives) {
		return countResult;
	}

	/**
	 * Method used to handle count all operation.
	 * 
	 * @param exception Exception thrown by count all operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorCountAll(final Exception exception, final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CountResult countAll(final Object... directives) {
		return executeOperation(
				COUNT_ALL, this::preCountdAll, this::posCountAll,
				countAllFunction::apply, this::errorCountAll, directives);
	}

	/**
	 * Method used to change directives object before use it (CountAll method).
	 * 
	 * @param directives Objects used to configure the find all query
	 * 
	 * @return A {@code Object[]} object
	 */
	protected Object[] preExistsAll(final Object... directives) {
		return directives;
	}

	/**
	 * Method used to change countResult object after use it (countAll method).
	 * 
	 * @param countResult The object to be changed
	 * 
	 * @return A new {@code ExistsResult} object
	 */
	protected ExistsResult posExistsAll(final ExistsResult countResult, final Object... directives) {
		return countResult;
	}

	/**
	 * Method used to handle exists all operation.
	 * 
	 * @param exception Exception thrown by exists all operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorExistsAll(final Exception exception, final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExistsResult existsAll(final Object... directives) {
		return executeOperation(
				EXISTS_ALL, this::preExistsAll, this::posExistsAll,
				existsAllFunction::apply, this::errorExistsAll, directives);
	}
}
