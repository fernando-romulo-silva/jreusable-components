package org.reusablecomponents.base.core.application.query.entity.pagination;

import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ALL_ENTITIES_PAGEABLE;
import static org.reusablecomponents.base.core.infra.util.operation.QueryOperation.FIND_ENTITY_SORTED;

import java.util.function.BiFunction;

import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The default <code>InterfaceEntityQueryPaginationFacade</code>'s
 * implementation.
 */
public non-sealed class QueryPaginationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort>
		extends BaseFacade<Entity, Id>
		implements InterfaceQueryPaginationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryPaginationFacade.class);

	protected final BiFunction<Pageable, Object[], MultiplePagedResult> findAllFunction;

	protected final BiFunction<Sort, Object[], OneResult> findFirstFunction;

	/**
	 * Default constructor
	 * 
	 * @param builder Object in charge to construct this one
	 */
	protected QueryPaginationFacade(
			final QueryPaginationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> builder) {
		super(builder);
		this.findAllFunction = builder.findAllFunction;
		this.findFirstFunction = builder.findFirstFunction;
	}

	/**
	 * Method used to change directives object before use it (findAll method).
	 * 
	 * @param pageable   The object to be changed
	 * @param directives The object to be changed
	 * 
	 * @return A new {@code Pageable} object
	 */
	protected Pageable preFindAll(final Pageable pageable, final Object... directives) {
		return pageable;
	}

	/**
	 * Method used to change multiplePagedResult object after use it (findAll
	 * method).
	 * 
	 * @param multiplePagedResult The object to be changed
	 * 
	 * @return A new {@code MultiplePagedResult} object
	 */
	protected MultiplePagedResult posFindAll(
			final MultiplePagedResult multiplePagedResult,
			final Object... directives) {
		return multiplePagedResult;
	}

	/**
	 * Method used to handle pageable find all errors.
	 * 
	 * @param pageable   The object used to pageable find all
	 * @param exception  Exception thrown by pageable find all operation
	 * @param directives Objects used to configure the pageable find all operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorFindAll(
			final Pageable pageable,
			final Exception exception,
			final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SafeVarargs
	public final MultiplePagedResult findAll(final Pageable pageable, final Object... directives) {
		return executeOperation(
				pageable, FIND_ALL_ENTITIES_PAGEABLE, this::preFindAll,
				this::posFindAll, findAllFunction::apply, this::errorFindAll, directives);
	}

	// ---------------------------------------------------------------------------

	/**
	 * 
	 * @param sort
	 * 
	 * @param directives
	 * 
	 * @return
	 */
	protected Sort preFindOne(final Sort sort, final Object... directives) {
		return sort;
	}

	/**
	 * 
	 * @param oneResult
	 * 
	 * @return
	 */
	protected OneResult posFindOne(final OneResult oneResult, final Object... directives) {
		return oneResult;
	}

	/**
	 * Method used to handle pageable find all errors.
	 * 
	 * @param sort       The object used to pageable find all
	 * @param exception  Exception thrown by pageable find all operation
	 * @param directives Objects used to configure the pageable find all operation
	 * 
	 * @return The handled exception
	 */
	protected Exception errorFindOne(
			final Sort sort,
			final Exception exception,
			final Object... directives) {
		return exception;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OneResult findOne(final Sort sort, final Object... directives) {
		return executeOperation(
				sort, FIND_ENTITY_SORTED, this::preFindOne,
				this::posFindOne, findFirstFunction::apply, this::errorFindOne, directives);
	}
}
