package org.reusablecomponents.base.core.domain;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.time.LocalDateTime;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

/**
 * Interface for entities. Provides common functionality for
 * managing entity state and validation.
 */
public interface InterfaceEntity<Id> {

    /**
     * Returns the unique identifier of the entity.
     *
     * @return the unique identifier
     */
    Id getId();

    /**
     * Returns the date and time when the entity was created.
     *
     * @return the creation date and time
     */
    @NotNull
    LocalDateTime getCreatedDate();

    /**
     * Returns the date and time when the entity was last updated.
     * If the entity has never been updated, this method returns an empty Optional.
     *
     * @return the last update date and time
     */
    default Optional<LocalDateTime> getUpdatedDate() {
        return Optional.empty();
    }

    /**
     * Returns the reason why the entity was created.
     *
     * @return the creation reason
     */
    @NotNull
    default String getCreatedReason() {
        return "Initial creation";
    }

    /**
     * Returns the reason why the entity was last updated.
     *
     * @return the last update reason
     */
    default Optional<String> getUpdatedReason() {
        return Optional.empty();
    }

    /**
     * Returns true if the entity is publishable, false otherwise.
     * By default, all entities are publishable, but this can be overridden by
     * sub‑classes if they want to be non‑publishable.
     *
     * @return true if the entity is publishable, false otherwise
     */
    default boolean isPublishable() {
        return true;
    }

    /**
     * Returns the realm ID of the entity. By default, this is an empty string,
     * but it can be overridden by sub‑classes if they want to provide a specific
     * realm ID.
     *
     * @return the realm ID
     */
    default String getRealmId() {
        return EMPTY;
    }

    /**
     * Returns the type of the entity. By default, this is the simple class name,
     * but it can be overridden by sub‑classes if they want to provide a specific
     * type.
     *
     * @return the type of the entity
     */
    default String getType() {
        return this.getClass().getSimpleName();
    }
}
