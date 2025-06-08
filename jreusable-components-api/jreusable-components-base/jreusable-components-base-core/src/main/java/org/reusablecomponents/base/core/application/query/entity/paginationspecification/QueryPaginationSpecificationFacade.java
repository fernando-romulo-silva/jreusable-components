package org.reusablecomponents.base.core.application.query.entity.paginationspecification;

import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITIES_BY_SPECIFICATION_PAGEABLE;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITY_BY_SPECIFICATION_SORTED;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

import org.apache.commons.lang3.function.TriFunction;
import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interface responsible for establishing contracts to retrieve objects, common
 * operations to all projects.
 */
public non-sealed class QueryPaginationSpecificationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification>
		extends BaseFacade<Entity, Id>
		implements
		InterfaceQueryPaginationSpecificationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification> {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryPaginationSpecificationFacade.class);

	protected final TriFunction<Pageable, Specification, Object[], MultiplePagedResult> findBySpecificationFunction;

	protected final TriFunction<Sort, Specification, Object[], OneResult> findOneByFunctionWithOrder;

	/**
	 * Default constructor
	 * 
	 * @param builder Object in charge to construct this one
	 */
	protected QueryPaginationSpecificationFacade(
			final QueryPaginationSpecificationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification> builder) {
		super(builder);

		this.findBySpecificationFunction = builder.findBySpecificationFunction;
		this.findOneByFunctionWithOrder = builder.findOneByFunctionWithOrder;
	}

	// ---------------------------------------------------------------------------

	/**
	 * 
	 * @param pageable
	 * 
	 * @param specification
	 * 
	 * @return
	 */
	protected Entry<Pageable, Specification> preFindBy(
			final Pageable pageable,
			final Specification specification,
			final Object... directives) {
		return new SimpleEntry<>(pageable, specification);
	}

	/**
	 * 
	 * @param multiplePagedResult
	 * 
	 * @return
	 */
	protected MultiplePagedResult posFindBy(final MultiplePagedResult multiplePagedResult, final Object... directives) {
		return multiplePagedResult;
	}

	/**
	 * Method used to handle {@link #findBy(Object, Object, Object...) findBy}
	 * errors.
	 * 
	 * @param pageable      The object used to find by pageable
	 * @param specification The object used to find by specification
	 * @param exception     Exception thrown by findBy operation
	 * @param directives    Objects used to configure the findBy operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorFindBy(
			final Pageable pageable,
			final Specification specification,
			final Exception exception,
			final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MultiplePagedResult findBy(
			final Pageable pageable,
			final Specification specification,
			final Object... directives) {
		return executeOperation(
				pageable, specification, FIND_ENTITIES_BY_SPECIFICATION_PAGEABLE,
				this::preFindBy, this::posFindBy, findBySpecificationFunction::apply,
				this::errorFindBy, directives);
	}

	// ---------------------------------------------------------------------------

	/**
	 * 
	 * @param specification
	 * 
	 * @param sort
	 * 
	 * @return
	 */
	protected Entry<Sort, Specification> preFindOneBy(
			final Sort sort,
			final Specification specification,
			final Object... directives) {
		return new SimpleEntry<>(sort, specification);
	}

	/**
	 * 
	 * @param oneResult
	 * 
	 * @return
	 */
	protected OneResult posFindOneBy(final OneResult oneResult, final Object... directives) {
		return oneResult;
	}

	protected Exception errorFindOneBy(
			final Sort sort,
			final Specification specification,
			final Exception exception,
			final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OneResult findOneBy(
			final Sort sort,
			final Specification specification,
			final Object... directives) {
		return executeOperation(
				sort, specification,
				FIND_ENTITY_BY_SPECIFICATION_SORTED,
				this::preFindOneBy,
				this::posFindOneBy,
				findOneByFunctionWithOrder::apply,
				this::errorFindOneBy, directives);
	}
}
