package org.reusablecomponent.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public abstract class AbstractEntity<Id> implements InterfaceEntity<Id, AbstractEntity<Id>> {

    protected Id id;
    
    protected LocalDateTime createdDate;
    
    protected LocalDateTime updatedDate;
    
    protected String createdReason;
    
    protected String updatedReason;
    
    // -------------------------------------------------------------------------- 
    
    protected AbstractEntity() {
	super();
	createdDate = LocalDateTime.now();
	updatedDate = createdDate;
    }
    
    // --------------------------------------------------------------------------
    
    @Override
    public Id getId() {
        return id;
    }

    protected void setId(final Id id) {
        this.id = id;
    }

    @Override
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    protected void setCreatedDate(@NotNull @FutureOrPresent final LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    protected void setUpdatedDate(@NotNull @FutureOrPresent final LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
    
    protected String getCreatedReason() {
        return createdReason;
    }

    protected void setCreatedReason(@NotBlank final String createdReason) {
        this.createdReason = createdReason;
    }

    protected String getUpdatedReason() {
        return updatedReason;
    }

    protected void setUpdatedReason(@NotBlank final String updatedReason) {
        this.updatedReason = updatedReason;
    } 
    
    // -------------------------------------------------------------------------
    
    public void update(@NotNull final AbstractEntity<Id> entitySource) {
	setUpdatedDate(LocalDateTime.now());
    }
    
    @Override
    public AbstractEntity<Id> createNewInstance() {
	return this;
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
