package org.reusablecomponents.base.core.application.query.entity.nonpaged;

import static org.reusablecomponents.base.messaging.operation.QueryOperation.COUNT_BY_SPECIFICATION;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.EXISTS_BY_SPECIFICATION;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.FIND_ENTITIES_BY_SPECIFICATION;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.FIND_ENTITY_BY_SPECIFICATION;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.reusablecomponents.base.core.application.base.EntiyBaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Supplier;

import jakarta.validation.constraints.NotNull;

/**
 * The default <code>InterfaceEntityQuerySpecificationFacade</code>'s
 * implementation.
 */
public non-sealed class EntityQuerySpecificationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification>
		extends EntiyBaseFacade<Entity, Id>
		implements
		InterfaceEntityQuerySpecificationFacade<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntityQuerySpecificationFacade.class);

	protected final BiFunction<Specification, Object[], MultipleResult> findBySpecificationFunction;

	protected final BiFunction<Specification, Object[], OneResult> findOneByFunction;

	protected final Function<Specification, ExistsResult> existsBySpecificationFunction;

	protected final Function<Specification, CountResult> countBySpecificationFunction;

	/**
	 * Default constructor
	 * 
	 * @param builder Object in charge to construct this one
	 */
	public EntityQuerySpecificationFacade(
			@NotNull final EntityQuerySpecificationFacadeBuilder<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification> builder) {

		super(builder);

		this.findBySpecificationFunction = builder.findBySpecificationFunction;
		this.findOneByFunction = builder.findOneByFunction;
		this.existsBySpecificationFunction = builder.existsBySpecificationFunction;
		this.countBySpecificationFunction = builder.countBySpecificationFunction;
	}

	// ---------------------------------------------------------------------------

	/**
	 * Create a supplier function (deferred execution) that converts a
	 * {@code Specification} object to String to show in logs, the default is the
	 * <code>java.util.Objects.toString</code>
	 * 
	 * @param specification The object to transform
	 * @return A Supplier object
	 */
	protected Supplier<String> convertSpecificationToPublishDataIn(final Specification specification) {
		return () -> Objects.toString(specification);
	}

	/**
	 * Create a supplier function (deferred execution) that converts a
	 * {@code MultipleResult} object to String to show in logs, the default is the
	 * <code>java.util.Objects.toString</code>
	 * 
	 * @param multipleResult The entity group to transform
	 * @return A Supplier object
	 */
	protected Supplier<String> convertMultipleResultToPublishDataOut(final MultipleResult multipleResult) {
		return () -> Objects.toString(multipleResult);
	}

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
	 * {@inheritDoc}
	 */
	@Override
	public MultipleResult findBy(@NotNull final Specification specification, @NotNull final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Findind by '{}', session '{}'", specification, session);

		final var finalSpecification = preFindBy(specification);

		final MultipleResult result;

		try {
			result = findBySpecificationFunction.apply(specification, directives);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(ex, i18nService);
		}

		final var finalResult = posFindBy(result);

		final var dataIn = convertSpecificationToPublishDataIn(finalSpecification);
		final var dataOut = convertMultipleResultToPublishDataOut(finalResult);
		publishEvent(dataIn, dataOut, FIND_ENTITIES_BY_SPECIFICATION);

		LOGGER.debug("Found by '{}', session '{}'", finalSpecification, session);

		return finalResult;
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
	 * Create a supplier function (deferred execution) that converts a
	 * {@code OneResult} object to String to show in logs, the default is the
	 * <code>java.util.Objects.toString</code>
	 * 
	 * @param oneResult The entity group to transform
	 * @return A Supplier object
	 */
	protected Supplier<String> convertOneResultToPublishDataOut(final OneResult oneResult) {
		return () -> Objects.toString(oneResult);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OneResult findOneBy(@NotNull final Specification specification, @NotNull final Object... directives) {

		final var session = securityService.getSession();

		LOGGER.debug("Findind one by '{}', session '{}'", specification, session);

		final var finalSpecification = preFindOneBy(specification);

		final OneResult result;

		try {
			result = findOneByFunction.apply(finalSpecification, directives);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(ex, i18nService);
		}

		final var finalResult = posFindOneBy(result);

		final var dataIn = convertSpecificationToPublishDataIn(finalSpecification);
		final var dataOut = convertOneResultToPublishDataOut(finalResult);
		publishEvent(dataIn, dataOut, FIND_ENTITY_BY_SPECIFICATION);

		LOGGER.debug("Found one by '{}', session '{}'", finalSpecification, session);

		return finalResult;
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
	 * Create a supplier function (deferred execution) that converts a
	 * {@code ExistsResult} object to String to show in logs, the default is the
	 * <code>java.util.Objects.toString</code>
	 * 
	 * @param resultFinal The entity group to transform
	 * @return A Supplier object
	 */
	protected Supplier<String> convertExistsResultToPublishDataOut(final ExistsResult resultFinal) {
		return () -> Objects.toString(resultFinal);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final ExistsResult existsBy(@NotNull final Specification specification) {

		final var session = securityService.getSession();

		LOGGER.debug("Existing by '{}', session '{}'", specification, session);

		final var finalSpecification = preExistsBy(specification);

		final ExistsResult result;

		try {
			result = existsBySpecificationFunction.apply(finalSpecification);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(ex, i18nService);
		}

		final var finalResult = posExistsBy(result);

		final var dataIn = convertSpecificationToPublishDataIn(finalSpecification);
		final var dataOut = convertExistsResultToPublishDataOut(finalResult);
		publishEvent(dataIn, dataOut, EXISTS_BY_SPECIFICATION);

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
	 * Create a supplier function (deferred execution) that converts a
	 * {@code CountResult} object to String to show in logs, the default is the
	 * <code>java.util.Objects.toString</code>
	 * 
	 * @param countResult The entity to transform
	 * @return A Supplier object
	 */
	protected String convertCountResultToPublishDataOut(final CountResult countResult) {
		return Objects.toString(countResult);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final CountResult count(@NotNull final Specification specification) {

		final var session = securityService.getSession();

		LOGGER.debug("Counting by '{}', session '{}'", specification, session);

		final var finalSpecification = preCountBy(specification);

		final CountResult result;

		try {
			result = countBySpecificationFunction.apply(finalSpecification);
		} catch (final Exception ex) {
			throw exceptionAdapterService.convert(ex, i18nService);
		}

		final var finalResult = posCountBy(result);

		final var dataIn = convertSpecificationToPublishDataIn(finalSpecification);
		final var dataOut = convertCountResultToPublishDataOut(finalResult);
		publishEvent(dataIn, dataOut, COUNT_BY_SPECIFICATION);

		LOGGER.debug("Counted by '{}', session '{}'", finalSpecification, session);

		return finalResult;
	}
}
