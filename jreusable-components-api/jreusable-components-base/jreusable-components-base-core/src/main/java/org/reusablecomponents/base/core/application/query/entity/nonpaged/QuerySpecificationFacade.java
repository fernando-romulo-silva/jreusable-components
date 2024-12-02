package org.reusablecomponents.base.core.application.query.entity.nonpaged;

import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.COUNT_BY_SPECIFICATION;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.EXISTS_BY_SPECIFICATION;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITIES_BY_SPECIFICATION;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITY_BY_SPECIFICATION;

import java.util.function.BiFunction;

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

	protected final BiFunction<Specification, Object[], ExistsResult> existsBySpecificationFunction;

	protected final BiFunction<Specification, Object[], CountResult> countBySpecificationFunction;

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
	protected Specification preFindBy(final Specification specification, final Object... directives) {
		return specification;
	}

	/**
	 * Method used to change multipleResult object after use it (posFindBy method).
	 * 
	 * @param multipleResult The object to be changed
	 * 
	 * @return A new {@code MultipleResult} object
	 */
	protected MultipleResult posFindBy(final MultipleResult multipleResult, final Object... directives) {
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
	public MultipleResult findBySpec(
			@NotNull final Specification specification,
			final Object... directives) {
		return executeOperation(
				specification, FIND_ENTITIES_BY_SPECIFICATION, this::preFindBy, this::posFindBy,
				findBySpecificationFunction::apply, this::errorFindBySpecification, directives);
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
	protected Specification preFindOneBy(final Specification specification, final Object... directives) {
		return specification;
	}

	/**
	 * Method used to change OneResult object after use it (findOneBy method).
	 * 
	 * @param oneResult The object to be changed
	 * 
	 * @return A new {@code OneResult} object
	 */
	protected OneResult posFindOneBy(final OneResult oneResult, final Object... directives) {
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
	public OneResult findOneBySpec(
			@NotNull final Specification specification,
			final Object... directives) {
		return executeOperation(
				specification, FIND_ENTITY_BY_SPECIFICATION, this::preFindOneBy, this::posFindOneBy,
				findOneByFunction::apply, this::errorFindOneBySpecification, directives);
	}

	// ---------------------------------------------------------------------------

	/**
	 * Method used to change specification object before use it (existsBy method).
	 * 
	 * @param specification The object to be changed
	 * 
	 * @return A new {@code Specification} object
	 */
	protected Specification preExistsBy(final Specification specification, final Object... directives) {
		return specification;
	}

	/**
	 * Method used to change existsResult object after use it (existsBy method).
	 * 
	 * @param existsResult The object to be changed
	 * 
	 * @return A new {@code ExistsResult} object
	 */
	protected ExistsResult posExistsBy(final ExistsResult existsResult, final Object... directives) {
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
			final Exception exception,
			final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final ExistsResult existsBySpec(@NotNull final Specification specification, final Object... directives) {
		return executeOperation(
				specification, EXISTS_BY_SPECIFICATION, this::preExistsBy, this::posExistsBy,
				existsBySpecificationFunction::apply, this::errorExistsBySpecification, directives);
	}

	// ---------------------------------------------------------------------------

	/**
	 * Method used to change specification object before use it (countBy method).
	 * 
	 * @param specification The object to be changed
	 * 
	 * @return A new {@code Specification} object
	 */
	protected Specification preCountBy(final Specification specification, final Object... directives) {
		return specification;
	}

	/**
	 * Method used to change countResult object after use it (countBy method).
	 * 
	 * @param countResult The object to be changed
	 * 
	 * @return A new {@code CountResult} object
	 */
	protected CountResult posCountBy(final CountResult countResult, final Object... directives) {
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
			final Exception exception,
			final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final CountResult countBySpec(@NotNull final Specification specification, final Object... directives) {
		return executeOperation(
				specification, COUNT_BY_SPECIFICATION, this::preCountBy, this::posCountBy,
				countBySpecificationFunction::apply, this::errorCountBySpecification, directives);
	}
}
