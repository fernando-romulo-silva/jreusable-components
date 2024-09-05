package org.reusablecomponents.base.core.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

@Valid
public abstract class AbstractEntity<Id> implements InterfaceEntity<Id, AbstractEntity<Id>> {

    protected Id id;

    protected LocalDateTime createdDate;

    protected String createdReason;

    protected LocalDateTime updatedDate;

    protected String updatedReason;

    // --------------------------------------------------------------------------

    protected AbstractEntity() {
        super();
        createdDate = LocalDateTime.now();
    }

    protected final AbstractEntity<Id> validade(final Validator validator) {

        checkNotNull(validator, "validator argument cannot be null");

        final var violations = validator.validate(this);

        if (ObjectUtils.isNotEmpty(violations)) {
            throw new ConstraintViolationException(violations);
        }

        return this;
    }

    // --------------------------------------------------------------------------

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public Optional<String> getCreatedReason() {
        return Optional.ofNullable(createdReason);
    }

    @Override
    public Optional<LocalDateTime> getUpdatedDate() {
        return Optional.ofNullable(updatedDate);
    }

    @Override
    public Optional<String> getUpdatedReason() {
        return Optional.ofNullable(updatedReason);
    }

    // --------------------------------------------------------------------------

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
