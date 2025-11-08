package org.reusablecomponents.base.core.application.query.entity.simple;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.function.Consumer;

import org.reusablecomponents.base.core.application.query.entity.simple.function.count_all.CountAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_all.ExistsAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_by_id.ExistsByIdFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_all.FindAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_by_id.FindByIdFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The <code>EntityQueryFacade</code> builder's class.
 * 
 * @see QueryFacade
 */
public class QueryFacadeBuilder<Entity extends AbstractEntity<Id>, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>
		extends
		AbstractQueryFacadeBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryFacadeBuilder.class);

	/**
	 * Function that executes exists by id operation
	 */
	public ExistsByIdFunction<QueryIdIn, ExistsResult> existsByIdFunction;

	/**
	 * Function that executes find by id operation
	 */
	public FindByIdFunction<QueryIdIn, OneResult> findByIdFunction;

	/**
	 * Function that executes find all operation
	 */
	public FindAllFunction<MultipleResult> findAllFunction;

	/**
	 * Function that executes count all operation
	 */
	public CountAllFunction<CountResult> countAllFunction;

	/**
	 * Function that executes exist all operation
	 */
	public ExistsAllFunction<ExistsResult> existsAllFunction;

	/**
	 * Default constructor.
	 * 
	 * @param function Consumer function
	 */
	public QueryFacadeBuilder(
			final Consumer<QueryFacadeBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>> function) {
		LOGGER.debug("Constructing QueryFacadeBuilder");
		super(function);

		this.existsByIdFunction = getExistsByIdFunction(existsByIdFunction);
		this.findByIdFunction = getFindByIdFunction(findByIdFunction);
		this.findAllFunction = getFindAllFunction(findAllFunction);
		this.countAllFunction = getCountAllFunction(countAllFunction);
		this.existsAllFunction = getExistsAllFunction(existsAllFunction);

		LOGGER.debug("CommandFacadeBuilder constructed commands, functions {}",
				List.of(existsByIdFunction, findByIdFunction, findAllFunction, countAllFunction,
						existsAllFunction));
	}

	private ExistsAllFunction<ExistsResult> getExistsAllFunction(
			final ExistsAllFunction<ExistsResult> newExistsAllFunction) {
		return nonNull(newExistsAllFunction)
				? newExistsAllFunction
				: directives -> {
					throw new UnsupportedOperationException("Unimplemented function 'existsAllFunction'");
				};
	}

	private CountAllFunction<CountResult> getCountAllFunction(final CountAllFunction<CountResult> newCountAllFunction) {
		return nonNull(newCountAllFunction)
				? newCountAllFunction
				: directives -> {
					throw new UnsupportedOperationException("Unimplemented function 'countAllFunction'");
				};
	}

	private FindAllFunction<MultipleResult> getFindAllFunction(
			final FindAllFunction<MultipleResult> newFindAllFunction) {
		return nonNull(newFindAllFunction)
				? newFindAllFunction
				: directives -> {
					throw new UnsupportedOperationException("Unimplemented function 'findAllFunction'");
				};
	}

	private ExistsByIdFunction<QueryIdIn, ExistsResult> getExistsByIdFunction(
			final ExistsByIdFunction<QueryIdIn, ExistsResult> newExistsByIdFunction) {
		return nonNull(newExistsByIdFunction)
				? newExistsByIdFunction
				: (queryIdIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'existsByIdFunction'");
				};
	}

	private FindByIdFunction<QueryIdIn, OneResult> getFindByIdFunction(
			final FindByIdFunction<QueryIdIn, OneResult> newFindByIdFunction) {
		return nonNull(newFindByIdFunction)
				? newFindByIdFunction
				: (queryIdIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'findByIdFunction'");
				};
	}
}
