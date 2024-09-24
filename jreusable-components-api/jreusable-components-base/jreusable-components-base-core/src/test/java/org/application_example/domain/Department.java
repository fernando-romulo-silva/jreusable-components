package org.application_example.domain;

import java.time.LocalDateTime;
import java.util.Objects;

import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Department extends AbstractEntity<String> {

    // ------------------- fields

    @NotEmpty
    private String name;

    @NotEmpty
    private String sector;

    @NotNull
    private Manager manager;

    @Min(0)
    @Max(1000)
    public Integer operation = 0;

    // ------------------- constructors

    Department() {
        super();
    }

    public Department(final String id, final String name, final String sector, final Manager manager) {
        super();

        this.id = id;
        this.name = name;
        this.sector = sector;
        this.manager = manager;

        if (Objects.nonNull(manager)) {
            this.manager.selectDepartment(this);
        }
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

    public Manager getManager() {
        return manager;
    }

    public void removeManager() {

        if (Objects.nonNull(manager)) {
            manager.clearDepartment();
        }

        manager = null;
    }
}
