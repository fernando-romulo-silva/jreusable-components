package org.application_example.application.query.entity.nonpaged;

import java.util.List;

import org.application_example.domain.Department;

public class DeparmentQueryFacade extends EntityQueryFacadeList<Department, String> {

    public DeparmentQueryFacade(final List<Department> data) {
        super(data);
    }

}
