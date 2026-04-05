package org.reusablecomponents.base.core.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;

/**
 * Abstract base class for entities. Provides common functionality for
 * managing entity state and validation.
 */
@Valid
public abstract class AbstractEntity<Id> implements InterfaceEntity<Id, AbstractEntity<Id>> {

    protected Id id;

    @NotNull
    protected LocalDateTime createdDate;

    @NotNull
    protected String createdReason;

    protected LocalDateTime updatedDate;

    protected String updatedReason;

    // --------------------------------------------------------------------------

    /**
     * Constructor. Protected, since this is an abstract class, and should only be
     * called by sub‑classes.
     */
    protected AbstractEntity() {
        super();
        createdDate = LocalDateTime.now();
        createdReason = "Initial creation";
    }

    // --------------------------------------------------------------------------

    /**
     * Sub‑classes can override this to provide a Validator, if they want the
     * entity to be validated. If they don't override, the default is no validation.
     * 
     * @return an Optional containing the Validator, or empty if no validation is
     *         desired.
     */
    protected Optional<Validator> getValidator() {
        return Optional.empty();
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

    // --------------------------------------------------------------------------

    /**
     * Returns the unique identifier of the entity.
     *
     * @return the unique identifier
     */
    @Override
    public Id getId() {
        return id;
    }

    /**
     * Returns the date and time when the entity was created.
     *
     * @return the creation date and time
     */
    @Override
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * Returns the reason why the entity was created.
     *
     * @return the creation reason
     */

    @Override
    public String getCreatedReason() {
        return createdReason;
    }

    /**
     * Returns the date and time when the entity was last updated.
     *
     * @return the last update date and time
     */

    @Override
    public Optional<LocalDateTime> getUpdatedDate() {
        return Optional.ofNullable(updatedDate);
    }

    /**
     * Returns the reason why the entity was last updated.
     *
     * @return the last update reason
     */
    @Override
    public Optional<String> getUpdatedReason() {
        return Optional.ofNullable(updatedReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(final Object obj) {

        final boolean result;

        if (Objects.isNull(obj)) {
            result = false;

        } else if (this == obj) {
            result = true;

        } else if (obj instanceof AbstractEntity<?> other) {
            result = Objects.equals(this.id, other.id);

        } else {
            result = false;
        }

        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
