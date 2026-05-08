package org.reusablecomponents.base.core.application.query.entity.simple;

import static java.util.Objects.nonNull;

import java.util.List;
import java.util.function.Consumer;

import org.reusablecomponents.base.core.application.query.entity.simple.function.count_all.CountAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_all.ExistsAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_by_id.ExistsByIdFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_all.FindAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_by_id.FindByIdFunction;
import org.reusablecomponents.base.core.domain.InterfaceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The <code>EntityQueryFacade</code> builder's class.
 * 
 * This class is responsible for building the <code>QueryFacade</code> object.
 * 
 * For each function in this class, if it is not set, it will be set with a
 * default function that throws an UnsupportedOperationException with a message
 * that the function is not implemented, example: "Unimplemented function
 * 'functionName'".
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 * 
 * @see AbstractQueryFacadeBuilder
 * @see QueryFacade
 */
public class QueryFacadeBuilder<Entity extends InterfaceEntity<Id>, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>
		extends
		AbstractQueryFacadeBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryFacadeBuilder.class);

	/**
	 * Check {@link QueryFacade#existsByIdFunction QueryFacade.existsByIdFunction}.
	 * 
	 * @see ExistsByIdFunction
	 */
	public ExistsByIdFunction<QueryIdIn, ExistsResult> existsByIdFunction;

	/**
	 * Check {@link QueryFacade#findByIdFunction QueryFacade.findByIdFunction}.
	 * 
	 * @see FindByIdFunction
	 */
	public FindByIdFunction<QueryIdIn, OneResult> findByIdFunction;

	/**
	 * Check {@link QueryFacade#findAllFunction QueryFacade.findAllFunction}.
	 * 
	 * @see FindAllFunction
	 */
	public FindAllFunction<MultipleResult> findAllFunction;

	/**
	 * Check {@link QueryFacade#countAllFunction QueryFacade.countAllFunction}.
	 * 
	 * @see CountAllFunction
	 */
	public CountAllFunction<CountResult> countAllFunction;

	/**
	 * Check {@link QueryFacade#existsAllFunction QueryFacade.existsAllFunction}.
	 * 
	 * @see ExistsAllFunction
	 */
	public ExistsAllFunction<ExistsResult> existsAllFunction;

	/**
	 * Default constructor.
	 * 
	 * @param function Consumer function, can't be null, used to set the builder
	 *                 attributes with a lambda expression, example:
	 * 
	 *                 <pre>
	 *                 new QueryFacadeBuilder&lt;Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult&gt;(
	 *                 		builder -&gt; {
	 *                 			builder.findByIdFunction = (queryIdIn, directives) -&gt; {
	 *                 				// implementation of the find by id operation in the persistence layer.
	 *                 			};
	 *                 			builder.findAllFunction = directives -&gt; {
	 *                 				// implementation of the find all operation in the persistence layer.
	 *                 			};
	 *                 			// set other functions...
	 *                 		});
	 *                 </pre>
	 */
	public QueryFacadeBuilder(
			final Consumer<QueryFacadeBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>> function) {
		LOGGER.atDebug().log("Constructing QueryFacadeBuilder");
		super(function);

		this.existsByIdFunction = getExistsByIdFunction();
		this.findByIdFunction = getFindByIdFunction();
		this.findAllFunction = getFindAllFunction();
		this.countAllFunction = getCountAllFunction();
		this.existsAllFunction = getExistsAllFunction();

		LOGGER.atDebug().log("QueryFacadeBuilder constructed functions {}",
				List.of(existsByIdFunction.getName(),
						findByIdFunction.getName(), findAllFunction.getName(),
						countAllFunction.getName(), existsAllFunction.getName()));
	}

	/**
	 * Gets the exists all function {@link #existsAllFunction}, if it is not set, it
	 * will be set with a function that throws an UnsupportedOperationException when
	 * executed.
	 * 
	 * @return the exists all function
	 * 
	 * @see ExistsAllFunction
	 * @see UnsupportedOperationException
	 */
	protected ExistsAllFunction<ExistsResult> getExistsAllFunction() {
		return nonNull(existsAllFunction)
				? existsAllFunction
				: directives -> {
					throw new UnsupportedOperationException("Unimplemented function 'existsAllFunction'");
				};
	}

	/**
	 * Gets the count all function {@link #countAllFunction}, if it is not set, it
	 * will be set with a function that throws an UnsupportedOperationException when
	 * executed.
	 * 
	 * @return the count all function
	 * 
	 * @see CountAllFunction
	 * @see UnsupportedOperationException
	 */
	protected CountAllFunction<CountResult> getCountAllFunction() {
		return nonNull(countAllFunction)
				? countAllFunction
				: directives -> {
					throw new UnsupportedOperationException("Unimplemented function 'countAllFunction'");
				};
	}

	/**
	 * Gets the find all function {@link #findAllFunction}, if it is not set, it
	 * will be set with a function that throws an UnsupportedOperationException when
	 * executed.
	 * 
	 * @return the find all function
	 * 
	 * @see FindAllFunction
	 * @see UnsupportedOperationException
	 */
	protected FindAllFunction<MultipleResult> getFindAllFunction() {
		return nonNull(findAllFunction)
				? findAllFunction
				: directives -> {
					throw new UnsupportedOperationException("Unimplemented function 'findAllFunction'");
				};
	}

	/**
	 * Gets the exists by id function {@link #existsByIdFunction}, if it is not set,
	 * it will be set with a function that throws an UnsupportedOperationException
	 * when executed.
	 * 
	 * @return the exists by id function
	 * 
	 * @see ExistsByIdFunction
	 * @see UnsupportedOperationException
	 */
	protected ExistsByIdFunction<QueryIdIn, ExistsResult> getExistsByIdFunction() {
		return nonNull(existsByIdFunction)
				? existsByIdFunction
				: (queryIdIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'existsByIdFunction'");
				};
	}

	/**
	 * Gets the find by id function {@link #findByIdFunction}, if it is not set, it
	 * will be set with a function that throws an UnsupportedOperationException when
	 * executed.
	 * 
	 * @return the find by id function
	 * 
	 * @see FindByIdFunction
	 * @see UnsupportedOperationException
	 */
	protected FindByIdFunction<QueryIdIn, OneResult> getFindByIdFunction() {
		return nonNull(findByIdFunction)
				? findByIdFunction
				: (queryIdIn, directives) -> {
					throw new UnsupportedOperationException("Unimplemented function 'findByIdFunction'");
				};
	}
}
