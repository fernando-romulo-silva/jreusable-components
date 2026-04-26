package org.reusablecomponents.base.core.domain;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

/**
 * Abstract base class for entities. Provides common functionality for
 * managing entity state and validation.
 */
@Valid
public abstract class AbstractEntity<Id> extends AbstractInternalEntity<Id> {

    /**
     * Constructor. Protected, since this is an abstract class, and should only be
     * called by sub‑classes.
     */
    protected AbstractEntity() {
        super();
        createdDate = LocalDateTime.now();
        createdReason = "Initial creation";
    }

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
     * 
     * @throws ConstraintViolationException if the entity is invalid
     */
    protected void validate() {
        final var violations = getValidator()
                .map(validator -> validator.validate(this))
                .orElseGet(Set::of);

        if (isNotEmpty(violations)) {
            throw new ConstraintViolationException(violations);
        }
    }
}
