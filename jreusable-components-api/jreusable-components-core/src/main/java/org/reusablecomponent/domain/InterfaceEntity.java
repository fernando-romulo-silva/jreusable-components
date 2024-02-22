package org.reusablecomponent.domain;

import java.time.LocalDateTime;

public interface InterfaceEntity <Id, Entity extends InterfaceEntity<Id, Entity>> extends InterfaceObject {
    
    Id getId();
    
    LocalDateTime getCreatedDate();
    
    LocalDateTime getUpdatedDate();
    
    Entity createNewInstance();
    
    void update(final Entity entitySource);
    
    default boolean isPublishable() {
	return true;
    }
    
    default String getRealmId() {
	return "";
    }
}
