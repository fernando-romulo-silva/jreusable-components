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

        this.preFindBySpecificationFunction = getPreFindBySpecificationFunction(preFindBySpecificationFunction);
        this.posFindBySpecificationFunction = getPosFindBySpecificationFunction(posFindBySpecificationFunction);
        this.errorFindBySpecificationFunction = getErrorFindBySpecificationFunction(
                errorFindBySpecificationFunction);

        this.preFindOneBySpecificationFunction = getPreFindOneBySpecificationFunction(
                preFindOneBySpecificationFunction);
        this.posFindOneBySpecificationFunction = getPosFindOneBySpecificationFunction(
                posFindOneBySpecificationFunction);
        this.errorFindOneBySpecificationFunction = getErrorFindOneBySpecificationFunction(
                errorFindOneBySpecificationFunction);

        this.preCountBySpecificationFunction = getPreCountBySpecificationFunction(
                preCountBySpecificationFunction);
        this.posCountBySpecificationFunction = getPosCountBySpecificationFunction(
                posCountBySpecificationFunction);
        this.errorCountBySpecificationFunction = getErrorCountBySpecificationFunction(
                errorCountBySpecificationFunction);

        this.preExistsBySpecificationFunction = getPreExistsBySpecificationFunction(
                preExistsBySpecificationFunction);
        this.posExistsBySpecificationFunction = getPosExistsBySpecificationFunction(
                posExistsBySpecificationFunction);
        this.errorExistsBySpecificationFunction = getErrorExistsBySpecificationFunction(
                errorExistsBySpecificationFunction);
    }

    private PreFindBySpecificationFunction<Specification> getPreFindBySpecificationFunction(
            final PreFindBySpecificationFunction<Specification> newFunction) {
        return nonNull(newFunction)
                ? newFunction
                : (specification, directives) -> {
                    LOGGER.debug("Default preFindBySpecFunction executed, specification {}, directives {}",
                            specification, directives);
                    return specification;
                };
    }

    private PosFindBySpecificationFunction<MultipleResult> getPosFindBySpecificationFunction(
            final PosFindBySpecificationFunction<MultipleResult> newFunction) {
        return nonNull(newFunction)
                ? newFunction
                : (multipleResult, directives) -> {
                    LOGGER.debug("Default posFindBySpecFunction executed, finalMultipleResult {}, directives {}",
                            multipleResult, directives);
                    return multipleResult;
                };
    }

    private ErrorFindBySpecificationFunction<Specification> getErrorFindBySpecificationFunction(
            ErrorFindBySpecificationFunction<Specification> newFunction) {
        return nonNull(newFunction)
                ? newFunction
                : (exception, specification, directives) -> {
                    LOGGER.debug(
                            "Default errorFindBySpecFunction executed, specification {}, finalException {}, directives {} ",
                            specification, exception, directives);
                    return exception;
                };
    }

    private PreFindOneBySpecificationFunction<Specification> getPreFindOneBySpecificationFunction(
            final PreFindOneBySpecificationFunction<Specification> newFunction) {
        return nonNull(newFunction)
                ? newFunction
                : (specification, directives) -> {
                    LOGGER.debug("Default preFindOneBySpec executed, finalSpecification {}, directives {}",
                            specification, directives);
                    return specification;
                };
    }

    private PosFindOneBySpecificationFunction<OneResult> getPosFindOneBySpecificationFunction(
            final PosFindOneBySpecificationFunction<OneResult> newFunction) {
        return nonNull(newFunction)
                ? newFunction
                : (oneResult, directives) -> {
                    LOGGER.debug("Default posFindOneBySpec executed, finalOneResult {}, directives {}",
                            oneResult, directives);
                    return oneResult;
                };
    }

    private ErrorFindOneBySpecificationFunction<Specification> getErrorFindOneBySpecificationFunction(
            final ErrorFindOneBySpecificationFunction<Specification> newFunction) {
        return nonNull(newFunction)
                ? newFunction
                : (exception, specification, directives) -> {
                    LOGGER.debug("Executing default errorFindOneBySpec, specification {}, exception {}, directives {}",
                            specification, exception, directives);
                    return exception;
                };
    }

    private PreCountBySpecificationFunction<Specification> getPreCountBySpecificationFunction(
            final PreCountBySpecificationFunction<Specification> newFunction) {
        return nonNull(newFunction)
                ? newFunction
                : (specification, directives) -> {
                    LOGGER.debug("Default preCountBySpecFunction executed, specification {}, directives {}",
                            specification, directives);
                    return specification;
                };
    }

    private PosCountBySpecificationFunction<CountResult> getPosCountBySpecificationFunction(
            final PosCountBySpecificationFunction<CountResult> newFunction) {
        return nonNull(newFunction)
                ? newFunction
                : (countResult, directives) -> {
                    LOGGER.debug("Default posCountBySpecFunction executed, countResult {}, directives {}",
                            countResult, directives);
                    return countResult;
                };
    }

    private ErrorCountBySpecificationFunction<Specification> getErrorCountBySpecificationFunction(
            final ErrorCountBySpecificationFunction<Specification> newSpecFunction) {
        return nonNull(newSpecFunction)
                ? newSpecFunction
                : (exception, specification, directives) -> {
                    LOGGER.debug(
                            "Default errorCountBySpecFunction executed, specification {}, finalException {}, directives {} ",
                            specification, exception, directives);
                    return exception;
                };
    }

    private ErrorExistsBySpecificationFunction<Specification> getErrorExistsBySpecificationFunction(
            final ErrorExistsBySpecificationFunction<Specification> newFunction) {
        return nonNull(newFunction)
                ? newFunction
                : (exception, specification, directives) -> {
                    LOGGER.debug(
                            "Default errorExistsBySpecFunction executed, specification {}, exception {}, directives {}",
                            specification, exception, directives);
                    return exception;
                };
    }

    private PosExistsBySpecificationFunction<ExistsResult> getPosExistsBySpecificationFunction(
            final PosExistsBySpecificationFunction<ExistsResult> newFunction) {
        return nonNull(newFunction)
                ? newFunction
                : (existsResult, directives) -> {
                    LOGGER.debug("Default posExistsBy executed, existsResult {}, directives {}",
                            existsResult, directives);
                    return existsResult;
                };
    }

    private PreExistsBySpecificationFunction<Specification> getPreExistsBySpecificationFunction(
            final PreExistsBySpecificationFunction<Specification> newFunction) {
        return nonNull(newFunction)
                ? newFunction
                : (specification, directives) -> {
                    LOGGER.debug("Default preExistsBySpecFunction executed, specification {}, directives {}",
                            specification, directives);
                    return specification;
                };
    }
}
