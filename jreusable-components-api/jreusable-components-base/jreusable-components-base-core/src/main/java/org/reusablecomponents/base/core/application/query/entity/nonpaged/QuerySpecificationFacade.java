package org.reusablecomponents.base.core.application.query.entity.nonpaged;

import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.COUNT_BY_SPECIFICATION;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.EXISTS_BY_SPECIFICATION;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITIES_BY_SPECIFICATION;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITY_BY_SPECIFICATION;

import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * The default <code>InterfaceEntityQuerySpecificationFacade</code>'s
 * implementation.
 */
public non-sealed class QuerySpecificationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification>
		extends BaseFacade<Entity, Id>
		implements
		InterfaceQuerySpecificationFacade<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification> {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuerySpecificationFacade.class);

	protected final BiFunction<Specification, Object[], MultipleResult> findBySpecificationFunction;

	protected final BiFunction<Specification, Object[], OneResult> findOneByFunction;

	protected final Function<Specification, ExistsResult> existsBySpecificationFunction;

	protected final Function<Specification, CountResult> countBySpecificationFunction;

	/**
	 * Default constructor
	 * 
	 * @param builder Object in charge to construct this one
	 */
	protected QuerySpecificationFacade(
			@NotNull final QuerySpecificationFacadeBuilder<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification> builder) {

		super(builder);

		this.findBySpecificationFunction = builder.findBySpecificationFunction;
		this.findOneByFunction = builder.findOneByFunction;
		this.existsBySpecificationFunction = builder.existsBySpecificationFunction;
		this.countBySpecificationFunction = builder.countBySpecificationFunction;
	}

	// ---------------------------------------------------------------------------

	/**
	 * Method used to change specification object before use it (FindBy method).
	 * 
	 * @param specification The object to be changed
	 * 
	 * @return A new {@code Specification} object
	 */
	protected Specification preFindBy(final Specification specification) {
		return specification;
	}

	/**
	 * Method used to change multipleResult object after use it (posFindBy method).
	 * 
	 * @param multipleResult The object to be changed
	 * 
	 * @return A new {@code MultipleResult} object
	 */
	protected MultipleResult posFindBy(final MultipleResult multipleResult) {
		return multipleResult;
	}

	/**
	 * Method used to handle find by specification errors.
	 * 
	 * @param specification The object used to find by specification
	 * @param exception     Exception thrown by find specification operation
	 * @param directives    Objects used to configure the find by specification
	 *                      operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorFindBySpecification(
			final Specification specification,
			final Exception exception,
			final Object... directives) {

		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MultipleResult findBySpec(@NotNull final Specification specification,
			@NotNull final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Findind by '{}', session '{}'", specification, session);

		final var finalSpecification = preFindBy(specification);

		LOGGER.debug("Findind by finalSpecification '{}' ", finalSpecification);

		final MultipleResult multipleResult;

		try {
			multipleResult = findBySpecificationFunction.apply(specification, directives);
		} catch (final Exception ex) {

			final var finalException = errorFindBySpecification(finalSpecification, ex, directives);

			LOGGER.debug("Error find by specification '{}', session '{}', error '{}'",
					finalSpecification, session, getRootCauseMessage(finalException));

			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					FIND_ENTITIES_BY_SPECIFICATION,
					getEntityClazz());
		}

		LOGGER.debug("Find by result '{}'", multipleResult);

		final var finalMultipleResult = posFindBy(multipleResult);

		LOGGER.debug("Found by '{}', session '{}'", finalSpecification, session);

		return finalMultipleResult;
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
	protected Specification preFindOneBy(final Specification specification) {
		return specification;
	}

	/**
	 * Method used to change OneResult object after use it (findOneBy method).
	 * 
	 * @param oneResult The object to be changed
	 * 
	 * @return A new {@code OneResult} object
	 */
	protected OneResult posFindOneBy(final OneResult oneResult) {
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
	public OneResult findOneBySpec(@NotNull final Specification specification, @NotNull final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Findind one by '{}', session '{}'", specification, session);

		final var finalSpecification = preFindOneBy(specification);

		LOGGER.debug("Findind by finalSpecification '{}' ", finalSpecification);

		final OneResult oneResult;

		try {
			oneResult = findOneByFunction.apply(finalSpecification, directives);
		} catch (final Exception ex) {

			final var finalException = errorFindOneBySpecification(finalSpecification, ex, directives);

			LOGGER.debug("Error find one by specification '{}', session '{}', error '{}'",
					finalSpecification, session, getRootCauseMessage(finalException));

			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					FIND_ENTITY_BY_SPECIFICATION,
					getEntityClazz());
		}

		LOGGER.debug("Find by result '{}'", oneResult);

		final var finalOneResult = posFindOneBy(oneResult);

		LOGGER.debug("Found one by '{}', session '{}'", finalSpecification, session);

		return finalOneResult;
	}

	// ---------------------------------------------------------------------------

	/**
	 * Method used to change specification object before use it (existsBy method).
	 * 
	 * @param specification The object to be changed
	 * 
	 * @return A new {@code Specification} object
	 */
	protected Specification preExistsBy(final Specification specification) {
		return specification;
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
	 * Method used to handle exists by specification errors.
	 * 
	 * @param specification The object used to exists by specification
	 * @param exception     Exception thrown by exists by specification operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorExistsBySpecification(
			final Specification specification,
			final Exception exception) {

		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final ExistsResult existsBySpec(@NotNull final Specification specification) {

		final var session = securityService.getSession();

		LOGGER.debug("Existing by '{}', session '{}'", specification, session);

		final var finalSpecification = preExistsBy(specification);

		LOGGER.debug("Existing by finalSpecification '{}' ", finalSpecification);

		final ExistsResult existsResult;

		try {
			existsResult = existsBySpecificationFunction.apply(finalSpecification);
		} catch (final Exception ex) {

			final var finalException = errorExistsBySpecification(finalSpecification, ex);

			LOGGER.debug("Error exists by specification '{}', session '{}', error '{}'",
					finalSpecification, session, getRootCauseMessage(finalException));

			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					EXISTS_BY_SPECIFICATION,
					getEntityClazz());
		}

		LOGGER.debug("Exists by result '{}'", existsResult);

		final var finalResult = posExistsBy(existsResult);

		LOGGER.debug("Existed by '{}', session '{}'", finalSpecification, session);

		return finalResult;
	}

	// ---------------------------------------------------------------------------

	/**
	 * Method used to change specification object before use it (countBy method).
	 * 
	 * @param specification The object to be changed
	 * 
	 * @return A new {@code Specification} object
	 */
	protected Specification preCountBy(final Specification specification) {
		return specification;
	}

	/**
	 * Method used to change countResult object after use it (countBy method).
	 * 
	 * @param countResult The object to be changed
	 * 
	 * @return A new {@code CountResult} object
	 */
	protected CountResult posCountBy(final CountResult countResult) {
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
			final Exception exception) {

		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final CountResult countBySpec(@NotNull final Specification specification) {

		final var session = securityService.getSession();

		LOGGER.debug("Counting by '{}', session '{}'", specification, session);

		final var finalSpecification = preCountBy(specification);

		LOGGER.debug("Counting by finalSpecification '{}' ", finalSpecification);

		final CountResult countResult;

		try {
			countResult = countBySpecificationFunction.apply(finalSpecification);
		} catch (final Exception ex) {

			final var finalException = errorCountBySpecification(finalSpecification, ex);

			LOGGER.debug("Error count by specification '{}', session '{}', error '{}'",
					finalSpecification, session, getRootCauseMessage(finalException));

			throw exceptionAdapterService.convert(
					ex,
					i18nService,
					COUNT_BY_SPECIFICATION,
					getEntityClazz());
		}

		LOGGER.debug("Count by result '{}'", countResult);

		final var finalCountResult = posCountBy(countResult);

		LOGGER.debug("Counted by '{}', session '{}'", finalSpecification, session);

		return finalCountResult;
	}
}
