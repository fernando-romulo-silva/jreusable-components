package org.reusablecomponent.core.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.validation.Valid;

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
	return Objects.hash(this.getId());
    }

    @Override
    public boolean equals(final Object obj) {
	
	final boolean result;
	
	if (Objects.isNull(obj)) {
	    result = false;
	    		   
	} else if (this == obj) {
	    result = true;
	    
	} else if (obj instanceof AbstractEntity<?> other) {
	    result = Objects.equals(this.getId(), other.getId());
	    
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
