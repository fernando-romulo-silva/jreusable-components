package org.reusablecomponents.base.core.domain;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.time.LocalDateTime;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

/**
 * Interface for entities. Provides common functionality for
 * managing entity state and validation.
 */
public interface InterfaceEntity<Id, Entity extends InterfaceEntity<Id, Entity>> {

    Id getId();

    @NotNull
    LocalDateTime getCreatedDate();

    Optional<LocalDateTime> getUpdatedDate();

    @NotNull
    String getCreatedReason();

    Optional<String> getUpdatedReason();

    default boolean isPublishable() {
        return true;
    }

    default String getRealmId() {
        return EMPTY;
    }
}
