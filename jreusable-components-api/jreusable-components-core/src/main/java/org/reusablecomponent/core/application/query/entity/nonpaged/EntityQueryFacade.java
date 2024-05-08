package org.reusablecomponent.core.application.query.entity.nonpaged;

import static org.reusablecomponent.core.infra.messaging.event.CommonEvent.FIND_ALL;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.reusablecomponent.core.application.base.AbstractEntiyBaseFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.core.infra.exception.ElementWithIdNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 */
public class EntityQueryFacade <Entity extends AbstractEntity<Id>, Id, QueryIdIn, OneResult, MultipleResult, CountResult, BooleanResult> 
	extends AbstractEntiyBaseFacade<Entity, Id> 
	implements InterfaceEntityQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, BooleanResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityQueryFacade.class);
    
    protected final Function<QueryIdIn, BooleanResult> existsByIdFunction;
    
    protected final Function<QueryIdIn, OneResult> findByIdFunction;
    
    protected final Supplier<MultipleResult> findAllFunction;
    
    protected final Supplier<CountResult> countAllFunction;
    
    /**
     * @param existsByIdFunction
     * @param findByIdFunction
     * @param findAllFunction
     * @param countAllFunction
     */
    public EntityQueryFacade(
		    @NotNull final Function<QueryIdIn, BooleanResult> existsByIdFunction,
		    @NotNull final Function<QueryIdIn, OneResult> findByIdFunction,
		    @NotNull final Supplier<MultipleResult> findAllFunction,
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
    protected String convertDirectivesToPublishData(final Map<String, String[]> directives) {
	return Objects.toString(directives);
    }
    
    /**
     * @param saveEntityOut
     * @return
     */
    protected String convertMultipleResultPublishData(final MultipleResult multipleResult) {
	return Objects.toString(multipleResult);
    }    

    /**
     * @param entity
     */
    protected Map<String, String[]> preFindAll(final Map<String, String[]> directives) {
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
    public MultipleResult findAll(@Nullable final Map<String, String[]> directives) {
	
	final var session = securityService.getSession();
	
	LOGGER.debug("Findind all '{}', session '{}'", getEntityClazz().getSimpleName(), session);
	
	final var finalDirectives = preFindAll(directives);
	
//	final var formatDirectives = Optional.ofNullable(directives)
//        	.map(params -> params.get("format"))
//        	.stream()
//        	.flatMap(Arrays::stream)
//        	.collect(Collectors.toList());
//        	.anyMatch("full"::equalsIgnoreCase);	
	
	final var result = findAllFunction.get();
	
	final var resultFinal = posFindAll(result);
	
	publish(convertDirectivesToPublishData(finalDirectives), convertMultipleResultPublishData(resultFinal), FIND_ALL);
	
	LOGGER.debug("Found '{}', session '{}'", getEntityClazz().getSimpleName(), session);
	
	return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public OneResult findBy(@NotNull final QueryIdIn id, final Map<String, String[]> directives) {
	
	final var result = findByIdFunction.apply(id);
	
	if (result instanceof Optional resultOptional && resultOptional.isEmpty()) {
	    throw new ElementWithIdNotFoundException(getEntityClazz(), i18nService, id);
	}
	
	return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanResult existsBy(@NotNull final QueryIdIn id) {
	
	final var result = existsByIdFunction.apply(id);
	
	return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CountResult count() {
	return countAllFunction.get();
    }
}
