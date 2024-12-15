package org.application_example.application.query.entity.nonpaged;

import java.util.List;

import org.application_example.domain.Department;

public class DeparmentQuerySpecificationFacade extends EntityQuerySpecificationFacadeList<Department, String> {

    public DeparmentQuerySpecificationFacade(final List<Department> data) {
        super(data);
    }
}
