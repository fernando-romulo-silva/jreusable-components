package org.reusablecomponents.core.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.function.Consumer;

import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;

public abstract class AbstractEntityBuilder <Id, Entity extends AbstractEntity<Id>> {
    
    public String createdReason;
    
    protected Entity validate(final Entity entity) {
	
	checkNotNull(entity, "Entity argument cannot be null");
	
	final var validator = getValidator();

	entity.validade(validator);

	return entity;
    }

    protected abstract Validator getValidator();
    
    @Valid 
    @NotNull 
    public abstract Entity build();
    
    
    
//    protected abstract <Builder extends AbstractEntityBuilder<Id, Entity>> Builder with(@NotNull final BuilderConsumer<Id, Entity, Builder> function);
    
    @FunctionalInterface
    static interface BuilderConsumer<Id, Entity extends AbstractEntity<Id>, Builder extends AbstractEntityBuilder<Id, Entity>> extends Consumer<Builder> {

	void accept(final Builder builder);
    }

}
