package org.reusablecomponents.base.core.domain;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.commons.lang3.ObjectUtils;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;

/**
 * Abstract base class for entities. Provides common functionality for
 * managing entity state and validation, and also provides a builder pattern for
 * creating immutable instances.
 */
@Valid
public class AbstractEntityWithBuilder<Id> extends AbstractEntity<Id> {

    /**
     * Constructor. Protected, since this is an abstract class, and should only be
     * called by sub‑classes.
     */
    protected AbstractEntityWithBuilder() {
        super();
    }

    /**
     * Constructor that takes a Builder as an argument. It is protected, since this
     * is an abstract class, and should only be called by sub‑classes.
     * 
     * @param builder the Builder instance to use for creating the entity. It must
     *                not be null.
     */
    protected AbstractEntityWithBuilder(
            final AbstractEntityBuilder<Id, ? extends AbstractEntityWithBuilder<Id>, ? extends AbstractEntityBuilder<?, ?, ?>> builder) {
        requireNonNull(builder, "builder must not be null");
        this.createdDate = builder.createdDate;
        this.createdReason = builder.createdReason;
    }

    /**
     * Abstract base class for entity builders. Provides common functionality for
     * building and validating entities.
     */
    public static abstract class AbstractEntityBuilder<Id, Entity extends AbstractEntityWithBuilder<Id>, Builder extends AbstractEntityBuilder<Id, Entity, Builder>> {

        /**
         * Created reason is required, since it is a non‑null field in the entity. It
         * can be set to an empty string if there is no specific reason, but it cannot
         * be null.
         */
        public String createdReason;

        /**
         * Created date is required, since it is a non‑null field in the entity. It can
         * be set to the current date and time when the entity is built, but it cannot
         * be null. It is protected, since it should only be accessed by sub‑classes,
         * and not by external code.
         */
        public LocalDateTime createdDate;

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
            final var self = self();
            requireNonNull(function, "function must not be null").accept(self);
            return self;
        }

        /**
         * Builds the entity, and validates it. If the entity is invalid, a
         * ConstraintViolationException is thrown, with all the violations. Otherwise, a
         * fully valid instance is returned.
         * 
         * @return a fully validated instance, or throws ConstraintViolationException.
         */
        @Valid
        @NotNull
        public Entity build() {
            final var entity = requireNonNull(createInstance(), "createInstance() must not return null");
            createdDate = ObjectUtils.getIfNull(createdDate, LocalDateTime.now());
            createdReason = ObjectUtils.getIfNull(createdReason, "Initial creation");
            validate();
            return entity;
        }

        /**
         * Returns the Builder instance itself, allowing for method chaining.
         * 
         * @return the Builder instance itself.
         */
        @SuppressWarnings("unchecked")
        protected final Builder self() {
            return (Builder) this;
        }

        /**
         * Validates the given entity using the provided Validator, if any.
         * 
         * @param entity the entity to validate
         * @throws ConstraintViolationException if the entity is invalid
         */
        protected void validate() {
            final var violations = getValidator()
                    .map(validator -> validator.validate(this))
                    .orElseGet(Set::of);

            if (ObjectUtils.isNotEmpty(violations)) {
                throw new ConstraintViolationException(violations);
            }
        }

        /**
         * Sub‑classes implement this to actually create the immutable instance.
         */
        @NotNull
        protected abstract Entity createInstance();

        /**
         * Sub‑classes can override this to provide a Validator, if they want the
         * entity to be validated. If they don't override, the default is no validation.
         * 
         * @return an Optional containing the Validator, or empty if no validation is
         *         desired.
         */
        protected abstract Optional<Validator> getValidator();

    }
}
