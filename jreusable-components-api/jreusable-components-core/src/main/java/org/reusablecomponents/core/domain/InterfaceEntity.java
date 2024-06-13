package org.reusablecomponents.core.domain;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.time.LocalDateTime;
import java.util.Optional;

import jakarta.validation.constraints.NotNull;

public interface InterfaceEntity <Id, Entity extends InterfaceEntity<Id, Entity>> {
    
    Id getId();
    
    @NotNull
    LocalDateTime getCreatedDate();
    
    Optional<LocalDateTime> getUpdatedDate();
    
    Optional<String> getCreatedReason();
    
    Optional<String> getUpdatedReason();
    
    default boolean isPublishable() {
	return true;
    }
    
    default String getRealmId() {
	return EMPTY;
    }
}
