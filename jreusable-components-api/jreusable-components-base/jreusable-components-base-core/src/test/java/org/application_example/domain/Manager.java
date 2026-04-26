package org.application_example.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("createdDate", createdDate)
                .append("createdReason", createdReason)
                .append("updatedDate", updatedDate)
                .append("updatedReason", updatedReason)
                .append("name", name)
                .append("department", department)
                .toString();
    }
}
