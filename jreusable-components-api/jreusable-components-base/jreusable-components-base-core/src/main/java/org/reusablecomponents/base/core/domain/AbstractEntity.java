package org.reusablecomponents.base.core.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Abstract base class for entities. Provides common functionality for managing
 * entity state and validation.
 */
@Valid
public abstract class AbstractEntity<Id> implements InterfaceEntity<Id> {

    /**
     * Unique identifier of the entity. It is protected, since it should only be
     * accessed by sub‑classes, and not by external code. It is also not final,
     * since it may be set by frameworks like JPA, which require a no‑arg
     * constructor and a setter for the ID field.
     */
    protected Id id;

    /**
     * Date and time when the entity was created. It is protected, since it should
     * only be accessed by sub‑classes, and not by external code.
     */
    @NotNull
    protected LocalDateTime createdDate;

    /**
     * Reason why the entity was created. It is protected, since it should only be
     * accessed by sub‑classes, and not by external code.
     */
    @NotNull
    protected String createdReason;

    /**
     * Date and time when the entity was last updated. It is protected, since it
     * should only be accessed by sub‑classes, and not by external code.
     */
    protected LocalDateTime updatedDate;

    /**
     * Reason why the entity was last updated. It is protected, since it should only
     * be accessed by sub‑classes, and not by external code.
     */
    protected String updatedReason;

    /**
     * {@inheritDoc}
     */
    @Override
    public Id getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCreatedReason() {
        return createdReason;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<LocalDateTime> getUpdatedDate() {
        return Optional.ofNullable(updatedDate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> getUpdatedReason() {
        return Optional.ofNullable(updatedReason);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final var other = (AbstractEntity<?>) obj;

        return id != null && Objects.equals(this.id, other.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        // TODO: Consider using a more concise ToStringStyle customizing the output
        return ToStringBuilder.reflectionToString(this);
        /*
         * return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
         * .append("id", id)
         * .append("createdDate", createdDate)
         * .append("createdReason", createdReason)
         * .append("updatedDate", updatedDate)
         * .append("updatedReason", updatedReason)
         * .toString();
         */
    }
}
