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
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

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
     */
    protected final PreFindByIdFunction<QueryIdIn> preFindByIdFunction;

    /**
     * Function executed in {@link QueryFacade#findById(Object, Object...) findById}
     * method after the {@link QueryFacade#findByIdFunction findByIdFunction}, use
     * it to configure, change, etc. the oneResult object.
     */
    protected final PosFindByIdFunction<OneResult> posFindByIdFunction;

    /**
     * Function executed in {@link QueryFacade#findById(Object, Object...) findById}
     * method to handle {@link QueryFacade#findByIdFunction findByIdFunction}
     * errors.
     */
    protected final ErrorFindByIdFunction<QueryIdIn> errorFindByIdFunction;

    /**
     * Function executed in {@link QueryFacade#findAll(Object...) findAll} method
     * before the {@link QueryFacade#findAllFunction findAllFunction}, use it to
     * execute pre operations for find all operation.
     */
    protected final PreFindAllFunction preFindAllFunction;

    /**
     * Function executed in {@link QueryFacade#findAll(Object...) findAll} method
     * after {@link QueryFacade#findAllFunction findAllFunction}, use it to
     * configure, change, etc. the output.
     */
    protected final PosFindAllFunction<MultipleResult> posFindAllFunction;

    /**
     * Function executed in {@link QueryFacade#findAll(Object...) findAll} method to
     * handle {@link QueryFacade#findAllFunction findAllFunction} errors.
     */
    protected final ErrorFindAllFunction errorFindAllFunction;

    /**
     * Function executed in {@link QueryFacade#countAll(Object...) countAll} method
     * before the {@link QueryFacade#countAllFunction countAllFunction}, use it to
     * configure, change, etc. the input.
     */
    protected final PreCountAllFunction preCountAllFunction;

    /**
     * Function executed in {@link QueryFacade#countAll(Object...) countAll} method
     * after {@link QueryFacade#countAllFunction countAllFunction}, use it to
     * configure, change, etc. the output.
     */
    protected final PosCountAllFunction<CountResult> posCountAllFunction;

    /**
     * Function executed in {@link QueryFacade#countAll(Object...) countAll} method
     * to handle {@link QueryFacade#countAllFunction countAllFunction} errors.
     */
    protected final ErrorCountAllFunction errorCountAllFunction;

    /**
     * Function executed in {@link QueryFacade#existsAll(Object...) #existsAll}
     * method before the {@link QueryFacade#existsAllFunction existsAllFunction},
     * use it to configure, change, etc. the input.
     */
    protected final PreExistsAllFunction preExistsAllFunction;

    /**
     * Funcion executed in {@link QueryFacade#existsAll(Object...) existsAll} method
     * after {@link QueryFacade#existsAllFunction existsAllFunction}, use it to
     * configure, change, etc. the output.
     */
    protected final PosExistsAllFunction<ExistsResult> posExistsAllFunction;

    /**
     * Method executed in {@link QueryFacade#existsAll(Object...) existsAll} method
     * to handle link QueryFacade#existsAllFunction existsAllFunction} errors.
     */
    protected final ErrorExistsAllFunction errorExistsAllFunction;

    protected final PreExistsByIdFunction<QueryIdIn> preExistsByIdFunction;

    protected final PosExistsByIdFunction<ExistsResult> posExistsByIdFunction;

    protected final ErrorExistsByIdFunction<QueryIdIn> errorExistsByIdFunction;

    protected AbstractQueryFacade(
            @NotNull final AbstractQueryFacadeBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> builder) {
        LOGGER.debug("Constructing AbstractQueryFacade");
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

        this.preExistsByIdFunction = this::preExistsById;
        this.posExistsByIdFunction = this::posExistsById;
        this.errorExistsByIdFunction = this::errorExistsById;

        LOGGER.debug("AbstractQueryFacade constructed");
    }

    /**
     * Method executed in {@link #existsById(Object, Object...) #existsById} method
     * before the {@link #existsByIdFunction existsByIdFunction}, use it to
     * configure, change, etc. the input.
     * 
     * This method execute {@link #existsByIdPreFunctions existsByIdPreFunctions} in
     * sequence
     * 
     * @param queryIdIn  The object id you want to use to retrieve on the
     *                   persistence mechanism
     * @param directives Objects used to configure the findBy operation
     * 
     * @return A new {@code QueryIdIn} object
     */
    protected QueryIdIn preExistsById(final QueryIdIn queryIdIn, final Object... directives) {
        LOGGER.debug("Default preExistsById, queryIdIn {}, directives {} ", queryIdIn, directives);
        return queryIdIn;
    }

    /**
     * Method executed in {@link #existsById(Object, Object...) #existsById} method
     * after the {@link #existsByIdFunction existsByIdFunction}, use it to
     * configure, change, etc. the output.
     * 
     * @param existsResult The result of existsByIdFunction
     * @param directives   Objects used to configure the findById operation
     * 
     * @return A new {@code ExistsResult} object
     */
    protected ExistsResult posExistsById(final ExistsResult existsResult, final Object... directives) {
        LOGGER.debug("Default posExistsById, existsResult {}, directives {} ", existsResult, directives);
        return existsResult;
    }

    /**
     * Method executed in {@link #existsById(Object, Object...) existsById} method
     * to handle {@link #existsByIdFunction existsByIdFunction} errors. <br />
     * 
     * This method execute {@link #existsByIdErrorFunctions
     * existsByIdErrorFunctions} in sequence
     * 
     * @param exception  Exception thrown by existsByIdFunction
     * @param queryIdIn  The object you tried to use on query by id operation
     * @param directives Objects used to configure the save operation
     * 
     * @return The handled exception
     */
    protected BaseException errorExistsById(
            final BaseException exception,
            final QueryIdIn queryIdIn,
            final Object... directives) {
        LOGGER.debug("Default errorExistsById, queryIdIn {}, exception {}, directives {}",
                queryIdIn, exception, directives);
        return exception;
    }

    @NotNull
    protected PreFindByIdFunction<QueryIdIn> getPreFindByIdFunction() {
        LOGGER.debug("Returning pre findById function {}", preFindByIdFunction.getName());
        return preFindByIdFunction;
    }

    @NotNull
    protected PosFindByIdFunction<OneResult> getPosFindByIdFunction() {
        LOGGER.debug("Returning pos findById function {}", posFindByIdFunction.getName());
        return posFindByIdFunction;
    }

    @NotNull
    protected ErrorFindByIdFunction<QueryIdIn> getErrorFindByIdFunction() {
        LOGGER.debug("Returning error findById function {}", errorFindByIdFunction.getName());
        return errorFindByIdFunction;
    }

    @NotNull
    protected PreFindAllFunction getPreFindAllFunction() {
        LOGGER.debug("Returning pre findAll function {}", preFindAllFunction.getName());
        return preFindAllFunction;
    }

    @NotNull
    protected PosFindAllFunction<MultipleResult> getPosFindAllFunction() {
        LOGGER.debug("Returning pos findAll function {}", posFindAllFunction.getName());
        return posFindAllFunction;
    }

    @NotNull
    protected ErrorFindAllFunction getErrorFindAllFunction() {
        LOGGER.debug("Returning error findById function {}", errorFindAllFunction.getName());
        return errorFindAllFunction;
    }

    @NotNull
    protected PreCountAllFunction getPreCountAllFunction() {
        LOGGER.debug("Returning pre countAll function {}", preCountAllFunction.getName());
        return preCountAllFunction;
    }

    @NotNull
    protected PosCountAllFunction<CountResult> getPosCountAllFunction() {
        LOGGER.debug("Returning pos countAll function {}", posCountAllFunction.getName());
        return posCountAllFunction;
    }

    @NotNull
    protected ErrorCountAllFunction getErrorCountAllFunction() {
        LOGGER.debug("Returning error countAll function {}", errorCountAllFunction.getName());
        return errorCountAllFunction;
    }

    @NotNull
    protected PreExistsAllFunction getPreExistsAllFunction() {
        LOGGER.debug("Returning pre existsAll function {}", preExistsAllFunction.getName());
        return preExistsAllFunction;
    }

    @NotNull
    protected PosExistsAllFunction<ExistsResult> getPosExistsAllFunction() {
        LOGGER.debug("Returning pos existsAll function {}", posExistsAllFunction.getName());
        return posExistsAllFunction;
    }

    @NotNull
    protected ErrorExistsAllFunction getErrorExistsAllFunction() {
        LOGGER.debug("Returning error existsAll function {}", errorExistsAllFunction.getName());
        return errorExistsAllFunction;
    }

    @NotNull
    protected PreExistsByIdFunction<QueryIdIn> getPreExistsByIdFunction() {
        LOGGER.debug("Returning pre existsById function {}", preExistsByIdFunction.getName());
        return preExistsByIdFunction;
    }

    @NotNull
    protected PosExistsByIdFunction<ExistsResult> getPosExistsByIdFunction() {
        LOGGER.debug("Returning pos existsById function {}", posExistsByIdFunction.getName());
        return posExistsByIdFunction;
    }

    @NotNull
    protected ErrorExistsByIdFunction<QueryIdIn> getErrorExistsByIdFunction() {
        LOGGER.debug("Returning error existsById function {}", errorExistsByIdFunction.getName());
        return errorExistsByIdFunction;
    }

}
