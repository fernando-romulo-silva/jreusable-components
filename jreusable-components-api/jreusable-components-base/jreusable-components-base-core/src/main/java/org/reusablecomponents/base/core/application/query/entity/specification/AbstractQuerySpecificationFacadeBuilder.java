package org.reusablecomponents.base.core.application.query.entity.specification;

import static java.util.Objects.nonNull;

import java.util.function.Consumer;

import org.reusablecomponents.base.core.application.base.BaseFacadeBuilder;
import org.reusablecomponents.base.core.application.query.entity.specification.function.count_by_spec.ErrorCountBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.count_by_spec.PosCountBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.count_by_spec.PreCountBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.exists_by_spec.ErrorExistsBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.exists_by_spec.PosExistsBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.exists_by_spec.PreExistsBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_by_spec.ErrorFindBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_by_spec.PosFindBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_by_spec.PreFindBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_one_by_spec.ErrorFindOneBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_one_by_spec.PosFindOneBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_one_by_spec.PreFindOneBySpecificationFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

abstract class AbstractQuerySpecificationFacadeBuilder<Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification>
        extends BaseFacadeBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQuerySpecificationFacadeBuilder.class);

    public PreFindBySpecificationFunction<Specification> preFindBySpecificationFunction;

    public PosFindBySpecificationFunction<MultipleResult> posFindBySpecificationFunction;

    public ErrorFindBySpecificationFunction<Specification> errorFindBySpecificationFunction;

    public PreFindOneBySpecificationFunction<Specification> preFindOneBySpecificationFunction;

    public PosFindOneBySpecificationFunction<OneResult> posFindOneBySpecificationFunction;

    public ErrorFindOneBySpecificationFunction<Specification> errorFindOneBySpecificationFunction;

    public PreCountBySpecificationFunction<Specification> preCountBySpecificationFunction;

    public PosCountBySpecificationFunction<CountResult> posCountBySpecificationFunction;

    public ErrorCountBySpecificationFunction<Specification> errorCountBySpecificationFunction;

    public PreExistsBySpecificationFunction<Specification> preExistsBySpecificationFunction;

    public PosExistsBySpecificationFunction<ExistsResult> posExistsBySpecificationFunction;

    public ErrorExistsBySpecificationFunction<Specification> errorExistsBySpecificationFunction;

    public AbstractQuerySpecificationFacadeBuilder(
            @NotNull final Consumer<? extends AbstractQuerySpecificationFacadeBuilder<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification>> function) {
        super(function);

        this.preFindBySpecificationFunction = getPreFindBySpecificationFunction();
        this.posFindBySpecificationFunction = getPosFindBySpecificationFunction();
        this.errorFindBySpecificationFunction = getErrorFindBySpecificationFunction();

        this.preFindOneBySpecificationFunction = getPreFindOneBySpecificationFunction();
        this.posFindOneBySpecificationFunction = getPosFindOneBySpecificationFunction();
        this.errorFindOneBySpecificationFunction = getErrorFindOneBySpecificationFunction();

        this.preCountBySpecificationFunction = getPreCountBySpecificationFunction();
        this.posCountBySpecificationFunction = getPosCountBySpecificationFunction();
        this.errorCountBySpecificationFunction = getErrorCountBySpecificationFunction();

        this.preExistsBySpecificationFunction = getPreExistsBySpecificationFunction();
        this.posExistsBySpecificationFunction = getPosExistsBySpecificationFunction();
        this.errorExistsBySpecificationFunction = getErrorExistsBySpecificationFunction();
    }

    private PreFindBySpecificationFunction<Specification> getPreFindBySpecificationFunction() {
        return nonNull(preFindBySpecificationFunction)
                ? preFindBySpecificationFunction
                : (specification, directives) -> {
                    LOGGER.debug("Default preFindBySpecificationFunction executed, specification {}, directives {}",
                            specification, directives);
                    return specification;
                };
    }

    private PosFindBySpecificationFunction<MultipleResult> getPosFindBySpecificationFunction() {
        return nonNull(posFindBySpecificationFunction)
                ? posFindBySpecificationFunction
                : (multipleResult, directives) -> {
                    LOGGER.debug(
                            "Default posFindBySpecificationFunction executed, multipleResult {}, directives {}",
                            multipleResult, directives);
                    return multipleResult;
                };
    }

    private ErrorFindBySpecificationFunction<Specification> getErrorFindBySpecificationFunction() {
        return nonNull(errorFindBySpecificationFunction)
                ? errorFindBySpecificationFunction
                : (exception, specification, directives) -> {
                    LOGGER.debug(
                            "Default errorFindBySpecificationFunction executed, specification {}, exception {}, directives {} ",
                            specification, exception, directives);
                    return exception;
                };
    }

    private PreFindOneBySpecificationFunction<Specification> getPreFindOneBySpecificationFunction() {
        return nonNull(preFindOneBySpecificationFunction)
                ? preFindOneBySpecificationFunction
                : (specification, directives) -> {
                    LOGGER.debug(
                            "Default preFindOneBySpecificationFunction executed, specification {}, directives {}",
                            specification, directives);
                    return specification;
                };
    }

    private PosFindOneBySpecificationFunction<OneResult> getPosFindOneBySpecificationFunction() {
        return nonNull(posFindOneBySpecificationFunction)
                ? posFindOneBySpecificationFunction
                : (oneResult, directives) -> {
                    LOGGER.debug("Default posFindOneBySpecificationFunction executed, oneResult {}, directives {}",
                            oneResult, directives);
                    return oneResult;
                };
    }

    private ErrorFindOneBySpecificationFunction<Specification> getErrorFindOneBySpecificationFunction() {
        return nonNull(errorFindOneBySpecificationFunction)
                ? errorFindOneBySpecificationFunction
                : (exception, specification, directives) -> {
                    LOGGER.debug(
                            "Executing default errorFindOneBySpecificationFunction, exception {}, exception {}, directives {}",
                            exception, specification, directives);
                    return exception;
                };
    }

    private PreCountBySpecificationFunction<Specification> getPreCountBySpecificationFunction() {
        return nonNull(preCountBySpecificationFunction)
                ? preCountBySpecificationFunction
                : (specification, directives) -> {
                    LOGGER.debug("Default preCountBySpecificationFunction executed, specification {}, directives {}",
                            specification, directives);
                    return specification;
                };
    }

    private PosCountBySpecificationFunction<CountResult> getPosCountBySpecificationFunction() {
        return nonNull(posCountBySpecificationFunction)
                ? posCountBySpecificationFunction
                : (countResult, directives) -> {
                    LOGGER.debug("Default posCountBySpecificationFunction executed, countResult {}, directives {}",
                            countResult, directives);
                    return countResult;
                };
    }

    private ErrorCountBySpecificationFunction<Specification> getErrorCountBySpecificationFunction() {
        return nonNull(errorCountBySpecificationFunction)
                ? errorCountBySpecificationFunction
                : (exception, specification, directives) -> {
                    LOGGER.debug(
                            "Default errorCountBySpecificationFunction executed, exception {}, specification {}, directives {} ",
                            exception, specification, directives);
                    return exception;
                };
    }

    private PreExistsBySpecificationFunction<Specification> getPreExistsBySpecificationFunction() {
        return nonNull(preExistsBySpecificationFunction)
                ? preExistsBySpecificationFunction
                : (specification, directives) -> {
                    LOGGER.debug("Default preExistsBySpecificationFunction executed, specification {}, directives {}",
                            specification, directives);
                    return specification;
                };
    }

    private PosExistsBySpecificationFunction<ExistsResult> getPosExistsBySpecificationFunction() {
        return nonNull(posExistsBySpecificationFunction)
                ? posExistsBySpecificationFunction
                : (existsResult, directives) -> {
                    LOGGER.debug("Default posExistsBySpecificationFunction executed, existsResult {}, directives {}",
                            existsResult, directives);
                    return existsResult;
                };
    }

    private ErrorExistsBySpecificationFunction<Specification> getErrorExistsBySpecificationFunction() {
        return nonNull(errorExistsBySpecificationFunction)
                ? errorExistsBySpecificationFunction
                : (exception, specification, directives) -> {
                    LOGGER.debug(
                            "Default errorExistsBySpecificationFunction executed, exception {}, specification {}, directives {}",
                            exception, specification, directives);
                    return exception;
                };
    }
}
