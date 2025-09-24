package org.reusablecomponents.base.core.application.query.entity.paginationspecification;

import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITIES_BY_SPECIFICATION_PAGEABLE;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITY_BY_SPECIFICATION_SORTED;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.function.TriFunction;
import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.application.base.FacadeBiFunction;
import org.reusablecomponents.base.core.application.base.FacadeTriFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interface responsible for establishing contracts to retrieve objects, common
 * to all projects.
 */
public non-sealed class QueryPaginationSpecificationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification>
		extends BaseFacade<Entity, Id>
		implements
		InterfaceQueryPaginationSpecificationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification> {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryPaginationSpecificationFacade.class);

	protected final TriFunction<Pageable, Specification, Object[], MultiplePagedResult> findByPagAndSpecFunction;

	protected final TriFunction<Sort, Specification, Object[], OneResult> findOneByPagAndSpecFunction;

	/**
	 * Default constructor
	 * 
	 * @param builder Object in charge to construct this one
	 */
	protected QueryPaginationSpecificationFacade(
			final QueryPaginationSpecificationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort, Specification> builder) {
		super(builder);

		this.findByPagAndSpecFunction = builder.findByPagAndSpecFunction;
		this.findOneByPagAndSpecFunction = builder.findOneByPagAndSpecFunction;
	}

	/**
	 * Method executed in {@link #findBySpec(Object, Object, Object...) findBySpec}
	 * method before the {@link #findByPagAndSpecFunction findByPagAndSpecFunction},
	 * use it to configure, change, etc. the input.
	 * 
	 * @param pageable      The query result controll
	 * @param specification The query result filter
	 * @param directives    Objects used to configure the find all operation
	 * 
	 * @return A {@code Entry<Pageable, Specification>} object
	 */
	protected Entry<Pageable, Specification> preFindBy(
			final Pageable pageable,
			final Specification specification,
			final Object... directives) {
		return new SimpleEntry<>(pageable, specification);
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #preFindBy(Object, Object...) preFindBy} method
	 */
	protected List<FacadeBiFunction<Specification>> getFindByPreFunctions() {
		return List.of();
	}

	/**
	 * Method executed in {@link #findBySpec(Object, Object, Object...) findBySpec}
	 * method after the {@link #findByPagAndSpecFunction findByPagAndSpecFunction},
	 * use it to configure, change, etc. the result.
	 * 
	 * @param multiplePagedResult The query result
	 * @param directives          Objects used to configure the find all operation
	 * 
	 * @return A {@code MultiplePagedResult} object
	 */
	protected MultiplePagedResult posFindBy(final MultiplePagedResult multiplePagedResult, final Object... directives) {
		return multiplePagedResult;
	}

	/**
	 * Get functions executed in sequence in the
	 * {@link #posFindBy(Object, Object...) posFindBy} method
	 */
	protected List<FacadeBiFunction<MultiplePagedResult>> getFindByPosFunctions() {
		return List.of();
	}

	/**
	 * Method used to handle {@link #findBySpec(Object, Object, Object...) findBy}
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
	 * Get functions executed in sequence in the
	 * {@link #errorFindBy(Object, Object, Object...) errorFindBy} method
	 */
	protected List<FacadeTriFunction<Exception, Specification>> getFindByErrorFunctions() {
		return List.of();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MultiplePagedResult findBySpec(
			final Pageable pageable,
			final Specification specification,
			final Object... directives) {
		return execute(
				pageable, specification, FIND_ENTITIES_BY_SPECIFICATION_PAGEABLE,
				this::preFindBy, this::posFindBy, findByPagAndSpecFunction::apply,
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
		return execute(
				sort, specification,
				FIND_ENTITY_BY_SPECIFICATION_SORTED,
				this::preFindOneBy,
				this::posFindOneBy,
				findOneByPagAndSpecFunction::apply,
				this::errorFindOneBy, directives);
	}
}
