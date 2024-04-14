package org.reusablecomponent.core.domain;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.time.LocalDateTime;
import java.util.Optional;

public interface InterfaceEntity <Id, Entity extends InterfaceEntity<Id, Entity>> {
    
    Id getId();
    
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
