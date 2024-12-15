package org.application_example.application.query.entity.paged;

import java.util.List;

import org.application_example.domain.Department;

public class DeparmentQueryPaginationSpecificationFacade
        extends EntityQueryPaginationSpecificationFacadeList<Department, String> {

    public DeparmentQueryPaginationSpecificationFacade(final List<Department> data) {
        super(data);
    }
}
