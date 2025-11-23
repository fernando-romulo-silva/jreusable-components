package org.reusablecomponents.base.core.application.query.entity.pagination;

import static java.util.Objects.nonNull;

import java.util.function.Consumer;

import org.reusablecomponents.base.core.application.base.BaseFacadeBuilder;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged.ErrorFindAllPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged.PosFindAllPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_all_paged.PreFindAllPagedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted.ErrorFindOneSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted.PosFindOneSortedFunction;
import org.reusablecomponents.base.core.application.query.entity.pagination.function.find_one_sorted.PreFindOneSortedFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public class AbstractQueryPaginationFacadeBuilder<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort>
        extends BaseFacadeBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQueryPaginationFacadeBuilder.class);

    public PreFindAllPagedFunction<Pageable> preFindAllPagedFunction;

    public PosFindAllPagedFunction<MultiplePagedResult> posFindAllPagedFunction;

    public ErrorFindAllPagedFunction<BaseException, Pageable> errorFindAllPagedFunction;

    public PreFindOneSortedFunction<Sort> preFindOneSortedFunction;

    public PosFindOneSortedFunction<OneResult> posFindOneSortedFunction;

    public ErrorFindOneSortedFunction<BaseException, Sort> errorFindOneSortedFunction;

    public AbstractQueryPaginationFacadeBuilder(
            @NotNull final Consumer<? extends AbstractQueryPaginationFacadeBuilder<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort>> function) {
        super(function);

        this.preFindAllPagedFunction = getPreFindAllPagedFunction();
        this.posFindAllPagedFunction = getPosFindAllPagedFunction();
        this.errorFindAllPagedFunction = getErrorFindAllPagedFunction();

        this.preFindOneSortedFunction = getPreFindOneSortedFunction();
        this.posFindOneSortedFunction = getPosFindOneSortedFunction();
        this.errorFindOneSortedFunction = getErrorFindOneSortedFunction();
    }

    private PreFindAllPagedFunction<Pageable> getPreFindAllPagedFunction() {
        return nonNull(preFindAllPagedFunction)
                ? preFindAllPagedFunction
                : (pageable, directives) -> {
                    LOGGER.debug("Executing default preFindAll, pageable {}, directives {}", pageable, directives);
                    return pageable;
                };
    }

    private PosFindAllPagedFunction<MultiplePagedResult> getPosFindAllPagedFunction() {
        return nonNull(posFindAllPagedFunction)
                ? posFindAllPagedFunction
                : (multiplePagedResult, directives) -> {
                    LOGGER.debug("Executing default posFindAll, multiplePagedResult {}, directives {}",
                            multiplePagedResult, directives);
                    return multiplePagedResult;
                };
    }

    private ErrorFindAllPagedFunction<BaseException, Pageable> getErrorFindAllPagedFunction() {
        return nonNull(errorFindAllPagedFunction)
                ? errorFindAllPagedFunction
                : (exception, pageable, directives) -> {
                    LOGGER.debug("Executing default errorFindAll, pageable {}, exception {}, directives {} ",
                            pageable, exception, directives);
                    return exception;
                };
    }

    private PreFindOneSortedFunction<Sort> getPreFindOneSortedFunction() {
        return nonNull(preFindOneSortedFunction)
                ? preFindOneSortedFunction
                : (sort, directives) -> {
                    LOGGER.debug("Executing default preFindOne, sort {}, directives {}", sort, directives);
                    return sort;
                };
    }

    private PosFindOneSortedFunction<OneResult> getPosFindOneSortedFunction() {
        return nonNull(posFindOneSortedFunction)
                ? posFindOneSortedFunction
                : (oneResult, directives) -> {
                    LOGGER.debug("Executing default oneResult, multiplePagedResult {}, directives {}", oneResult,
                            directives);
                    return oneResult;
                };
    }

    private ErrorFindOneSortedFunction<BaseException, Sort> getErrorFindOneSortedFunction() {
        return nonNull(errorFindOneSortedFunction)
                ? errorFindOneSortedFunction
                : (exception, sort, directives) -> {
                    LOGGER.debug("Executing default errorFindOne, pageable {}, exception {}, directives {} ",
                            sort, exception, directives);
                    return exception;
                };
    }
}
