package org.application_example.application.command;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.application_example.domain.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepartmentCommandFacade extends EntityCommandFacadeList<Department, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentCommandFacade.class);

    public DepartmentCommandFacade(final List<Department> data) {
        super(data);
    }
    /*
     * @Override
     * protected Department preSave(final Department saveEntityIn, final Object...
     * directives) {
     * 
     * super.preSave(saveEntityIn, directives);
     * 
     * if (ObjectUtils.allNull(saveEntityIn)) {
     * return saveEntityIn;
     * }
     * 
     * saveEntityIn.increaseOperation();
     * return saveEntityIn;
     * }
     * 
     * @Override
     * protected Department posSave(final Department saveEntityIn, final Object...
     * directives) {
     * 
     * super.posSave(saveEntityIn, directives);
     * 
     * if (ObjectUtils.allNull(saveEntityIn)) {
     * return saveEntityIn;
     * }
     * 
     * saveEntityIn.increaseOperation();
     * return saveEntityIn;
     * }
     * 
     * @Override
     * protected Exception errorSave(
     * final Department saveEntityIn,
     * final Exception exception,
     * final Object... directives) {
     * 
     * super.errorSave(saveEntityIn, exception, directives);
     * 
     * LOGGER.debug("Error Save");
     * 
     * return exception;
     * }
     */
}
