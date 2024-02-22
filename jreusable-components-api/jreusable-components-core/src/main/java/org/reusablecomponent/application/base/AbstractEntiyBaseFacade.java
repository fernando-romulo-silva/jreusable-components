package org.reusablecomponent.application.base;

import java.util.function.Function;
import java.util.function.Predicate;

import org.reusablecomponent.domain.AbstractEntity;
import org.reusablecomponent.infra.i18n.DefaultI18nFunction;
import org.reusablecomponent.infra.i18n.I18nFuntion;
import org.reusablecomponent.infra.messaging.InterfaceEventPublisher;
import org.reusablecomponent.infra.security.InterfaceSecurityService;

import com.google.common.reflect.TypeToken;

import jakarta.validation.constraints.NotNull;

/**
 * @param <Entity>
 * @param <Id>
 */
public abstract class AbstractEntiyBaseFacade<Entity extends AbstractEntity<Id>, Id> implements InterfaceEntityBaseFacade<Entity, Id> {

    protected InterfaceEventPublisher eventPublisher;
    
    protected InterfaceSecurityService securityService;
    
    protected final Class<Entity> entityClazz;

    protected final Class<Id> idClazz;

    @SuppressWarnings("unchecked")
    protected AbstractEntiyBaseFacade() {

	final var entityTypeToken = new TypeToken<Entity>(getClass()) {
	    private static final long serialVersionUID = 1L;
	};

	entityClazz = (Class<Entity>) entityTypeToken.getRawType();

	final var idTypeToken = new TypeToken<Id>(getClass()) {
	    private static final long serialVersionUID = 1L;
	};

	idClazz = (Class<Id>) idTypeToken.getRawType();
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    protected Class<Entity> getEntityClazz() {
	return entityClazz;
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    protected InterfaceEventPublisher getEventPublisher() {
        return eventPublisher;
    }
    
    /**
     * @return
     */
    @NotNull
    protected I18nFuntion getI18nFunction() {
	return new DefaultI18nFunction();
    }

    /**
     * @param action
     * @return
     */
    protected Function<Entity, String> getMsgConverter(final String action) {
	return entity -> entity.toString().concat(" ").concat(action);
    }    
    
    /**
     * @param <BooleanResult>
     * @param existsEntityFunction
     * @param booleanResult
     * @return
     */
    protected final <BooleanResult> boolean checkEntityExists(final Predicate<BooleanResult> existsEntityFunction, final BooleanResult booleanResult) {
	return existsEntityFunction.test(booleanResult);
    }
}
