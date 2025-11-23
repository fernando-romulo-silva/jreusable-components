package org.reusablecomponents.base.core.application.query.entity.specification;

import org.reusablecomponents.base.core.application.base.BaseFacade;
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

/**
 * 
 */
public abstract sealed class AbstractQuerySpecificationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification>
        extends BaseFacade<Entity, Id>
        permits QuerySpecificationFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractQuerySpecificationFacade.class);

    /**
     * Function executed in
     * {@link QuerySpecificationFacade#findBySpecification(Object, Object...)
     * findBySpec}
     * method before the {@link QuerySpecificationFacade#findBySpecificationFunction
     * findBySpecFunction}, use it to configure, change, etc. the query input.
     */
    protected PreFindBySpecificationFunction<Specification> preFindBySpecificationFunction;

    /**
     * Function executed in
     * {@link QuerySpecificationFacade#findBySpecification(Object, Object...)
     * findBySpec}
     * method after the {@link QuerySpecificationFacade#findBySpecificationFunction
     * findBySpecFunction}, use it to configure, change, etc. the result.
     */
    protected PosFindBySpecificationFunction<MultipleResult> posFindBySpecificationFunction;

    /**
     * Function executed in
     * {@link QuerySpecificationFacade#findBySpecification(Object, Object...)
     * findBySpec}
     * method to handle {@link QuerySpecificationFacade#findBySpecificationFunction
     * findBySpecFunction} errors.
     */
    protected ErrorFindBySpecificationFunction<Specification> errorFindBySpecificationFunction;

    /**
     * Function executed in
     * {@link QuerySpecificationFacade#findOneBySpecification(Object, Object...)
     * findOneBySpec}
     * method before the {@link QuerySpecificationFacade#findOneBySpecFunction
     * findOneBySpecFunction}, use it to configure, change, etc. the input.
     */
    protected PreFindOneBySpecificationFunction<Specification> preFindOneBySpecificationFunction;

    /**
     * Function executed in
     * {@link QuerySpecificationFacade#findOneBySpecification(Object, Object...)
     * findOneBySpec} method after the
     * {@link QuerySpecificationFacade#findOneBySpecFunction
     * findOneBySpecFunction}, use it to configure, change, etc. the result.
     */
    protected PosFindOneBySpecificationFunction<OneResult> posFindOneBySpecificationFunction;

    /**
     * Function executed in
     * {@link QuerySpecificationFacade#findOneBySpecification(Object, Object...)
     * findOneBySpec} method to handle
     * {@link QuerySpecificationFacade#findBySpecificationFunction
     * findBySpecFunction}
     * errors.
     */
    protected ErrorFindOneBySpecificationFunction<Specification> errorFindOneBySpecificationFunction;

    /**
     * Function executed in
     * {@link QuerySpecificationFacade#countBySpecification(Object, Object...)
     * countBySpec}
     * method before the {@link QuerySpecificationFacade#countBySpecFunction
     * countBySpecFunction}, use it to configure, change, etc. the input.
     */
    protected PreCountBySpecificationFunction<Specification> preCountBySpecificationFunction;

    /**
     * Function executed in
     * {@link QuerySpecificationFacade#countBySpecification(Object, Object...)
     * countBySpec}
     * method after the {@link QuerySpecificationFacade#countBySpecFunction
     * countBySpecFunction}, use it to configure, change, etc. the result.
     */
    protected PosCountBySpecificationFunction<CountResult> posCountBySpecificationFunction;

    /**
     * Function executed in
     * {@link QuerySpecificationFacade#countBySpecification(Object, Object...)
     * countBySpec}
     * method to handle {@link QuerySpecificationFacade#countBySpecFunction
     * countBySpecFunction} errors.
     */
    protected ErrorCountBySpecificationFunction<Specification> errorCountBySpecificationFunction;

    /**
     * Method executed in
     * {@link QuerySpecificationFacade#existsBySpecification(Object, Object...)
     * existsBySpec}
     * method before the {@link QuerySpecificationFacade#existsBySpecFunction
     * existsBySpecFunction}, use it to configure, change, etc. the input.
     */
    protected PreExistsBySpecificationFunction<Specification> preExistsBySpecificationFunction;

    /**
     * Method executed in
     * {@link QuerySpecificationFacade#existsBySpecification(Object, Object...)
     * existsBySpec}
     * method after the {@link QuerySpecificationFacade#existsBySpecFunction
     * existsBySpecFunction}, use it to configure, change, etc. the result.
     */
    protected PosExistsBySpecificationFunction<ExistsResult> posExistsBySpecificationFunction;

    /**
     * Method executed in
     * {@link QuerySpecificationFacade#existsBySpecification(Object, Object...)
     * existsBySpec}
     * method to handle {@link QuerySpecificationFacade#existsBySpecFunction
     * existsBySpecFunction} errors.
     */
    protected ErrorExistsBySpecificationFunction<Specification> errorExistsBySpecificationFunction;

    protected AbstractQuerySpecificationFacade(
            @NotNull final AbstractQuerySpecificationFacadeBuilder<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification> builder) {
        LOGGER.debug("Constructing AbstractQuerySpecificationFacade {}", builder);

        super(builder);

        this.preFindBySpecificationFunction = builder.preFindBySpecificationFunction;
        this.posFindBySpecificationFunction = builder.posFindBySpecificationFunction;
        this.errorFindBySpecificationFunction = builder.errorFindBySpecificationFunction;

        this.preFindOneBySpecificationFunction = builder.preFindOneBySpecificationFunction;
        this.posFindOneBySpecificationFunction = builder.posFindOneBySpecificationFunction;
        this.errorFindOneBySpecificationFunction = builder.errorFindOneBySpecificationFunction;

        this.preCountBySpecificationFunction = builder.preCountBySpecificationFunction;
        this.posCountBySpecificationFunction = builder.posCountBySpecificationFunction;
        this.errorCountBySpecificationFunction = builder.errorCountBySpecificationFunction;

        this.preExistsBySpecificationFunction = builder.preExistsBySpecificationFunction;
        this.posExistsBySpecificationFunction = builder.posExistsBySpecificationFunction;
        this.errorExistsBySpecificationFunction = builder.errorExistsBySpecificationFunction;
    }

    @NotNull
    public PreFindBySpecificationFunction<Specification> getPreFindBySpecificationFunction() {
        LOGGER.debug("Returning preFindBySpecFunction function {}", preFindBySpecificationFunction.getName());
        return preFindBySpecificationFunction;
    }

    @NotNull
    public PosFindBySpecificationFunction<MultipleResult> getPosFindBySpecificationFunction() {
        LOGGER.debug("Returning posFindBySpecFunction function {}", posFindBySpecificationFunction.getName());
        return posFindBySpecificationFunction;
    }

    @NotNull
    public ErrorFindBySpecificationFunction<Specification> getErrorFindBySpecificationFunction() {
        LOGGER.debug("Returning errorFindBySpecFunction function {}", errorFindBySpecificationFunction.getName());
        return errorFindBySpecificationFunction;
    }

    @NotNull
    public PreFindOneBySpecificationFunction<Specification> getPreFindOneBySpecificationFunction() {
        LOGGER.debug("Returning preFindOneBySpecFunction function {}", preFindOneBySpecificationFunction.getName());
        return preFindOneBySpecificationFunction;
    }

    @NotNull
    public PosFindOneBySpecificationFunction<OneResult> getPosFindOneBySpecificationFunction() {
        LOGGER.debug("Returning posFindOneBySpecFunction function {}", posFindOneBySpecificationFunction.getName());
        return posFindOneBySpecificationFunction;
    }

    @NotNull
    public ErrorFindOneBySpecificationFunction<Specification> getErrorFindOneBySpecificationFunction() {
        LOGGER.debug("Returning errorFindOneBySpecFunction function {}", errorFindOneBySpecificationFunction.getName());
        return errorFindOneBySpecificationFunction;
    }

    @NotNull
    public PreCountBySpecificationFunction<Specification> getPreCountBySpecificationFunction() {
        LOGGER.debug("Returning preCountBySpecFunction function {}", preCountBySpecificationFunction.getName());
        return preCountBySpecificationFunction;
    }

    @NotNull
    public PosCountBySpecificationFunction<CountResult> getPosCountBySpecificationFunction() {
        LOGGER.debug("Returning posCountBySpecFunction function {}", posCountBySpecificationFunction.getName());
        return posCountBySpecificationFunction;
    }

    @NotNull
    public ErrorCountBySpecificationFunction<Specification> getErrorCountBySpecificationFunction() {
        LOGGER.debug("Returning errorCountBySpecFunction function {}", errorCountBySpecificationFunction.getName());
        return errorCountBySpecificationFunction;
    }

    @NotNull
    public PreExistsBySpecificationFunction<Specification> getPreExistsBySpecificationFunction() {
        LOGGER.debug("Returning preExistsBySpecFunction function {}", preExistsBySpecificationFunction.getName());
        return preExistsBySpecificationFunction;
    }

    @NotNull
    public PosExistsBySpecificationFunction<ExistsResult> getPosExistsBySpecificationFunction() {
        LOGGER.debug("Returning posExistsBySpecFunction function {}", posExistsBySpecificationFunction.getName());
        return posExistsBySpecificationFunction;
    }

    @NotNull
    public ErrorExistsBySpecificationFunction<Specification> getErrorExistsBySpecificationFunction() {
        LOGGER.debug("Returning errorExistsBySpecFunction function {}", errorExistsBySpecificationFunction.getName());
        return errorExistsBySpecificationFunction;
    }
}
