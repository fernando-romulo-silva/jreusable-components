package org.application_example.domain;

import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.application_example.infra.Utils;
import org.reusablecomponents.base.core.domain.AbstractEntity;

import jakarta.validation.Validator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Project extends AbstractEntity<Long> {

    @NotEmpty
    private String name;

    private Department department;

    Project() {
        super();
    }

    public Project(final Long id, final String name, final Department department) {
        super();

        this.id = id;
        this.name = name;
        this.department = department;

        validate();
    }

    @Override
    protected Optional<Validator> getValidator() {
        return Optional.of(Utils.VALIDATOR);
    }

    public boolean isPublishable() {
        return false;
    }

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
