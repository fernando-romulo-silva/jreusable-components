package org.reusablecomponents.base.core.application.query.entity.simple;

import org.reusablecomponents.base.core.application.base.BaseFacade;
import org.reusablecomponents.base.core.application.query.entity.simple.function.count_all.ErrorCountAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.count_all.PosCountAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.count_all.PreCountAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_all.ErrorExistsAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_all.PosExistsAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_all.PreExistsAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_by_id.ErrorExistsByIdFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_by_id.PosExistsByIdFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.exists_by_id.PreExistsByIdFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_all.ErrorFindAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_all.PosFindAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_all.PreFindAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_by_id.ErrorFindByIdFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_by_id.PosFindByIdFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_by_id.PreFindByIdFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * Abstract class for query facades, providing common functionality for
 * handling pre, post, and error functions for various query operations.
 * 
 * Used by QueryFacade, but can be extended by the user to create custom facades
 * with additional functionality.
 * 
 * @param <Entity>         The entity type
 * @param <Id>             The entity id type
 * @param <QueryIdIn>      The input id type for the find by id and exists by id
 * @param <OneResult>      The one-result type, like the entity or wrap type
 *                         like Mono<Entity>
 * @param <MultipleResult> The multiple-result type, like List<Entity>,
 *                         Iterable<Entity>, or a wrap type like
 *                         Mono<List<Entity>>
 * @param <CountResult>    The count-result type, like Long, Integer, or a wrap
 *                         type like Mono<Long>
 * @param <ExistsResult>   The exist-result type, like Boolean or a wrap type
 *                         like Mono<Boolean>
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0
 * 
 * @see QueryFacade
 */
public abstract sealed class AbstractQueryFacade< // generics
        // default
        Entity extends AbstractEntity<Id>, Id, // basic
        // input id
        QueryIdIn, //
        // results
        OneResult, MultipleResult, CountResult, ExistsResult>
        // Base Facade
        extends BaseFacade<Entity, Id> permits QueryFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQueryFacade.class);

    /**
     * Function executed in {@link QueryFacade#findById(Object, Object...) findById}
     * method before the {@link QueryFacade#findByIdFunction findByIdFunction}, use
     * it to configure, change, etc. the queryIdIn object.
     * 
     * @see PreFindByIdFunction
     */
    protected final PreFindByIdFunction<QueryIdIn> preFindByIdFunction;

    /**
     * Function executed in {@link QueryFacade#findById(Object, Object...) findById}
     * method after the {@link QueryFacade#findByIdFunction findByIdFunction}, use
     * it to configure, change, etc. the oneResult object.
     * 
     * @see PosFindByIdFunction
     */
    protected final PosFindByIdFunction<OneResult> posFindByIdFunction;

    /**
     * Function executed in {@link QueryFacade#findById(Object, Object...) findById}
     * method to handle {@link QueryFacade#findByIdFunction findByIdFunction}
     * errors.
     * 
     * @see ErrorFindByIdFunction
     */
    protected final ErrorFindByIdFunction<QueryIdIn> errorFindByIdFunction;

    /**
     * Function executed in {@link QueryFacade#findAll(Object...) findAll} method
     * before the {@link QueryFacade#findAllFunction findAllFunction}, use it to
     * execute pre operations for find all operation.
     * 
     * @see PreFindAllFunction
     */
    protected final PreFindAllFunction preFindAllFunction;

    /**
     * Function executed in {@link QueryFacade#findAll(Object...) findAll} method
     * after {@link QueryFacade#findAllFunction findAllFunction}, use it to
     * configure, change, etc. the output.
     * 
     * @see PosFindAllFunction
     */
    protected final PosFindAllFunction<MultipleResult> posFindAllFunction;

    /**
     * Function executed in {@link QueryFacade#findAll(Object...) findAll} method to
     * handle {@link QueryFacade#findAllFunction findAllFunction} errors.
     * 
     * @see ErrorFindAllFunction
     */
    protected final ErrorFindAllFunction errorFindAllFunction;

    /**
     * Function executed in {@link QueryFacade#countAll(Object...) countAll} method
     * before the {@link QueryFacade#countAllFunction countAllFunction}, use it to
     * configure, change, etc. the input.
     * 
     * @see PreCountAllFunction
     */
    protected final PreCountAllFunction preCountAllFunction;

    /**
     * Function executed in {@link QueryFacade#countAll(Object...) countAll} method
     * after {@link QueryFacade#countAllFunction countAllFunction}, use it to
     * configure, change, etc. the output.
     * 
     * @see PosCountAllFunction
     */
    protected final PosCountAllFunction<CountResult> posCountAllFunction;

    /**
     * Function executed in {@link QueryFacade#countAll(Object...) countAll} method
     * to handle {@link QueryFacade#countAllFunction countAllFunction} errors.
     * 
     * @see ErrorCountAllFunction
     */
    protected final ErrorCountAllFunction errorCountAllFunction;

    /**
     * Function executed in {@link QueryFacade#existsAll(Object...) #existsAll}
     * method before the {@link QueryFacade#existsAllFunction existsAllFunction},
     * use it to configure, change, etc. the input.
     * 
     * @see PreExistsAllFunction
     */
    protected final PreExistsAllFunction preExistsAllFunction;

    /**
     * Function executed in {@link QueryFacade#existsAll(Object...) existsAll}
     * method after {@link QueryFacade#existsAllFunction existsAllFunction}, use it
     * to configure, change, etc. the output.
     * 
     * @see PosExistsAllFunction
     */
    protected final PosExistsAllFunction<ExistsResult> posExistsAllFunction;

    /**
     * Function executed in {@link QueryFacade#existsAll(Object...) existsAll}
     * method to handle {@link QueryFacade#existsAllFunction existsAllFunction}
     * errors.
     * 
     * @see ErrorExistsAllFunction
     */
    protected final ErrorExistsAllFunction errorExistsAllFunction;

    /**
     * Function executed in {@link QueryFacade#existsById(Object, Object...)
     * #existsById} method before the {@link QueryFacade#existsByIdFunction
     * existsByIdFunction}, use it to configure, change, etc. the input.
     * 
     * @see PreExistsByIdFunction
     */
    protected final PreExistsByIdFunction<QueryIdIn> preExistsByIdFunction;

    /**
     * Function executed in {@link QueryFacade#existsById(Object, Object...)
     * #existsById} method after the {@link QueryFacade#existsByIdFunction
     * existsByIdFunction}, use it to configure, change, etc. the output.
     * 
     * @see PosExistsByIdFunction
     */
    protected final PosExistsByIdFunction<ExistsResult> posExistsByIdFunction;

    /**
     * Function executed in {@link QueryFacade#existsById(Object, Object...)
     * existsById} method to handle {@link QueryFacade#existsByIdFunction
     * existsByIdFunction} errors.
     * 
     * @see ErrorExistsByIdFunction
     */
    protected final ErrorExistsByIdFunction<QueryIdIn> errorExistsByIdFunction;

    /**
     * Default constructor, used by the builder to construct this class.
     * 
     * @param builder The builder used to construct this class, can't be null
     * 
     * @throws NullPointerException if the builder is null
     * 
     * @see AbstractQueryFacadeBuilder
     */
    protected AbstractQueryFacade(
            @NotNull final AbstractQueryFacadeBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> builder) {
        LOGGER.atDebug().log("Constructing AbstractQueryFacade");
        super(builder);

        this.preFindByIdFunction = builder.preFindByIdFunction;
        this.posFindByIdFunction = builder.posFindByIdFunction;
        this.errorFindByIdFunction = builder.errorFindByIdFunction;

        this.preFindAllFunction = builder.preFindAllFunction;
        this.posFindAllFunction = builder.posFindAllFunction;
        this.errorFindAllFunction = builder.errorFindAllFunction;

        this.preCountAllFunction = builder.preCountAllFunction;
        this.posCountAllFunction = builder.posCountAllFunction;
        this.errorCountAllFunction = builder.errorCountAllFunction;

        this.preExistsAllFunction = builder.preExistsAllFunction;
        this.posExistsAllFunction = builder.posExistsAllFunction;
        this.errorExistsAllFunction = builder.errorExistsAllFunction;

        this.preExistsByIdFunction = builder.preExistsByIdFunction;
        this.posExistsByIdFunction = builder.posExistsByIdFunction;
        this.errorExistsByIdFunction = builder.errorExistsByIdFunction;

        LOGGER.atDebug().log("AbstractQueryFacade constructed");
    }

    @NotNull
    protected PreFindByIdFunction<QueryIdIn> getPreFindByIdFunction() {
        LOGGER.atDebug().log("Returning pre findById function {}", preFindByIdFunction.getName());
        return preFindByIdFunction;
    }

    @NotNull
    protected PosFindByIdFunction<OneResult> getPosFindByIdFunction() {
        LOGGER.atDebug().log("Returning pos findById function {}", posFindByIdFunction.getName());
        return posFindByIdFunction;
    }

    @NotNull
    protected ErrorFindByIdFunction<QueryIdIn> getErrorFindByIdFunction() {
        LOGGER.atDebug().log("Returning error findById function {}", errorFindByIdFunction.getName());
        return errorFindByIdFunction;
    }

    @NotNull
    protected PreFindAllFunction getPreFindAllFunction() {
        LOGGER.atDebug().log("Returning pre findAll function {}", preFindAllFunction.getName());
        return preFindAllFunction;
    }

    @NotNull
    protected PosFindAllFunction<MultipleResult> getPosFindAllFunction() {
        LOGGER.atDebug().log("Returning pos findAll function {}", posFindAllFunction.getName());
        return posFindAllFunction;
    }

    @NotNull
    protected ErrorFindAllFunction getErrorFindAllFunction() {
        LOGGER.atDebug().log("Returning error findById function {}", errorFindAllFunction.getName());
        return errorFindAllFunction;
    }

    @NotNull
    protected PreCountAllFunction getPreCountAllFunction() {
        LOGGER.atDebug().log("Returning pre countAll function {}", preCountAllFunction.getName());
        return preCountAllFunction;
    }

    @NotNull
    protected PosCountAllFunction<CountResult> getPosCountAllFunction() {
        LOGGER.atDebug().log("Returning pos countAll function {}", posCountAllFunction.getName());
        return posCountAllFunction;
    }

    @NotNull
    protected ErrorCountAllFunction getErrorCountAllFunction() {
        LOGGER.atDebug().log("Returning error countAll function {}", errorCountAllFunction.getName());
        return errorCountAllFunction;
    }

    @NotNull
    protected PreExistsAllFunction getPreExistsAllFunction() {
        LOGGER.atDebug().log("Returning pre existsAll function {}", preExistsAllFunction.getName());
        return preExistsAllFunction;
    }

    @NotNull
    protected PosExistsAllFunction<ExistsResult> getPosExistsAllFunction() {
        LOGGER.atDebug().log("Returning pos existsAll function {}", posExistsAllFunction.getName());
        return posExistsAllFunction;
    }

    @NotNull
    protected ErrorExistsAllFunction getErrorExistsAllFunction() {
        LOGGER.atDebug().log("Returning error existsAll function {}", errorExistsAllFunction.getName());
        return errorExistsAllFunction;
    }

    @NotNull
    protected PreExistsByIdFunction<QueryIdIn> getPreExistsByIdFunction() {
        LOGGER.atDebug().log("Returning pre existsById function {}", preExistsByIdFunction.getName());
        return preExistsByIdFunction;
    }

    @NotNull
    protected PosExistsByIdFunction<ExistsResult> getPosExistsByIdFunction() {
        LOGGER.atDebug().log("Returning pos existsById function {}", posExistsByIdFunction.getName());
        return posExistsByIdFunction;
    }

    @NotNull
    protected ErrorExistsByIdFunction<QueryIdIn> getErrorExistsByIdFunction() {
        LOGGER.atDebug().log("Returning error existsById function {}", errorExistsByIdFunction.getName());
        return errorExistsByIdFunction;
    }
}
