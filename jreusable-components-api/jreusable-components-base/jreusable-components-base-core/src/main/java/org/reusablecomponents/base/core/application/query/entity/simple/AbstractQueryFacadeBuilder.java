package org.reusablecomponents.base.core.application.query.entity.simple;

import static java.util.Objects.nonNull;

import java.util.function.Consumer;

import org.reusablecomponents.base.core.application.base.BaseFacadeBuilder;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The <code>AbstractQueryFacade</code> builder's class.
 * 
 * This class is responsible for building the <code>AbstractQuery</code> object.
 * 
 * For each function in this class, if it is not set, it will be set with a
 * default function that just logs the execution and returns the input
 * parameters, example: "Default function 'functionName', input parameters:
 * ['parameter1']".
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0.0
 * 
 * @see BaseFacadeBuilder
 * @see AbstractQueryFacade
 */
public abstract class AbstractQueryFacadeBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>
        extends BaseFacadeBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQueryFacadeBuilder.class);

    /**
     * Check {@link AbstractQueryFacade#preFindByIdFunction
     * AbstractQueryFacade.preFindByIdFunction}.
     * 
     * @see PreFindByIdFunction
     */
    public PreFindByIdFunction<QueryIdIn> preFindByIdFunction;

    /**
     * Check {@link AbstractQueryFacade#posFindByIdFunction
     * AbstractQueryFacade.posFindByIdFunction}.
     * 
     * @see PosFindByIdFunction
     */
    public PosFindByIdFunction<OneResult> posFindByIdFunction;

    /**
     * Check {@link AbstractQueryFacade#errorFindByIdFunction
     * AbstractQueryFacade.errorFindByIdFunction}.
     * 
     * @see ErrorFindByIdFunction
     */
    public ErrorFindByIdFunction<QueryIdIn> errorFindByIdFunction;

    /**
     * Check {@link AbstractQueryFacade#preFindAllFunction
     * AbstractQueryFacade.preFindAllFunction}.
     * 
     * @see PreFindAllFunction
     */
    public PreFindAllFunction preFindAllFunction;

    /**
     * Check {@link AbstractQueryFacade#posFindAllFunction
     * AbstractQueryFacade.posFindAllFunction}.
     * 
     * @see PosFindAllFunction
     */
    public PosFindAllFunction<MultipleResult> posFindAllFunction;

    /**
     * Check {@link AbstractQueryFacade#errorFindAllFunction
     * AbstractQueryFacade.errorFindAllFunction}.
     * 
     * @see ErrorFindAllFunction
     */
    public ErrorFindAllFunction errorFindAllFunction;

    /**
     * Check {@link AbstractQueryFacade#preCountAllFunction
     * AbstractQueryFacade.preCountAllFunction}.
     * 
     * @see PreCountAllFunction
     */
    public PreCountAllFunction preCountAllFunction;

    /**
     * Check {@link AbstractQueryFacade#posCountAllFunction
     * AbstractQueryFacade.posCountAllFunction}.
     * 
     * @see PosCountAllFunction
     */
    public PosCountAllFunction<CountResult> posCountAllFunction;

    /**
     * Check {@link AbstractQueryFacade#errorCountAllFunction
     * AbstractQueryFacade.errorCountAllFunction}.
     * 
     * @see ErrorCountAllFunction
     */
    public ErrorCountAllFunction errorCountAllFunction;

    /**
     * Check {@link AbstractQueryFacade#preExistsAllFunction
     * AbstractQueryFacade.preExistsAllFunction}.
     * 
     * @see PreExistsAllFunction
     */
    public PreExistsAllFunction preExistsAllFunction;

    /**
     * Check {@link AbstractQueryFacade#posExistsAllFunction
     * AbstractQueryFacade.posExistsAllFunction}.
     * 
     * @see PosExistsAllFunction
     */
    public PosExistsAllFunction<ExistsResult> posExistsAllFunction;

    /**
     * Check {@link AbstractQueryFacade#errorExistsAllFunction
     * AbstractQueryFacade.errorExistsAllFunction}.
     * 
     * @see ErrorExistsAllFunction
     */
    public ErrorExistsAllFunction errorExistsAllFunction;

    /**
     * Check {@link AbstractQueryFacade#preExistsByIdFunction
     * AbstractQueryFacade.preExistsByIdFunction}.
     * 
     * @see PreExistsByIdFunction
     */
    public PreExistsByIdFunction<QueryIdIn> preExistsByIdFunction;

    /**
     * Check {@link AbstractQueryFacade#posExistsByIdFunction
     * AbstractQueryFacade.posExistsByIdFunction}.
     * 
     * @see PosExistsByIdFunction
     */
    public PosExistsByIdFunction<ExistsResult> posExistsByIdFunction;

    /**
     * Check {@link AbstractQueryFacade#errorExistsByIdFunction
     * AbstractQueryFacade.errorExistsByIdFunction}.
     * 
     * @see ErrorExistsByIdFunction
     */
    public ErrorExistsByIdFunction<QueryIdIn> errorExistsByIdFunction;

    /**
     * Constructor for AbstractQueryFacadeBuilder
     * 
     * @param function A consumer function to initialize the builder, can't be null.
     */
    protected AbstractQueryFacadeBuilder(
            Consumer<? extends AbstractQueryFacadeBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>> function) {
        LOGGER.atDebug().log("Constructing AbstractQueryFacadeBuilder");
        super(function);

        this.preFindByIdFunction = getPreFindByIdFunction();
        this.posFindByIdFunction = getPosFindByIdFunction();
        this.errorFindByIdFunction = getErrorFindByIdFunction();

        this.preFindAllFunction = getPreFindAllFunction();
        this.posFindAllFunction = getPosFindAllFunction();
        this.errorFindAllFunction = getErrorFindAllFunction();

        this.preCountAllFunction = getPreCountAllFunction();
        this.posCountAllFunction = getPosCountAllFunction();
        this.errorCountAllFunction = getErrorCountAllFunction();

        this.preExistsAllFunction = getPreExistsAllFunction();
        this.posExistsAllFunction = getPosExistsAllFunction();
        this.errorExistsAllFunction = getErrorExistsAllFunction();

        this.preExistsByIdFunction = getPreExistsByIdFunction();
        this.posExistsByIdFunction = getPosExistsByIdFunction();
        this.errorExistsByIdFunction = getErrorExistsByIdFunction();

        LOGGER.atDebug().log("AbstractQueryFacadeBuilder constructed");
    }

    /**
     * Gets the pre exists by id function {@link #preExistsByIdFunction}, if it is
     * not set, it will be set with a default function that logs the execution.
     * 
     * @return The pre exists by id function.
     * @see PreExistsByIdFunction
     */
    protected PreExistsByIdFunction<QueryIdIn> getPreExistsByIdFunction() {
        return nonNull(preExistsByIdFunction)
                ? preExistsByIdFunction
                : (queryIdIn, directives) -> {
                    LOGGER.atDebug().log("Default preExistsById, queryIdIn {}, directives {} ", queryIdIn, directives);
                    return queryIdIn;
                };
    }

    /**
     * Gets the error-exists by id function {@link #errorExistsByIdFunction}, if it
     * is not set, it will be set with a default function that logs the execution.
     * 
     * @return The error exists by id function.
     * @see ErrorExistsByIdFunction
     */
    protected ErrorExistsByIdFunction<QueryIdIn> getErrorExistsByIdFunction() {
        return nonNull(errorExistsByIdFunction)
                ? errorExistsByIdFunction
                : (exception, queryIdIn, directives) -> {
                    LOGGER.atDebug().log("Default errorExistsById, queryIdIn {}, exception {}, directives {}",
                            queryIdIn, exception, directives);
                    return exception;
                };
    }

    /**
     * Gets the pos exists by id function {@link #posExistsByIdFunction}, if it is
     * not set, it will be set with a default function that logs the execution.
     * 
     * @return The pos exists by id function.
     * @see PosExistsByIdFunction
     */
    protected PosExistsByIdFunction<ExistsResult> getPosExistsByIdFunction() {
        return nonNull(posExistsByIdFunction)
                ? posExistsByIdFunction
                : (existsResult, directives) -> {
                    LOGGER.atDebug().log("Default posExistsById, existsResult {}, directives {} ", existsResult,
                            directives);
                    return existsResult;
                };
    }

    /**
     * Gets the pre find all function {@link #preFindAllFunction}, if it is not set,
     * it will be set with a default function that logs the execution.
     * 
     * @return The pre find all function.
     * @see PreFindAllFunction
     */
    protected PreFindAllFunction getPreFindAllFunction() {
        return nonNull(preFindAllFunction)
                ? preFindAllFunction
                : directives -> {
                    // final var formatDirectives = Optional.ofNullable(directives)
                    // .map(params -> params.get("format"))
                    // .stream()
                    // .flatMap(Arrays::stream)
                    // .collect(Collectors.toList());
                    // .anyMatch("full"::equalsIgnoreCase);
                    LOGGER.atDebug().log("Default preFindAll, directives {}", directives);
                    return directives;
                };
    }

    /**
     * Gets the pos find all function {@link #posFindAllFunction}, if it is not set,
     * it will be set with a default function that logs the execution.
     * 
     * @return The pos find all function.
     * @see PosFindAllFunction
     */
    protected PosFindAllFunction<MultipleResult> getPosFindAllFunction() {
        return nonNull(posFindAllFunction)
                ? posFindAllFunction
                : (multipleResult, directives) -> {
                    LOGGER.atDebug().log("Default posFindAll, multipleResult {}, directives {}",
                            multipleResult, directives);
                    return multipleResult;
                };

    }

    /**
     * Gets the error find all function {@link #errorFindAllFunction}, if it is not
     * set, it will be set with a default function that logs the execution.
     * 
     * @return The error find all function.
     * @see ErrorFindAllFunction
     */
    protected ErrorFindAllFunction getErrorFindAllFunction() {
        return nonNull(errorFindAllFunction)
                ? errorFindAllFunction
                : (exception, directives) -> {
                    LOGGER.atDebug().log("Executing default errorFindAll, exception {}, directives {}", exception,
                            directives);
                    return exception;
                };
    }

    /**
     * Gets the pre find by id function {@link #preFindByIdFunction}, if it is not
     * set, it will be set with a default function that logs the execution.
     * 
     * @return The pre find by id function.
     * @see PreFindByIdFunction
     */
    protected PreFindByIdFunction<QueryIdIn> getPreFindByIdFunction() {
        return nonNull(preFindByIdFunction)
                ? preFindByIdFunction
                : (queryIdIn, directives) -> {
                    LOGGER.atDebug().log("Default preFindById, queryIdIn {}, directives {}", queryIdIn, directives);
                    return queryIdIn;
                };
    }

    /**
     * Gets the pos find by id function {@link #posFindByIdFunction}, if it is not
     * set, it will be set with a default function that logs the execution.
     * 
     * @return The pos find by id function.
     * @see PosFindByIdFunction
     */
    protected PosFindByIdFunction<OneResult> getPosFindByIdFunction() {
        return nonNull(posFindByIdFunction)
                ? posFindByIdFunction
                : (oneResult, directives) -> {
                    LOGGER.atDebug().log("Default posFindById, oneResult {}, directives {}", oneResult, directives);
                    return oneResult;
                };
    }

    /**
     * Gets the error find by id function {@link #errorFindByIdFunction}, if it is
     * not set, it will be set with a default function that logs the execution.
     * 
     * @return The error find by id function.
     * @see ErrorFindByIdFunction
     */
    protected ErrorFindByIdFunction<QueryIdIn> getErrorFindByIdFunction() {
        return nonNull(errorFindByIdFunction)
                ? errorFindByIdFunction
                : (exception, queryIdIn, directives) -> {
                    LOGGER.atDebug().log("Default errorFindById, queryIdIn {}, exception {}, directives {}",
                            queryIdIn, exception, directives);
                    return exception;
                };
    }

    /**
     * Gets the pre count all function {@link #preCountAllFunction}, if it is not
     * set, it will be set with a default function that logs the execution.
     * 
     * @return The pre count all function.
     * @see PreCountAllFunction
     */
    protected PreCountAllFunction getPreCountAllFunction() {
        return nonNull(preCountAllFunction)
                ? preCountAllFunction
                : (final Object... directives) -> {
                    LOGGER.atDebug().log("Default preCountAll, directives {}", directives);
                    return directives;
                };
    }

    /**
     * Gets the pos count all function {@link #posCountAllFunction}, if it is not
     * set, it will be set with a default function that logs the execution.
     * 
     * @return The pos count all function.
     * @see PosCountAllFunction
     */
    protected PosCountAllFunction<CountResult> getPosCountAllFunction() {
        return nonNull(posCountAllFunction)
                ? posCountAllFunction
                : (final CountResult countResult, final Object... directives) -> {
                    LOGGER.atDebug().log("Default posCountAll, countResult {}, directives {} ", countResult,
                            directives);
                    return countResult;
                };
    }

    /**
     * Gets the error count all function {@link #errorCountAllFunction}, if it is
     * not set, it will be set with a default function that logs the execution.
     * 
     * @return The error count all function.
     * @see ErrorCountAllFunction
     */
    protected ErrorCountAllFunction getErrorCountAllFunction() {
        return nonNull(errorCountAllFunction)
                ? errorCountAllFunction
                : (exception, directives) -> {
                    LOGGER.atDebug().log("Default errorCountAll, exception {}, directives {}", exception, directives);
                    return exception;
                };
    }

    /**
     * Gets the pre exists all function {@link #preExistsAllFunction}, if it is not
     * set, it will be set with a default function that logs the execution.
     * 
     * @return The pre exists all function.
     * @see PreExistsAllFunction
     */
    protected PreExistsAllFunction getPreExistsAllFunction() {
        return nonNull(preExistsAllFunction)
                ? preExistsAllFunction
                : directives -> {
                    LOGGER.atDebug().log("Default preExistsAll, directives {}", directives);
                    return directives;
                };
    }

    /**
     * Gets the pos exists all function {@link #posExistsAllFunction}, if it is not
     * set, it will be set with a default function that logs the execution.
     * 
     * @return The pos exists all function.
     * @see PosExistsAllFunction
     */
    protected PosExistsAllFunction<ExistsResult> getPosExistsAllFunction() {
        return nonNull(posExistsAllFunction)
                ? posExistsAllFunction
                : (existsResult, directives) -> {
                    LOGGER.atDebug().log("Default posExistsAll, existsResult {}, directives {} ",
                            existsResult, directives);
                    return existsResult;
                };

    }

    /**
     * Gets the error exists all function {@link #errorExistsAllFunction}, if it is
     * not set, it will be set with a default function that logs the execution.
     * 
     * @return The error exists all function.
     * @see ErrorExistsAllFunction
     */
    protected ErrorExistsAllFunction getErrorExistsAllFunction() {
        return nonNull(errorExistsAllFunction)
                ? errorExistsAllFunction
                : (exception, directives) -> {
                    LOGGER.atDebug().log("Default errorExistsAll, exception {}, directives {}", exception, directives);
                    return exception;
                };
    }
}
