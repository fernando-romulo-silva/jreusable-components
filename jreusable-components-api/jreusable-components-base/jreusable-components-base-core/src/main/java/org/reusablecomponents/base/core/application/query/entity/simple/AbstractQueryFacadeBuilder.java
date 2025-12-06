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
 * 
 */
// throw new UnsupportedOperationException("Unimplemented method
// 'getPosFindAllFunction'");
public abstract class AbstractQueryFacadeBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>
        extends BaseFacadeBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQueryFacadeBuilder.class);

    public PreFindByIdFunction<QueryIdIn> preFindByIdFunction;

    public PosFindByIdFunction<OneResult> posFindByIdFunction;

    public ErrorFindByIdFunction<QueryIdIn> errorFindByIdFunction;

    public PreFindAllFunction preFindAllFunction;

    public PosFindAllFunction<MultipleResult> posFindAllFunction;

    public ErrorFindAllFunction errorFindAllFunction;

    public PreCountAllFunction preCountAllFunction;

    public PosCountAllFunction<CountResult> posCountAllFunction;

    public ErrorCountAllFunction errorCountAllFunction;

    public PreExistsAllFunction preExistsAllFunction;

    public PosExistsAllFunction<ExistsResult> posExistsAllFunction;

    public ErrorExistsAllFunction errorExistsAllFunction;

    public PreExistsByIdFunction<QueryIdIn> preExistsByIdFunction;

    public PosExistsByIdFunction<ExistsResult> posExistsByIdFunction;

    public ErrorExistsByIdFunction<QueryIdIn> errorExistsByIdFunction;

    protected AbstractQueryFacadeBuilder(
            Consumer<? extends AbstractQueryFacadeBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult>> function) {
        LOGGER.debug("Constructing AbstractQueryFacadeBuilder");
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

        LOGGER.debug("AbstractQueryFacadeBuilder constructed");
    }

    private PreExistsByIdFunction<QueryIdIn> getPreExistsByIdFunction() {
        return nonNull(preExistsByIdFunction)
                ? preExistsByIdFunction
                : (queryIdIn, directives) -> {
                    LOGGER.debug("Default preExistsById, queryIdIn {}, directives {} ", queryIdIn, directives);
                    return queryIdIn;
                };
    }

    private ErrorExistsByIdFunction<QueryIdIn> getErrorExistsByIdFunction() {
        return nonNull(errorExistsByIdFunction)
                ? errorExistsByIdFunction
                : (exception, queryIdIn, directives) -> {
                    LOGGER.debug("Default errorExistsById, queryIdIn {}, exception {}, directives {}",
                            queryIdIn, exception, directives);
                    return exception;
                };
    }

    private PosExistsByIdFunction<ExistsResult> getPosExistsByIdFunction() {
        return nonNull(posExistsByIdFunction)
                ? posExistsByIdFunction
                : (existsResult, directives) -> {
                    LOGGER.debug("Default posExistsById, existsResult {}, directives {} ", existsResult, directives);
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
                    LOGGER.debug("Default preFindAll, directives {}", directives);
                    return directives;
                };
    }

    private PosFindAllFunction<MultipleResult> getPosFindAllFunction() {
        return nonNull(posFindAllFunction)
                ? posFindAllFunction
                : (multipleResult, directives) -> {
                    LOGGER.debug("Default posFindAll, multipleResult {}, directives {}", multipleResult, directives);
                    return multipleResult;
                };

    }

    private ErrorFindAllFunction getErrorFindAllFunction() {
        return nonNull(errorFindAllFunction)
                ? errorFindAllFunction
                : (exception, directives) -> {
                    LOGGER.debug("Executing default errorFindAll, exception {}, directives {}", exception, directives);
                    return exception;
                };
    }

    private PreFindByIdFunction<QueryIdIn> getPreFindByIdFunction() {
        return nonNull(preFindByIdFunction)
                ? preFindByIdFunction
                : (queryIdIn, directives) -> {
                    LOGGER.debug("Default preFindBy, queryIdIn {}, directives {}", queryIdIn, directives);
                    return queryIdIn;
                };
    }

    private PosFindByIdFunction<OneResult> getPosFindByIdFunction() {
        return nonNull(posFindByIdFunction)
                ? posFindByIdFunction
                : (oneResult, directives) -> {
                    LOGGER.debug("Default posFindBy, oneResult {}, directives {}", oneResult, directives);
                    return oneResult;
                };
    }

    private ErrorFindByIdFunction<QueryIdIn> getErrorFindByIdFunction() {
        return nonNull(errorFindByIdFunction)
                ? errorFindByIdFunction
                : (exception, queryIdIn, directives) -> {
                    LOGGER.debug("Default errorFindById, queryIdIn {}, exception {}, directives {}",
                            queryIdIn, exception, directives);
                    return exception;
                };
    }

    private PreCountAllFunction getPreCountAllFunction() {
        return nonNull(preCountAllFunction)
                ? preCountAllFunction
                : (final Object... directives) -> {
                    LOGGER.debug("Default preCountAll, directives {}", directives);
                    return directives;
                };
    }

    private PosCountAllFunction<CountResult> getPosCountAllFunction() {
        return nonNull(posCountAllFunction)
                ? posCountAllFunction
                : (final CountResult countResult, final Object... directives) -> {
                    LOGGER.debug("Default posCountAll, countResult {}, directives {} ", countResult, directives);
                    return countResult;
                };
    }

    private ErrorCountAllFunction getErrorCountAllFunction() {
        return nonNull(errorCountAllFunction)
                ? errorCountAllFunction
                : (exception, directives) -> {
                    LOGGER.debug("Default errorCountAll, exception {}, directives {}", exception, directives);
                    return exception;
                };
    }

    private PreExistsAllFunction getPreExistsAllFunction() {
        return nonNull(preExistsAllFunction)
                ? preExistsAllFunction
                : directives -> {
                    LOGGER.debug("Default preExistsAll, directives {}", directives);
                    return directives;
                };
    }

    private PosExistsAllFunction<ExistsResult> getPosExistsAllFunction() {
        return nonNull(posExistsAllFunction)
                ? posExistsAllFunction
                : (existsResult, directives) -> {
                    LOGGER.debug("Default posExistsAll, countResult {}, directives {} ", existsResult, directives);
                    return existsResult;
                };

    }

    private ErrorExistsAllFunction getErrorExistsAllFunction() {
        return nonNull(errorExistsAllFunction)
                ? errorExistsAllFunction
                : (exception, directives) -> {
                    LOGGER.debug("Default errorExistsAll, exception {}, directives {}", exception, directives);
                    return exception;
                };
    }
}
