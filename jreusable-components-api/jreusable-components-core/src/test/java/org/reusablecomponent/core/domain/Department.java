package org.reusablecomponent.core.domain;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;

public class Department extends AbstractEntity<String> {

    // ------------------- fields

    @NotEmpty
    private String name;

    @NotEmpty
    private String sector;

    // ------------------- constructors

    Department() {
	super();
    }

    public Department(@NotEmpty final String id, @NotEmpty final String name, @NotEmpty final String sector) {
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
}
