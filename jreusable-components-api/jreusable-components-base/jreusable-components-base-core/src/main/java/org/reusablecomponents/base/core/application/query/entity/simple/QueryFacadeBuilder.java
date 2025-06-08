package org.reusablecomponents.base.core.application.query.entity.simple;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.reusablecomponents.base.core.application.base.BaseFacadeBuilder;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The <code>EntityQueryFacade</code> builder's class.
 * 
 * @see QueryFacade
 */
public class QueryFacadeBuilder<Entity extends AbstractEntity<Id>, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>
		extends BaseFacadeBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryFacadeBuilder.class);

	/**
	 * Function that executes exists by id algorithm
	 */
	public BiFunction<QueryIdIn, Object[], ExistsResult> existsByIdFunction;

	/**
	 * Function that executes find by id algorithm
	 */
	public BiFunction<QueryIdIn, Object[], OneResult> findByIdFunction;

	/**
	 * Function that executes find all algorithm
	 */
	public Function<Object[], MultipleResult> findAllFunction;

	/**
	 * Function that executes count all algorithm
	 */
	public Function<Object[], CountResult> countAllFunction;

	/**
	 * Function that executes exist all algorithm
	 */
	public Function<Object[], ExistsResult> existsAllFunction;

	/**
	 * Default constructor.
	 * 
	 * @param function Consumer function
	 */
	public QueryFacadeBuilder(
			final Consumer<QueryFacadeBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>> function) {
		super(function);
		LOGGER.debug("Constructing QueryFacadeBuilder function {} ", function);

		function.accept(this);

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
