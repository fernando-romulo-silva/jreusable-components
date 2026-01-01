package org.application_example.application.command;

import java.util.List;

import org.application_example.domain.Department;

public class DepartmentCommandFacade extends EntityCommandFacadeList<Department, String> {

    public DepartmentCommandFacade(final List<Department> data) {
        super(data);
    }
}
