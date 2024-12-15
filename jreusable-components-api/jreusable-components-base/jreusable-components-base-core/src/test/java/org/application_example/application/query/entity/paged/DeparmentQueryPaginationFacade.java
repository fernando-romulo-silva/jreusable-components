package org.application_example.application.query.entity.paged;

import java.util.List;

import org.application_example.domain.Department;

public class DeparmentQueryPaginationFacade extends EntityQueryPaginationFacadeList<Department, String> {

    public DeparmentQueryPaginationFacade(final List<Department> data) {
        super(data);
    }
}
