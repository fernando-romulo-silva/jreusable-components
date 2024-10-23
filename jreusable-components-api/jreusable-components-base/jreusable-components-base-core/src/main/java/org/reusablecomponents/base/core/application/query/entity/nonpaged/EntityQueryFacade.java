package org.reusablecomponents.base.core.application.query.entity.nonpaged;

import static java.util.Optional.ofNullable;
import static org.reusablecomponents.base.core.infra.util.Functions.createNullPointerException;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.COUNT_ALL;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.EXISTS_ALL;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.EXISTS_BY_ID;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.FIND_ALL_ENTITIES;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.FIND_ENTITY_BY_ID;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.reusablecomponents.base.core.application.base.EntiyBaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

/**
 * The default <code>InterfaceEntityQueryFacade</code>'s implementation.
 */
public non-sealed class EntityQueryFacade<Entity extends AbstractEntity<Id>, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>
		extends EntiyBaseFacade<Entity, Id>
		implements
		InterfaceEntityQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntityQueryFacade.class);

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
	protected EntityQueryFacade(
			@NotNull final EntityQueryFacadeBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> builder) {

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
			multipleResult = findAllFunction.apply(directives);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					FIND_ALL_ENTITIES,
					getEntityClazz(),
					finalDirectives);
		}

		LOGGER.debug("Find all result '{}'", multipleResult);

		final var finalMultipleResult = posFindAll(multipleResult);

		// TODO
		// findAllPublishEvent(finalMultipleResult, finalDirectives);

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
	 * {@inheritDoc}
	 */
	@Override
	public OneResult findBy(@NotNull final QueryIdIn queryIdIn, @Nullable final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Findind by '{}', directives '{}', and session '{}'", queryIdIn, directives, session);

		final var preQueryIdIn = preFindBy(queryIdIn, directives);

		final var finalQueryIdIn = ofNullable(preQueryIdIn)
				.orElseThrow(createNullPointerException(i18nService, "preQueryIdIn"));

		LOGGER.debug("Findind by finalQueryIdIn '{}'", finalQueryIdIn);

		final OneResult oneResult;

		try {
			oneResult = findByIdFunction.apply(queryIdIn, directives);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					FIND_ENTITY_BY_ID,
					getEntityClazz(),
					finalQueryIdIn);
		}

		LOGGER.debug("Find by id result '{}'", oneResult);

		final var finalOneResult = posFindBy(oneResult);

		// TODO
		// findByPublishEvent(finalQueryIdIn, finalOneResult, directives);

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
	 * {@inheritDoc}
	 */
	@Override
	public ExistsResult existsBy(@NotNull final QueryIdIn queryIdIn) {

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
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					EXISTS_BY_ID,
					getEntityClazz(),
					finalQueryIdIn);
		}

		LOGGER.debug("Existing by id, existsResult '{}'", existsResult);

		final var finalExistsResult = posExistsBy(existsResult);

		// TODO
		// existsByPublishEvent(finalQueryIdIn, finalExistsResult);

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
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					COUNT_ALL,
					getEntityClazz());
		}

		LOGGER.debug("Count all result '{}'", countResult);

		final var finalCountResult = posCountAll(countResult);

		// TODO
		// countAllPublishEvent(finalCountResult);

		LOGGER.debug("Counted all entity '{}', result '{}', and session '{}'",
				getEntityClazz().getSimpleName(),
				finalCountResult,
				session);

		return finalCountResult;
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
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					EXISTS_ALL,
					getEntityClazz());
		}

		LOGGER.debug("Exists all result '{}'", existsResult);

		final var finalExistsResult = posExistsBy(existsResult);

		// TODO
		// existsAllPublishEvent(finalExistsResult);

		LOGGER.debug(
				"Existed all '{}', result '{}', session '{}'",
				getEntityClazz().getSimpleName(),
				finalExistsResult,
				session);

		return finalExistsResult;
	}
}
