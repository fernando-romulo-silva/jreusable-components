package org.application_example.application;

import java.util.List;

import org.application_example.domain.Department;

public class DepartmentFacade extends EntityCommandFacadeDummy<Department, String> {

    public DepartmentFacade(final List<Department> data) {
	super(data);
    }
    
    @Override
    protected Department preSave(final Department saveEntityIn) {
	saveEntityIn.increaseOperation();
        return saveEntityIn;
    }
}
