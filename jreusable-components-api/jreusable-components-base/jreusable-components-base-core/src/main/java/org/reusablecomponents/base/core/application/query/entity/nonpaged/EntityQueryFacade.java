package org.reusablecomponents.base.core.application.query.entity.nonpaged;

import static org.reusablecomponents.base.messaging.operation.QueryOperation.COUNT_ALL;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.EXISTS_ALL;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.EXISTS_BY_ID;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.FIND_ALL_ENTITIES;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.FIND_ENTITY_BY_ID;
import static java.util.Optional.ofNullable;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;
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
	 * Create a supplier function (deferred execution) that converts a
	 * {@code MultipleResult} object to String to show in logs, the default is the
	 * <code>java.util.Objects.toString</code>
	 * 
	 * @param multipleResult The entity to transform
	 * @return A Supplier object
	 */
	protected Supplier<String> convertMultipleResultToPublishDataOut(final MultipleResult multipleResult) {
		return () -> Objects.toString(multipleResult);
	}

	/**
	 * Create a supplier function (deferred execution) that converts a
	 * {@code OneResult} object to String to show in logs, the default is the
	 * <code>java.util.Objects.toString</code>
	 * 
	 * @param oneResult The entity to transform
	 * @return A Supplier object
	 */
	protected Supplier<String> convertOneResultToPublishDataOut(final OneResult oneResult) {
		return () -> Objects.toString(oneResult);
	}

	/**
	 * Create a supplier function (deferred execution) that converts a
	 * {@code CountResult} object to String to show in logs, the default is the
	 * <code>java.util.Objects.toString</code>
	 * 
	 * @param countResult The entity to transform
	 * @return A Supplier object
	 */
	protected Supplier<String> convertCountResultToPublishDataOut(final CountResult countResult) {
		return () -> Objects.toString(countResult);
	}

	/**
	 * Create a supplier function (deferred execution) that converts a
	 * {@code QueryIdIn} object to String to show in logs, the default is the
	 * <code>java.util.Objects.toString</code>
	 * 
	 * @param queryIdIn The entity to transform
	 * @return A Supplier object
	 */
	protected Supplier<String> convertQueryIdInToPublishDataIn(final QueryIdIn queryIdIn) {
		return () -> Objects.toString(queryIdIn);
	}

	/**
	 * Create a supplier function (deferred execution) that converts a
	 * {@code Object...} object to String to show in logs, the default is the
	 * <code>java.util.Objects.toString</code>
	 * 
	 * @param directives The entity to transform
	 * @return A Supplier object
	 */
	protected Supplier<String> convertDirectivesToPublishDataIn(final Object... directives) {
		return () -> Objects.toString(directives);
	}

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

		LOGGER.debug("Findind all '{}', session '{}'", getEntityClazz().getSimpleName(), session);

		final var finalDirectives = preFindAll(directives);

		final MultipleResult result;

		try {
			result = findAllFunction.apply(directives);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(
					ex,
					i18nService, FIND_ALL_ENTITIES,
					getEntityClazz(),
					finalDirectives);
		}

		final var finalResult = posFindAll(result);

		final var dataIn = convertDirectivesToPublishDataIn(finalDirectives);
		final var dataOut = convertMultipleResultToPublishDataOut(finalResult);
		publishEvent(dataIn, dataOut, FIND_ALL_ENTITIES);

		LOGGER.debug("Found all '{}', session '{}'", getEntityClazz().getSimpleName(), session);

		return finalResult;
	}

	/**
	 * Method used to change queryIdIn and directives (interns) object before use it
	 * (findBy method).
	 * 
	 * @param queryIdIn  The object to be changed
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

		LOGGER.debug("Findind by '{}', directives '{}', session '{}'", queryIdIn, directives, session);

		final var preQueryIdIn = preFindBy(queryIdIn, directives);

		final var finalQueryIdIn = ofNullable(preQueryIdIn)
				.orElseThrow(createNullPointerException("preQueryIdIn"));

		LOGGER.debug("Findind by finalQueryIdIn '{}' ", finalQueryIdIn);

		final OneResult result;

		try {
			result = findByIdFunction.apply(queryIdIn, directives);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					FIND_ENTITY_BY_ID,
					getEntityClazz(),
					finalQueryIdIn);
		}

		LOGGER.debug("Find by result '{}'", result);

		final var finalResult = posFindBy(result);

		final var dataIn = convertQueryIdInToPublishDataIn(finalQueryIdIn);
		final var dataOut = convertOneResultToPublishDataOut(finalResult);
		publishEvent(dataIn, dataOut, FIND_ENTITY_BY_ID);

		LOGGER.debug("Found by '{}', result '{}', session '{}'", queryIdIn, finalResult, session);

		return finalResult;
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
	 * Create a supplier function (deferred execution) that converts a
	 * {@code ExistsResult} object to String to show in logs, the default is the
	 * <code>java.util.Objects.toString</code>
	 * 
	 * @param resultFinal The entity to transform
	 * @return A Supplier object
	 */
	protected Supplier<String> convertExistsResultToPublishData(final ExistsResult resultFinal) {
		return () -> Objects.toString(resultFinal);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExistsResult existsBy(@NotNull final QueryIdIn queryIdIn) {

		final var session = securityService.getSession();

		LOGGER.debug("Existing by '{}', session '{}'", queryIdIn, session);

		final var preQueryIdIn = preExistsBy(queryIdIn);

		final var finalQueryIdIn = ofNullable(preQueryIdIn)
				.orElseThrow(createNullPointerException("preQueryIdIn"));

		LOGGER.debug("Existing by finalQueryIdIn '{}' ", finalQueryIdIn);

		final ExistsResult result;

		try {
			result = existsByIdFunction.apply(finalQueryIdIn);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					EXISTS_BY_ID,
					getEntityClazz(),
					finalQueryIdIn);
		}

		final var finalResult = posExistsBy(result);

		LOGGER.debug("Existing by result '{}'", result);

		final var dataIn = convertQueryIdInToPublishDataIn(finalQueryIdIn);
		final var dataOut = convertExistsResultToPublishData(finalResult);
		publishEvent(dataIn, dataOut, EXISTS_BY_ID);

		LOGGER.debug("Existed by '{}', result '{}', session '{}'", finalQueryIdIn, finalResult, session);

		return finalResult;
	}

	protected CountResult posCountAll(final CountResult countResult) {
		return countResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CountResult countAll() {

		final var session = securityService.getSession();

		LOGGER.debug("Counting all '{}', session '{}'", getEntityClazz().getSimpleName(), session);

		final CountResult result;

		try {
			result = countAllFunction.get();
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					COUNT_ALL,
					getEntityClazz());
		}

		final var finalResult = posCountAll(result);

		final var dataOut = convertCountResultToPublishDataOut(finalResult);
		publishEvent(() -> StringUtils.EMPTY, dataOut, COUNT_ALL);

		LOGGER.debug("Counted all '{}', result '{}', session '{}'", getEntityClazz().getSimpleName(), finalResult,
				session);

		return finalResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExistsResult existsAll() {

		final var session = securityService.getSession();

		LOGGER.debug("Existing all '{}', session '{}'", getEntityClazz().getSimpleName(), session);

		final ExistsResult result;

		try {
			result = existsAllFunction.get();
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					EXISTS_ALL,
					getEntityClazz());
		}

		final var finalResult = posExistsBy(result);

		final var dataOut = convertExistsResultToPublishData(finalResult);
		publishEvent(() -> StringUtils.EMPTY, dataOut, EXISTS_ALL);

		LOGGER.debug(
				"Existed all '{}', result '{}', session '{}'",
				getEntityClazz().getSimpleName(),
				finalResult,
				session);

		return finalResult;
	}
}
