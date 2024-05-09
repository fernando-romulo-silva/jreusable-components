package org.reusablecomponent.core.application.query.entity.nonpaged;

import static org.reusablecomponent.core.infra.messaging.event.CommonEvent.FIND_ALL;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.reusablecomponent.core.application.base.AbstractEntiyBaseFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
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
 * @param <BooleanResult>
 */
public class EntityQueryFacade <Entity extends AbstractEntity<Id>, Id, QueryIdIn, Directives, OneResult, MultipleResult, CountResult, BooleanResult> 
	extends AbstractEntiyBaseFacade<Entity, Id> 
	implements InterfaceEntityQueryFacade<Entity, Id, QueryIdIn, Directives, OneResult, MultipleResult, CountResult, BooleanResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityQueryFacade.class);
    
    protected final Function<QueryIdIn, BooleanResult> existsByIdFunction;
    
    protected final BiFunction<QueryIdIn, Directives, OneResult> findByIdFunction;
    
    protected final Function<Directives, MultipleResult> findAllFunction;
    
    protected final Supplier<CountResult> countAllFunction;
    
    /**
     * @param existsByIdFunction
     * @param findByIdFunction
     * @param findAllFunction
     * @param countAllFunction
     */
    public EntityQueryFacade(
		    @NotNull final Function<QueryIdIn, BooleanResult> existsByIdFunction,
		    @NotNull final BiFunction<QueryIdIn, Directives, OneResult> findByIdFunction,
		    @NotNull final Function<Directives, MultipleResult> findAllFunction,
		    @NotNull final Supplier<CountResult> countAllFunction) {
	super();
	this.existsByIdFunction = existsByIdFunction;
	this.findByIdFunction = findByIdFunction;
	this.findAllFunction = findAllFunction;
	this.countAllFunction = countAllFunction;
    }
    
    // ---------------------------------------------------------------------------
    
    /**
     * @param saveEntityIn
     * @return
     */
    protected String convertDirectivesToPublishData(final Directives directives) {
	return Objects.toString(directives);
    }
    
    /**
     * @param saveEntityOut
     * @return
     */
    protected String convertMultipleResultToPublishData(final MultipleResult multipleResult) {
	return Objects.toString(multipleResult);
    }
    
    /**
     * @param saveEntityOut
     * @return
     */
    protected String convertOneResultToPublishData(final OneResult oneResult) {
	return Objects.toString(oneResult);
    }
    
    // ---------------------------------------------------------------------------

    /**
     * @param entity
     */
    protected Directives preFindAll(final Directives directives) {
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
    public MultipleResult findAll() {
	return findAll(null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public MultipleResult findAll(@Nullable final Directives directives) {
	
	final var session = securityService.getSession();
	
	LOGGER.debug("Findind all '{}', session '{}'", getEntityClazz().getSimpleName(), session);
	
	final var finalDirectives = preFindAll(directives);
	
//	final var formatDirectives = Optional.ofNullable(directives)
//        	.map(params -> params.get("format"))
//        	.stream()
//        	.flatMap(Arrays::stream)
//        	.collect(Collectors.toList());
//        	.anyMatch("full"::equalsIgnoreCase);	
	
	final var result = findAllFunction.apply(directives);
	
	final var resultFinal = posFindAll(result);
	
	publish(convertDirectivesToPublishData(finalDirectives), convertMultipleResultToPublishData(resultFinal), FIND_ALL);
	
	LOGGER.debug("Found '{}', session '{}'", getEntityClazz().getSimpleName(), session);
	
	return result;
    }
    
    // ---------------------------------------------------------------------------
    
    /**
     * {@inheritDoc}
     */
    @Override
    public OneResult findBy(final QueryIdIn queryIdIn) {
	return findBy(queryIdIn, null);
    }    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public OneResult findBy(@NotNull final QueryIdIn queryIdIn, @Nullable final Directives directives) {
	
	final var result = findByIdFunction.apply(queryIdIn, directives);
	
//	if (result instanceof Optional resultOptional && resultOptional.isEmpty()) {
//	    throw new ElementWithIdNotFoundException(getEntityClazz(), i18nService, queryIdIn);
//	}
	
	return result;
    }
    
    
    // ---------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanResult existsBy(@NotNull final QueryIdIn queryIdIn) {
	
	final var result = existsByIdFunction.apply(queryIdIn);
	
	return result;
    }
    
    // ---------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public CountResult count() {
	return countAllFunction.get();
    }
}
