package org.application_example.domain;

import java.time.LocalDateTime;

import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class Department extends AbstractEntity<String> {

    // ------------------- fields

    @NotEmpty
    private String name;

    @NotEmpty
    private String sector;
    
    @Min(0)
    @Max(1000)
    public Integer operation = 0;

    // ------------------- constructors

    Department() {
	super();
    }

    public Department(final String id, final String name, final String sector) {
	super();
	
	this.id = id;
	this.name = name;
	this.sector = sector;
    }
    
    // -------------------- update

    public void update(@NotEmpty final String name, @NotEmpty final String sector) {
	this.updatedDate = LocalDateTime.now();
	this.updatedReason = "update";

	this.name = name;
	this.sector = sector;
    }
    
    public void increaseOperation() {
	operation++;
    }

    // -------------------- getters
    
    @NotEmpty
    public String getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public String getSector() {
	return sector;
    }
    
    public Integer getOperation() {
        return operation;
    }
}
