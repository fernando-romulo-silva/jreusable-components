package org.reusablecomponents.base.core.application.query.entity.specification;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.nonNull;

import java.util.function.Consumer;

import org.reusablecomponents.base.core.application.query.entity.specification.function.count_by_spec.CountBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.exists_by_spec.ExistsBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_by_spec.FindBySpecificationFunction;
import org.reusablecomponents.base.core.application.query.entity.specification.function.find_one_by_spec.FindOneBySpecFunction;
import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotNull;

/**
 * The <code>EntityQuerySpecificationFacade</code> builder's class.
 */
public class QuerySpecificationFacadeBuilder<Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification>
        extends
        AbstractQuerySpecificationFacadeBuilder<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification> {

    public FindBySpecificationFunction<Specification, MultipleResult> findBySpecificationFunction;

    public FindOneBySpecFunction<Specification, OneResult> findOneBySpecificationFunction;

    public ExistsBySpecificationFunction<Specification, ExistsResult> existsBySpecificationFunction;

    public CountBySpecificationFunction<Specification, CountResult> countBySpecificationFunction;

    /**
     * Default constructor
     * 
     * @param builder Object in charge to construct this one
     */
    public QuerySpecificationFacadeBuilder(
            @NotNull final Consumer<QuerySpecificationFacadeBuilder<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification>> function) {
        super(function);

        this.findBySpecificationFunction = nonNull(findBySpecificationFunction)
                ? findBySpecificationFunction
                : (specification, directives) -> {
                    throw new UnsupportedOperationException("Unimplemented function 'findBySpecificationFunction'");
                };

        this.findOneBySpecificationFunction = nonNull(findOneBySpecificationFunction)
                ? findOneBySpecificationFunction
                : (specification, directives) -> {
                    throw new UnsupportedOperationException("Unimplemented function 'findOneBySpecificationFunction'");
                };

        this.existsBySpecificationFunction = nonNull(existsBySpecificationFunction)
                ? existsBySpecificationFunction
                : (specification, directives) -> {
                    throw new UnsupportedOperationException("Unimplemented function 'existsBySpecificationFunction'");
                };

        this.countBySpecificationFunction = nonNull(countBySpecificationFunction)
                ? countBySpecificationFunction
                : (specification, directives) -> {
                    throw new UnsupportedOperationException("Unimplemented function 'countBySpecificationFunction'");
                };
    }
}
