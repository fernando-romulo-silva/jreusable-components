package org.reusablecomponent.core.application.query.entity.paged;

import static org.reusablecomponent.core.infra.messaging.event.CommonEvent.*;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.reusablecomponent.core.application.base.AbstractEntiyBaseFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 * @param <OneResult>
 * @param <MultiplePagedResult>
 * @param <Pageable>
 * @param <Sort>
 */
public class EntityQueryPaginationFacade<Entity extends AbstractEntity<Id>, Id, OneResult, MultiplePagedResult, Pageable, Sort>
	extends AbstractEntiyBaseFacade<Entity, Id>
	implements InterfaceEntityQueryPaginationFacade<Entity, Id, OneResult, MultiplePagedResult, Pageable, Sort> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityQueryPaginationFacade.class);
    
    protected final BiFunction<Pageable, Object[], MultiplePagedResult> findAllFunction;
    
    protected final  Function<Sort, OneResult> findFirstFunction;
    
    /**
     * @param findAllFunction
     * @param findFirstFunction
     */
    public EntityQueryPaginationFacade(
		    @NotNull final BiFunction<Pageable, Object[], MultiplePagedResult> findAllFunction, 
		    @NotNull final Function<Sort, OneResult> findFirstFunction) {
	super();
	this.findAllFunction = findAllFunction;
	this.findFirstFunction = findFirstFunction;
    }

    // ---------------------------------------------------------------------------
    protected String convertPageableToPublishData(final Pageable pageable) {
	return Objects.toString(pageable);
    }
    
    protected String convertMultiplePagedResultToPublishData(final MultiplePagedResult multiplePagedResult) {
	return Objects.toString(multiplePagedResult);
    }
    
    protected Pageable preFindAll(final Pageable pageable, @NotNull final Object... directives) {
	return pageable;
    }

    protected MultiplePagedResult posFindAll(final MultiplePagedResult multiplePagedResult) {
	return multiplePagedResult;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @SafeVarargs
    public final MultiplePagedResult findAll(@Nullable final Pageable pageable, @NotNull final Object... directives) {
	
	final var session = securityService.getSession();
	
	LOGGER.debug("Finding all by '{}', session '{}'", pageable, session);
	
	final var finalPageable = preFindAll(pageable, directives);
	
	final MultiplePagedResult result;
	
	try {
	    result = findAllFunction.apply(finalPageable, directives);
	} catch (final Exception ex) {
	    throw exceptionTranslatorService.translate(ex, i18nService);
	}
	
	final var finalResult = posFindAll(result);
	
	final var dataIn = convertPageableToPublishData(finalPageable);
	final var dataOut = convertMultiplePagedResultToPublishData(finalResult);
	publish(dataIn, dataOut, FIND_ALL_PAGEABLE);
	
	LOGGER.debug("Found all by '{}', session '{}'", finalPageable, session);
	
	return finalResult;
    }

    
    // ---------------------------------------------------------------------------
    
    protected String convertSortToPublishData(final Sort sort) {
	return Objects.toString(sort);
    }
    
    protected String convertOneResultResultToPublishData(final OneResult oneResult) {
	return Objects.toString(oneResult);
    }
    
    protected Sort preFindFirst(final Sort sort) {
	return sort;
    }

    protected OneResult posFindFirst(final OneResult oneResult) {
	return oneResult;
    }  
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final OneResult findFirst(@Nullable final Sort sort) {
	
	final var session = securityService.getSession();
	
	LOGGER.debug("Finding first by '{}', session '{}'", sort, session);
	
	final var finalSort = preFindFirst(sort);
	
	final OneResult result; 
	
	try {
	    result = findFirstFunction.apply(finalSort);
	} catch (final Exception ex) {
	    throw exceptionTranslatorService.translate(ex, i18nService);
	}
	
	final var finalResult = posFindFirst(result);
	
	final var dataIn = convertSortToPublishData(finalSort);
	final var dataOut = convertOneResultResultToPublishData(finalResult);
	publish(dataIn, dataOut, FIND_ALL_PAGEABLE);
	
	LOGGER.debug("Found first by '{}', session '{}'", finalSort, session);
	
	return finalResult;
    }
}
