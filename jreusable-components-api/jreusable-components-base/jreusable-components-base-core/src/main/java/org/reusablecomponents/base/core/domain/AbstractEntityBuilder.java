package org.reusablecomponents.base.core.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.function.Consumer;

import org.apache.commons.lang3.ObjectUtils;

import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;

public abstract class AbstractEntityBuilder<Id, Entity extends AbstractEntity<Id>, Builder extends AbstractEntityBuilder<Id, Entity, Builder>> {

    public String createdReason;

    protected AbstractEntityBuilder() {
        super();
    }

    protected Validator getValidator() {
        return null;
    }

    @Valid
    @NotNull
    public abstract Entity build();

    @SuppressWarnings("unchecked")
    protected Builder with(@NotNull final Consumer<Builder> function) {
        function.accept((Builder) this);
        return (Builder) this;
    }

    protected Entity validate(final Entity entity) {

        checkNotNull(entity, "The argument 'entity' cannot be null");

        final var validator = getValidator();

        if (ObjectUtils.isEmpty(validator)) {
            return entity;
        }

        entity.validade(validator);

        return entity;
    }

}
