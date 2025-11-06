package org.reusablecomponents.base.core.application.query.entity.simple;

import static com.google.common.base.Preconditions.checkNotNull;

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

		checkNotNull(existsByIdFunction, "Please pass a non-null 'existsByIdFunction'");
		checkNotNull(findByIdFunction, "Please pass a non-null 'findByIdFunction'");
		checkNotNull(findAllFunction, "Please pass a non-null 'findAllFunction'");
		checkNotNull(countAllFunction, "Please pass a non-null 'countAllFunction'");
		checkNotNull(existsAllFunction, "Please pass a non-null 'existsAllFunction'");

		LOGGER.debug("CommandFacadeBuilder constructed commands, functions {}",
				List.of(existsByIdFunction, findByIdFunction, findAllFunction, countAllFunction,
						existsAllFunction));
	}
}
