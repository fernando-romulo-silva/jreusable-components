package org.reusablecomponent.core.application.query.entity.nonpaged;

import static org.reusablecomponent.core.infra.messaging.event.CommonEvent.*;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.reusablecomponent.core.application.base.EntiyBaseFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 * @param <Specification>
 */
public non-sealed class EntityQuerySpecificationFacade <Entity extends AbstractEntity<Id>, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification> 
	extends EntiyBaseFacade<Entity, Id> 
	implements InterfaceEntityQuerySpecificationFacade<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityQuerySpecificationFacade.class);
    
    protected final BiFunction<Specification, Object[], MultipleResult> findBySpecificationFunction;
    
    protected final BiFunction<Specification, Object[], OneResult> findOneByFunction;
    
    protected final Function<Specification, ExistsResult> existsBySpecificationFunction;
    
    protected final Function<Specification, CountResult> countBySpecificationFunction;
    
    /**
     * @param saveFunction
     * @param deleteFunction
     * @param existsByIdFunction
     * @param findByIdFunction
     * @param findAllFunction
     */
    public EntityQuerySpecificationFacade(
		    @NotNull final BiFunction<Specification, Object[], MultipleResult> findBySpecificationFunction,
		    @NotNull final BiFunction<Specification, Object[], OneResult> findOneByFunction,
		    @NotNull final Function<Specification, ExistsResult> existsBySpecificationFunction,
		    @NotNull final Function<Specification, CountResult> countBySpecificationFunction) {
	
	this.findBySpecificationFunction = findBySpecificationFunction;
	this.findOneByFunction = findOneByFunction;
	this.existsBySpecificationFunction = existsBySpecificationFunction;
	this.countBySpecificationFunction = countBySpecificationFunction;
    }

    // ---------------------------------------------------------------------------
    
    protected String convertSpecificationToPublishData(final Specification specification) {
	return Objects.toString(specification);
    }
    
    protected String convertMultipleResultToPublishData(final MultipleResult multipleResult) {
	return Objects.toString(multipleResult);
    }
    
    protected Specification preFindBy(final Specification specification) {
	return specification;
    }

    protected MultipleResult posFindBy(final MultipleResult multipleResult) {
	return multipleResult;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MultipleResult findBy(@NotNull final Specification specification, @NotNull final Object... directives) {
	
	final var session = securityService.getSession();
	
	LOGGER.debug("Findind by '{}', session '{}'", specification, session);
	
	final var finalSpecification = preFindBy(specification);
	
	final MultipleResult result;
	
	try {
	    result =  findBySpecificationFunction.apply(specification, directives);
	} catch (final Exception ex) {
	    throw exceptionTranslatorService.translate(ex, i18nService);
	}
	
	final var finalResult = posFindBy(result);
	
	final var dataIn = convertSpecificationToPublishData(finalSpecification);
	final var dataOut = convertMultipleResultToPublishData(finalResult);
	publish(dataIn, dataOut, FIND_BY_SPECIFICATION);
	
	LOGGER.debug("Found by '{}', session '{}'", finalSpecification, session);
	
	return finalResult;
    }
    
    // ---------------------------------------------------------------------------
    
    protected Specification preFindOneBy(final Specification specification) {
	return specification;
    }

    protected OneResult posFindOneBy(final OneResult oneResult) {
	return oneResult;
    }
    
    protected String convertOneResultToPublishData(final OneResult oneResult) {
	return Objects.toString(oneResult);
    }    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public OneResult findOneBy(@NotNull final Specification specification, @NotNull final Object... directives) {
	
	final var session = securityService.getSession();
	
	LOGGER.debug("Findind one by '{}', session '{}'", specification, session);
	
	final var finalSpecification = preFindOneBy(specification);
	
	final OneResult result; 
	
	try {
	    result = findOneByFunction.apply(finalSpecification, directives);
	} catch (final Exception ex) {
	    throw exceptionTranslatorService.translate(ex, i18nService);
	}
	
	final var finalResult = posFindOneBy(result);
	
	final var dataIn = convertSpecificationToPublishData(finalSpecification);
	final var dataOut = convertOneResultToPublishData(finalResult);
	publish(dataIn, dataOut, FIND_ONE_BY_SPECIFICATION);
	
	LOGGER.debug("Found one by '{}', session '{}'", finalSpecification, session);
	
	return finalResult;
    }
    
    
    // ---------------------------------------------------------------------------

    protected Specification preExistsBy(final Specification specification) {
	return specification;
    }
    
    protected ExistsResult posExistsBy(final ExistsResult existsResult) {
	return existsResult;
    }
    
    protected String convertExistsResultToPublishData(final ExistsResult resultFinal) {
	return Objects.toString(resultFinal);
    }    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final ExistsResult existsBy(@NotNull final Specification specification) {
	
	final var session = securityService.getSession();
	
	LOGGER.debug("Existing by '{}', session '{}'", specification, session);
	
	final var finalSpecification = preExistsBy(specification);	
	
	final ExistsResult result; 
	
	try {
	    result = existsBySpecificationFunction.apply(finalSpecification);
	} catch (final Exception ex) {
	    throw exceptionTranslatorService.translate(ex, i18nService);
	}
	
	final var finalResult = posExistsBy(result);
	
	final var dataIn = convertSpecificationToPublishData(finalSpecification);
	final var dataOut = convertExistsResultToPublishData(finalResult);
	publish(dataIn, dataOut, EXISTS_BY_SPECIFICATION);
	
	LOGGER.debug("Existed by '{}', session '{}'", finalSpecification, session);
	
	return finalResult;
    }
    
    // ---------------------------------------------------------------------------

    protected Specification preCountBy(final Specification specification) {
	return specification;
    }
    
    protected CountResult posCountBy(final CountResult countResult) {
	return countResult;
    }
    
    protected String convertCountResultToPublishData(final CountResult countResult) {
	return Objects.toString(countResult);
    }      

    /**
     * {@inheritDoc}
     */
    @Override
    public final CountResult count(@NotNull final Specification specification) {
	
	final var session = securityService.getSession();
	
	LOGGER.debug("Counting by '{}', session '{}'", specification, session);
	
	final var finalSpecification = preCountBy(specification);
	
	final CountResult result;
	
	try {
	    result = countBySpecificationFunction.apply(finalSpecification);
	} catch (final Exception ex) {
	    throw exceptionTranslatorService.translate(ex, i18nService);
	}
	
	final var finalResult = posCountBy(result);
	
	final var dataIn = convertSpecificationToPublishData(finalSpecification);
	final var dataOut = convertCountResultToPublishData(finalResult);
	publish(dataIn, dataOut, COUNT_BY_SPECIFICATION);
	
	LOGGER.debug("Counted by '{}', session '{}'", finalSpecification, session);
	
	return finalResult;
    }
}
