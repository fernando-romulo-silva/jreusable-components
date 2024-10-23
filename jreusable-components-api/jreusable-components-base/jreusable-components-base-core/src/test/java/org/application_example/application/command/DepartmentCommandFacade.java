package org.application_example.application.command;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.application_example.domain.Department;

public class DepartmentCommandFacade extends EntityCommandFacadeList<Department, String> {

    public DepartmentCommandFacade(final List<Department> data) {
        super(data);
    }

    @Override
    protected Department preSave(final Department saveEntityIn, final Object... directives) {

        if (ObjectUtils.allNull(saveEntityIn)) {
            return saveEntityIn;
        }

        saveEntityIn.increaseOperation();
        return saveEntityIn;
    }
}
