package org.reusablecomponents.base.core.domain;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Abstract base class for entity builders. Provides common functionality for
 * building and validating entities.
 */
public abstract class AbstractEntityBuilder<Id, Entity extends AbstractEntity<Id>, Builder extends AbstractEntityBuilder<Id, Entity, Builder>> {

    public String createdReason;

    /**
     * Constructor. Protected, since this is an abstract class, and should only be
     * called by sub‑classes.
     */
    protected AbstractEntityBuilder() {
        super();
    }

    /**
     * Convenience method to set a property, using a lambda. The lambda receives the
     * Builder as an argument, and can call any of its methods to set properties.
     * This allows for a more fluent API when building the entity.
     * 
     * @param function A lambda that receives the Builder as an argument, and can
     *                 call any of its methods to set properties.
     * @return the Builder instance, allowing for method chaining.
     */
    protected Builder with(@NotNull final Consumer<Builder> function) {
        requireNonNull(function, "function must not be null")
                .accept(self());
        return self();
    }

    /**
     * Builds the entity, and validates it. If the entity is invalid, a
     * ConstraintViolationException is thrown, with all the violations. Otherwise, a
     * fully valid instance is returned.
     * 
     * @return a fully validated instance, or throws ConstraintViolationException.
     * 
     */
    @Valid
    @NotNull
    public Entity build() {
        final var entity = requireNonNull(createInstance(), "createInstance() must not return null");
        entity.validate();
        return entity;
    }

    @SuppressWarnings("unchecked")
    protected final Builder self() {
        return (Builder) this;
    }

    /**
     * Sub‑classes implement this to actually create the immutable instance.
     */
    @NotNull
    protected abstract Entity createInstance();

}
