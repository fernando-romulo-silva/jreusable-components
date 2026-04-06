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
 * This class is responsible for building the <code>AbstractQuery</code> object.
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
 */
public abstract class AbstractQueryFacadeBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>
        extends BaseFacadeBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQueryFacadeBuilder.class);

    /**
     * Function that executes pre-find by id operation
     * 
     * @see PreFindByIdFunction
     */
    public PreFindByIdFunction<QueryIdIn> preFindByIdFunction;

    /**
     * Function that executes post-find by id operation
     * 
     * @see PosFindByIdFunction
     */
    public PosFindByIdFunction<OneResult> posFindByIdFunction;

    /**
     * Function that executes error-find by id operation
     * 
     * @see ErrorFindByIdFunction
     */
    public ErrorFindByIdFunction<QueryIdIn> errorFindByIdFunction;

    /**
     * Function that executes pre-find all operation
     * 
     * @see PreFindAllFunction
     */
    public PreFindAllFunction preFindAllFunction;

    /**
     * Function that executes post-find all operation
     * 
     * @see PosFindAllFunction
     */
    public PosFindAllFunction<MultipleResult> posFindAllFunction;

    /**
     * Function that executes error-find all operation
     * 
     * @see ErrorFindAllFunction
     */
    public ErrorFindAllFunction errorFindAllFunction;

    /**
     * Function that executes pre-count all operation
     * 
     * @see PreCountAllFunction
     */
    public PreCountAllFunction preCountAllFunction;

    /**
     * Function that executes post-count all operation
     * 
     * @see PosCountAllFunction
     */
    public PosCountAllFunction<CountResult> posCountAllFunction;

    /**
     * Function that executes error-count all operation
     * 
     * @see ErrorCountAllFunction
     */
    public ErrorCountAllFunction errorCountAllFunction;

    /**
     * Function that executes pre-exists all operation
     * 
     * @see PreExistsAllFunction
     */
    public PreExistsAllFunction preExistsAllFunction;

    /**
     * Function that executes post-exists all operation
     * 
     * @see PosExistsAllFunction
     */
    public PosExistsAllFunction<ExistsResult> posExistsAllFunction;

    /**
     * Function that executes error-exists all operation
     * 
     * @see ErrorExistsAllFunction
     */
    public ErrorExistsAllFunction errorExistsAllFunction;

    /**
     * Function that executes pre-exists by id operation
     * 
     * @see PreExistsByIdFunction
     */
    public PreExistsByIdFunction<QueryIdIn> preExistsByIdFunction;

    /**
     * Function that executes post-exists by id operation
     * 
     * @see PosExistsByIdFunction
     */
    public PosExistsByIdFunction<ExistsResult> posExistsByIdFunction;

    /**
     * Function that executes error-exists by id operation
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

    private PreExistsByIdFunction<QueryIdIn> getPreExistsByIdFunction() {
        return nonNull(preExistsByIdFunction)
                ? preExistsByIdFunction
                : (queryIdIn, directives) -> {
                    LOGGER.atDebug().log("Default preExistsById, queryIdIn {}, directives {} ", queryIdIn, directives);
                    return queryIdIn;
                };
    }

    private ErrorExistsByIdFunction<QueryIdIn> getErrorExistsByIdFunction() {
        return nonNull(errorExistsByIdFunction)
                ? errorExistsByIdFunction
                : (exception, queryIdIn, directives) -> {
                    LOGGER.atDebug().log("Default errorExistsById, queryIdIn {}, exception {}, directives {}",
                            queryIdIn, exception, directives);
                    return exception;
                };
    }

    private PosExistsByIdFunction<ExistsResult> getPosExistsByIdFunction() {
        return nonNull(posExistsByIdFunction)
                ? posExistsByIdFunction
                : (existsResult, directives) -> {
                    LOGGER.atDebug().log("Default posExistsById, existsResult {}, directives {} ", existsResult,
                            directives);
                    return existsResult;
                };
    }

    private PreFindAllFunction getPreFindAllFunction() {
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

    private PosFindAllFunction<MultipleResult> getPosFindAllFunction() {
        return nonNull(posFindAllFunction)
                ? posFindAllFunction
                : (multipleResult, directives) -> {
                    LOGGER.atDebug().log("Default posFindAll, multipleResult {}, directives {}", multipleResult,
                            directives);
                    return multipleResult;
                };

    }

    private ErrorFindAllFunction getErrorFindAllFunction() {
        return nonNull(errorFindAllFunction)
                ? errorFindAllFunction
                : (exception, directives) -> {
                    LOGGER.atDebug().log("Executing default errorFindAll, exception {}, directives {}", exception,
                            directives);
                    return exception;
                };
    }

    private PreFindByIdFunction<QueryIdIn> getPreFindByIdFunction() {
        return nonNull(preFindByIdFunction)
                ? preFindByIdFunction
                : (queryIdIn, directives) -> {
                    LOGGER.atDebug().log("Default preFindBy, queryIdIn {}, directives {}", queryIdIn, directives);
                    return queryIdIn;
                };
    }

    private PosFindByIdFunction<OneResult> getPosFindByIdFunction() {
        return nonNull(posFindByIdFunction)
                ? posFindByIdFunction
                : (oneResult, directives) -> {
                    LOGGER.atDebug().log("Default posFindBy, oneResult {}, directives {}", oneResult, directives);
                    return oneResult;
                };
    }

    private ErrorFindByIdFunction<QueryIdIn> getErrorFindByIdFunction() {
        return nonNull(errorFindByIdFunction)
                ? errorFindByIdFunction
                : (exception, queryIdIn, directives) -> {
                    LOGGER.atDebug().log("Default errorFindById, queryIdIn {}, exception {}, directives {}",
                            queryIdIn, exception, directives);
                    return exception;
                };
    }

    private PreCountAllFunction getPreCountAllFunction() {
        return nonNull(preCountAllFunction)
                ? preCountAllFunction
                : (final Object... directives) -> {
                    LOGGER.atDebug().log("Default preCountAll, directives {}", directives);
                    return directives;
                };
    }

    private PosCountAllFunction<CountResult> getPosCountAllFunction() {
        return nonNull(posCountAllFunction)
                ? posCountAllFunction
                : (final CountResult countResult, final Object... directives) -> {
                    LOGGER.atDebug().log("Default posCountAll, countResult {}, directives {} ", countResult,
                            directives);
                    return countResult;
                };
    }

    private ErrorCountAllFunction getErrorCountAllFunction() {
        return nonNull(errorCountAllFunction)
                ? errorCountAllFunction
                : (exception, directives) -> {
                    LOGGER.atDebug().log("Default errorCountAll, exception {}, directives {}", exception, directives);
                    return exception;
                };
    }

    private PreExistsAllFunction getPreExistsAllFunction() {
        return nonNull(preExistsAllFunction)
                ? preExistsAllFunction
                : directives -> {
                    LOGGER.atDebug().log("Default preExistsAll, directives {}", directives);
                    return directives;
                };
    }

    private PosExistsAllFunction<ExistsResult> getPosExistsAllFunction() {
        return nonNull(posExistsAllFunction)
                ? posExistsAllFunction
                : (existsResult, directives) -> {
                    LOGGER.atDebug().log("Default posExistsAll, countResult {}, directives {} ", existsResult,
                            directives);
                    return existsResult;
                };

    }

    private ErrorExistsAllFunction getErrorExistsAllFunction() {
        return nonNull(errorExistsAllFunction)
                ? errorExistsAllFunction
                : (exception, directives) -> {
                    LOGGER.atDebug().log("Default errorExistsAll, exception {}, directives {}", exception, directives);
                    return exception;
                };
    }
}
