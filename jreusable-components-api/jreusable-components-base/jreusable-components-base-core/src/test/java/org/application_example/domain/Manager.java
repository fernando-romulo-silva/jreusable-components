package org.application_example.domain;

import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.validation.constraints.NotEmpty;

public class Manager extends AbstractEntity<String> {

    @NotEmpty
    private String name;

    private Department department;

    Manager() {
        super();
    }

    public Manager(final String id, final String name) {
        super();

        this.id = id;
        this.name = name;
    }

    void selectDepartment(final Department department) {
        this.department = department;
    }

    void clearDepartment() {
        this.department = null;
    }

    public String getName() {
        return name;
    }

    public Department getDepartment() {
        return department;
    }
}
