package org.application_example.application;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.application_example.domain.Department;

public class DepartmentFacade extends EntityCommandFacadeList<Department, String> {

    public DepartmentFacade(final List<Department> data) {
        super(data);
    }

    @Override
    protected Department preSave(final Department saveEntityIn) {

        if (ObjectUtils.allNull(saveEntityIn)) {
            return saveEntityIn;
        }

        saveEntityIn.increaseOperation();
        return saveEntityIn;
    }
}
