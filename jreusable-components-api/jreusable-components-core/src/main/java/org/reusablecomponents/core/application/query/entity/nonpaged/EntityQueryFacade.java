package org.reusablecomponents.core.application.query.entity.nonpaged;

import static org.reusablecomponents.core.infra.messaging.event.CommonEvent.*;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;
import org.reusablecomponents.core.application.base.EntiyBaseFacade;
import org.reusablecomponents.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;


/**
 * @param <Entity>
 * @param <Id>
 * @param <QueryIdIn>
 * @param <Directives>
 * @param <OneResult>
 * @param <MultipleResult>
 * @param <CountResult>
 * @param <ExistsResult>
 */
public non-sealed class EntityQueryFacade <Entity extends AbstractEntity<Id>, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> 
	extends EntiyBaseFacade<Entity, Id> 
	implements InterfaceEntityQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityQueryFacade.class);
    
    protected final Function<QueryIdIn, ExistsResult> existsByIdFunction;
    
    protected final BiFunction<QueryIdIn, Object[], OneResult> findByIdFunction;
    
    protected final Function<Object[], MultipleResult> findAllFunction;
    
    protected final Supplier<CountResult> countAllFunction;
    
    protected final Supplier<ExistsResult> existsAllFunction;
    
    /**
     * @param existsByIdFunction
     * @param findByIdFunction
     * @param findAllFunction
     * @param countAllFunction
     */
    public EntityQueryFacade(
		    @NotNull final Function<QueryIdIn, ExistsResult> existsByIdFunction,
		    @NotNull final BiFunction<QueryIdIn, Object[], OneResult> findByIdFunction,
		    @NotNull final Function<Object[], MultipleResult> findAllFunction,
		    @NotNull final Supplier<CountResult> countAllFunction,
		    @NotNull final Supplier<ExistsResult> existsAllFunction) {
	super();
	this.existsByIdFunction = existsByIdFunction;
	this.findByIdFunction = findByIdFunction;
	this.findAllFunction = findAllFunction;
	this.countAllFunction = countAllFunction;
	this.existsAllFunction = existsAllFunction;
    }
    
    // ---------------------------------------------------------------------------
    
    protected String convertMultipleResultToPublishDataOut(final MultipleResult multipleResult) {
	return Objects.toString(multipleResult);
    }
    
    protected String convertOneResultToPublishDataOut(final OneResult oneResult) {
	return Objects.toString(oneResult);
    }
    
    protected String convertCountResultToPublishDataOut(final CountResult countResult) {
	return Objects.toString(countResult);
    }
    
    protected String convertQueryIdInToPublishDataIn(final QueryIdIn queryIdIn) {
	return Objects.toString(queryIdIn);
    } 
    
    // ---------------------------------------------------------------------------
    
    protected String convertDirectivesToPublishDataIn(final Object... directives) {
	return Objects.toString(directives);
    }
    
    /**
     * @param entity
     */
    protected Object[] preFindAll(final Object... directives) {
	
//	final var formatDirectives = Optional.ofNullable(directives)
//	.map(params -> params.get("format"))
//	.stream()
//	.flatMap(Arrays::stream)
//	.collect(Collectors.toList());
//	.anyMatch("full"::equalsIgnoreCase);	
	
	return directives;
    }

    /**
     * @param entity
     */
    protected MultipleResult posFindAll(final MultipleResult multipleResult) {
	return multipleResult;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MultipleResult findAll(@Nullable final Object... directives) {
	
	final var session = securityService.getSession();
	
	LOGGER.debug("Findind all '{}', session '{}'", getEntityClazz().getSimpleName(), session);
	
	final var finalDirectives = preFindAll(directives);
	
	final MultipleResult result;
	
	try {
	    result = findAllFunction.apply(directives);
	} catch (final Exception ex) {
	    throw exceptionTranslatorService.translate(ex, i18nService);
	}
	
	final var finalResult = posFindAll(result);
	
	final var dataIn = convertDirectivesToPublishDataIn(finalDirectives);
	final var dataOut = convertMultipleResultToPublishDataOut(finalResult);
	publish(dataIn, dataOut, FIND_ALL);
	
	LOGGER.debug("Found all '{}', session '{}'", getEntityClazz().getSimpleName(), session);
	
	return finalResult;
    }
    
    // ---------------------------------------------------------------------------
    
    protected QueryIdIn preFindBy(final QueryIdIn queryIdIn, final Object... directives) {
	
//	final var formatDirectives = Optional.ofNullable(directives)
//	.map(params -> params.get("format"))
//	.stream()
//	.flatMap(Arrays::stream)
//	.collect(Collectors.toList());
//	.anyMatch("full"::equalsIgnoreCase);	
	
	return queryIdIn;
    }
    
    protected OneResult posFindBy(final OneResult oneResult) {
	return oneResult;
    }    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public OneResult findBy(@NotNull final QueryIdIn queryIdIn, @Nullable final Object... directives) {
	
	final var session = securityService.getSession();
	
	LOGGER.debug("Findind by '{}', directives '{}', session '{}'", queryIdIn, directives, session);
	
	final var finalQueryIdIn = preFindBy(queryIdIn, directives);	
	
	final OneResult result;
	
	try {
	    result = findByIdFunction.apply(queryIdIn, directives);
	} catch (final Exception ex) {
	    throw exceptionTranslatorService.translate(ex, i18nService);
	}
	
	final var finalResult = posFindBy(result);
	
	final var dataIn = convertQueryIdInToPublishDataIn(finalQueryIdIn);
	final var dataOut = convertOneResultToPublishDataOut(finalResult);
	publish(dataIn, dataOut, FIND_BY_ID);
	
	LOGGER.debug("Found by '{}', result '{}', session '{}'", queryIdIn, finalResult, session);
	
	return finalResult;
    }
    
    
    // ---------------------------------------------------------------------------
    
    protected QueryIdIn preExistsBy(final QueryIdIn queryIdIn) {
	return queryIdIn;
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
    public ExistsResult existsBy(@NotNull final QueryIdIn queryIdIn) {
	
	final var session = securityService.getSession();
	
	LOGGER.debug("Existing by '{}', session '{}'", queryIdIn, session);
	
	final var finalQueryIdIn = preExistsBy(queryIdIn);	
	
	final ExistsResult result;
	
	try { 
	    result = existsByIdFunction.apply(finalQueryIdIn);
	} catch (final Exception ex) {
	    throw exceptionTranslatorService.translate(ex, i18nService);
	}
	
	final var finalResult = posExistsBy(result);
	
	final var dataIn = convertQueryIdInToPublishDataIn(finalQueryIdIn);
	final var dataOut = convertExistsResultToPublishData(finalResult);
	publish(dataIn, dataOut, EXISTS_BY_ID);
	
	LOGGER.debug("Existed by '{}', result '{}', session '{}'", finalQueryIdIn, finalResult, session);
	
	return finalResult;
    }

    // ---------------------------------------------------------------------------

    protected CountResult posCountAll(final CountResult countResult) {
	return countResult;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CountResult countAll() {
	
	final var session = securityService.getSession();
	
	LOGGER.debug("Counting all '{}', session '{}'", getEntityClazz().getSimpleName(), session);
	
	final CountResult result;
	
	try { 
	    result = countAllFunction.get();
	} catch (final Exception ex) {
	    throw exceptionTranslatorService.translate(ex, i18nService);
	}
	
	final var finalResult = posCountAll(result);
	
	final var dataIn = StringUtils.EMPTY;
	final var dataOut = convertCountResultToPublishDataOut(finalResult);
	publish(dataIn, dataOut, COUNT_ALL);
	
	LOGGER.debug("Counted all '{}', result '{}', session '{}'", getEntityClazz().getSimpleName(), finalResult, session);
	
	return finalResult;
    }

    // ---------------------------------------------------------------------------
    
    @Override
    public ExistsResult existsAll() {
	
	final var session = securityService.getSession();
	
	LOGGER.debug("Existing all '{}', session '{}'", getEntityClazz().getSimpleName(), session);
	
	final ExistsResult result;
	
	try { 
	    result = existsAllFunction.get();
	} catch (final Exception ex) {
	    throw exceptionTranslatorService.translate(ex, i18nService);
	}
	
	final var finalResult = posExistsBy(result);
	
	final var dataIn = StringUtils.EMPTY;
	final var dataOut = convertExistsResultToPublishData(finalResult);
	publish(dataIn, dataOut, EXISTS_ALL);
	
	LOGGER.debug("Existed all '{}', result '{}', session '{}'", getEntityClazz().getSimpleName(), finalResult, session);
	
	return finalResult;
    }
}
