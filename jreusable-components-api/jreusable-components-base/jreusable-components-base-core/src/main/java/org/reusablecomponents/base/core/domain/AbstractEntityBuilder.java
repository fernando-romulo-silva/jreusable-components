package org.reusablecomponents.base.core.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;

public abstract class AbstractEntityBuilder<Id, Entity extends AbstractEntity<Id>> {

    public String createdReason;

    protected AbstractEntityBuilder() {
        super();
    }

    protected abstract Validator getValidator();

    @Valid
    @NotNull
    public abstract Entity build();

    protected Entity validate(final Entity entity) {

        checkNotNull(entity, "Entity argument cannot be null");

        final var validator = getValidator();

        entity.validade(validator);

        return entity;
    }

    // protected abstract AbstractEntityBuilder<Id, Entity> with(
    // @NotNull final Consumer<? extends AbstractEntityBuilder<Id, Entity>>
    // function);

    // @FunctionalInterface
    // public static interface BuilderConsumer<Id, Entity extends
    // AbstractEntity<Id>, Builder extends AbstractEntityBuilder<Id, Entity>>
    // extends Consumer<Builder> {

    // void accept(final Builder builder);
    // }

}
