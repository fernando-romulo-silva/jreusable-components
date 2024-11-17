package org.reusablecomponents.base.core.application.query.entity.nonpaged;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;
import static org.reusablecomponents.base.core.infra.util.Functions.createNullPointerException;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.COUNT_ALL;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.EXISTS_ALL;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.EXISTS_BY_ID;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ALL_ENTITIES;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITY_BY_ID;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

/**
 * The default <code>InterfaceEntityQueryFacade</code>'s implementation.
 */
public non-sealed class QueryFacade<Entity extends AbstractEntity<Id>, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>
		extends BaseFacade<Entity, Id>
		implements
		InterfaceQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryFacade.class);

	protected final Function<QueryIdIn, ExistsResult> existsByIdFunction;

	protected final BiFunction<QueryIdIn, Object[], OneResult> findByIdFunction;

	protected final Function<Object[], MultipleResult> findAllFunction;

	protected final Supplier<CountResult> countAllFunction;

	protected final Supplier<ExistsResult> existsAllFunction;

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
	 * @param directives The object to be changed
	 * 
	 * @return A new {@code Object[]} object
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
	 * 
	 * @return A new {@code MultipleResult} object
	 */
	protected MultipleResult posFindAll(final MultipleResult multipleResult) {
		return multipleResult;
	}

	/**
	 * Method used to handle find all errors.
	 * 
	 * @param exception  Exception thrown by find all operation
	 * @param directives Objects used to configure the find all operation
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
	public MultipleResult findAll(@Nullable final Object... directives) {

		final var session = securityService.getSession();
		final var simpleName = getEntityClazz().getSimpleName();

		LOGGER.debug("Findind all '{}' object, session '{}', and directives '{}'", simpleName, session, directives);

		final var finalDirectives = preFindAll(directives);

		LOGGER.debug("Findind all finalDirectives '{}' ", finalDirectives);

		final MultipleResult multipleResult;

		try {
			multipleResult = findAllFunction.apply(finalDirectives);
		} catch (final Exception ex) {

			final var finalException = errorFindAll(ex, finalDirectives);

			LOGGER.debug("Error find all directives '{}', session '{}', error '{}'",
					finalDirectives, session, getRootCauseMessage(finalException));

			throw exceptionAdapterService.convert(
					finalException,
					i18nService,
					FIND_ALL_ENTITIES,
					getEntityClazz(),
					finalDirectives);
		}

		LOGGER.debug("Find all result '{}'", multipleResult);

		final var finalMultipleResult = posFindAll(multipleResult);

		LOGGER.debug("Found all '{}', session '{}'", getEntityClazz().getSimpleName(), session);

		return finalMultipleResult;
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

		// final var formatDirectives = Optional.ofNullable(directives)
		// .map(params -> params.get("format"))
		// .stream()
		// .flatMap(Arrays::stream)
		// .collect(Collectors.toList());
		// .anyMatch("full"::equalsIgnoreCase);

		return queryIdIn;
	}

	/**
	 * Method used to change oneResult object after use it (findBy method).
	 * 
	 * @param oneResult The object to be changed
	 * 
	 * @return A new {@code QueryIdIn} object
	 */
	protected OneResult posFindBy(final OneResult oneResult) {
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
	public OneResult findById(@NotNull final QueryIdIn queryIdIn, @Nullable final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Findind by '{}', directives '{}', and session '{}'", queryIdIn, directives, session);

		final var preQueryIdIn = preFindBy(queryIdIn, directives);

		final var finalQueryIdIn = ofNullable(preQueryIdIn)
				.orElseThrow(createNullPointerException(i18nService, "preQueryIdIn"));

		LOGGER.debug("Findind by finalQueryIdIn '{}'", finalQueryIdIn);

		final OneResult oneResult;

		try {
			oneResult = findByIdFunction.apply(finalQueryIdIn, directives);
		} catch (final Exception ex) {

			final var finalException = errorFindBy(finalQueryIdIn, ex, directives);

			LOGGER.debug("Error find by id '{}', session '{}', error '{}'",
					finalQueryIdIn, session, getRootCauseMessage(finalException));

			throw exceptionAdapterService.convert(
					finalException,
					i18nService,
					FIND_ENTITY_BY_ID,
					getEntityClazz(),
					finalQueryIdIn);
		}

		LOGGER.debug("Find by id result '{}'", oneResult);

		final var finalOneResult = posFindBy(oneResult);

		LOGGER.debug("Found by id '{}', result '{}', session '{}'", finalQueryIdIn, finalOneResult, session);

		return finalOneResult;
	}

	/**
	 * Method used to change queryIdIn object before use it (existsBy method).
	 * 
	 * @param queryIdIn The object to be changed
	 * 
	 * @return A new {@code QueryIdIn} object
	 */
	protected QueryIdIn preExistsBy(final QueryIdIn queryIdIn) {
		return queryIdIn;
	}

	/**
	 * Method used to change existsResult object after use it (existsBy method).
	 * 
	 * @param existsResult The object to be changed
	 * 
	 * @return A new {@code ExistsResult} object
	 */
	protected ExistsResult posExistsBy(final ExistsResult existsResult) {
		return existsResult;
	}

	/**
	 * Method used to handle exists by id operation.
	 * 
	 * @param queryIdIn The object used to exists by id
	 * @param exception Exception thrown by exists by id operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorExistBy(
			final QueryIdIn queryIdIn,
			final Exception exception) {

		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExistsResult existsById(@NotNull final QueryIdIn queryIdIn) {

		final var session = securityService.getSession();

		LOGGER.debug("Existing by id, queryIdIn '{}' and session '{}'", queryIdIn, session);

		final var preQueryIdIn = preExistsBy(queryIdIn);

		final var finalQueryIdIn = ofNullable(preQueryIdIn)
				.orElseThrow(createNullPointerException(i18nService, "preQueryIdIn"));

		LOGGER.debug("Existing by id, finalQueryIdIn '{}' ", finalQueryIdIn);

		final ExistsResult existsResult;

		try {
			existsResult = existsByIdFunction.apply(finalQueryIdIn);
		} catch (final Exception ex) {

			final var finalException = errorExistBy(finalQueryIdIn, ex);

			LOGGER.debug("Error exists by id '{}', session '{}', error '{}'",
					finalQueryIdIn, session, getRootCauseMessage(finalException));

			throw exceptionAdapterService.convert(
					finalException,
					i18nService,
					EXISTS_BY_ID,
					getEntityClazz(),
					finalQueryIdIn);
		}

		LOGGER.debug("Existing by id, existsResult '{}'", existsResult);

		final var finalExistsResult = posExistsBy(existsResult);

		LOGGER.debug("Existed by '{}', result '{}', session '{}'", finalQueryIdIn, finalExistsResult, session);

		return finalExistsResult;
	}

	/**
	 * Method used to change countResult object after use it (countAll method).
	 * 
	 * @param countResult The object to be changed
	 * 
	 * @return A new {@code CountResult} object
	 */
	protected CountResult posCountAll(final CountResult countResult) {
		return countResult;
	}

	/**
	 * Method used to handle count all operation.
	 * 
	 * @param exception Exception thrown by count all operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorCountAll(final Exception exception) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CountResult countAll() {

		final var session = securityService.getSession();

		LOGGER.debug("Counting all, entity '{}' and session '{}'", getEntityClazz().getSimpleName(), session);

		final CountResult countResult;

		try {
			countResult = countAllFunction.get();
		} catch (final Exception ex) {

			final var finalException = errorCountAll(ex);

			LOGGER.debug("Error counting all, entity '{}', session '{}', error '{}'",
					getEntityClazz().getSimpleName(), session, getRootCauseMessage(finalException));

			throw exceptionAdapterService.convert(
					finalException,
					i18nService,
					COUNT_ALL,
					getEntityClazz());
		}

		LOGGER.debug("Count all result '{}'", countResult);

		final var finalCountResult = posCountAll(countResult);

		LOGGER.debug("Counted all entity '{}', result '{}', and session '{}'",
				getEntityClazz().getSimpleName(),
				finalCountResult,
				session);

		return finalCountResult;
	}

	/**
	 * Method used to handle exists all operation.
	 * 
	 * @param exception Exception thrown by exists all operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorExistAll(final Exception exception) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExistsResult existsAll() {

		final var session = securityService.getSession();

		LOGGER.debug("Existing all '{}', session '{}'", getEntityClazz().getSimpleName(), session);

		final ExistsResult existsResult;

		try {
			existsResult = existsAllFunction.get();
		} catch (final Exception ex) {
			final var finalException = errorExistAll(ex);

			LOGGER.debug("Error exists all, entity '{}', session '{}', error '{}'",
					getEntityClazz().getSimpleName(), session, getRootCauseMessage(finalException));

			throw exceptionAdapterService.convert(
					finalException,
					i18nService,
					EXISTS_ALL,
					getEntityClazz());
		}

		LOGGER.debug("Exists all result '{}'", existsResult);

		final var finalExistsResult = posExistsBy(existsResult);

		LOGGER.debug(
				"Existed all '{}', result '{}', session '{}'",
				getEntityClazz().getSimpleName(),
				finalExistsResult,
				session);

		return finalExistsResult;
	}
}
