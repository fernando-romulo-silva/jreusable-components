package org.application_example.domain;

import static org.reusablecomponents.base.core.util.AbstractValidatorTest.VALIDATOR;

import java.util.Optional;

import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Project extends AbstractEntity<Long> {

    // ------------------- fields

    @NotEmpty
    private String name;

    private Department department;

    // ------------------- constructors

    Project() {
        super();
    }

    public Project(final Long id, final String name, final Department department) {
        super();

        this.id = id;
        this.name = name;
        this.department = department;

        validade(VALIDATOR);
    }

    public boolean isPublishable() {
        return false;
    }

    // -------------------- getters

    @NotNull
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Optional<Department> getDepartment() {
        return Optional.ofNullable(department);
    }
}
