package org.reusablecomponent.core.domain;

import java.util.function.Consumer;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;

public abstract class AbstractEntityBuilder <Id, Entity extends AbstractEntity<Id>> {
    
    public String createdReason;
    
    protected final Validator validator;
    
    protected AbstractEntityBuilder(final Validator validator) {
	this.validator = validator;
    }

    @Valid @NotNull public abstract Entity build();
    
    protected Entity validate(final Entity entity) {
	
        final var violations = validator.validate(entity);
	
        if (!violations.isEmpty()) {
	    throw new ConstraintViolationException(violations);
	}

	return entity;
    }
    
    
    
//    protected abstract <Builder extends AbstractEntityBuilder<Id, Entity>> Builder with(@NotNull final BuilderConsumer<Id, Entity, Builder> function);
    
    @FunctionalInterface
    static interface BuilderConsumer<Id, Entity extends AbstractEntity<Id>, Builder extends AbstractEntityBuilder<Id, Entity>> extends Consumer<Builder> {

	void accept(final Builder builder);
    }

}
