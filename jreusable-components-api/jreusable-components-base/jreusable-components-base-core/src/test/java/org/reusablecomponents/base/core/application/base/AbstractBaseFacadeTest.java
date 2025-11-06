package org.reusablecomponents.base.core.application.base;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.apache.commons.lang3.function.TriFunction;
import org.application_example.application.TestEntiyBaseFacade;
import org.application_example.domain.Department;
import org.application_example.domain.Manager;
import org.application_example.infra.DummySecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.reusablecomponents.base.core.infra.exception.DefaultExceptionAdapterService;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction4Args;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;

abstract class AbstractBaseFacadeTest {

    protected final InterfaceI18nService i18nService = (code, params) -> "translated!";
    protected final InterfaceSecurityService interfaceSecurityService = new DummySecurityService();
    protected final InterfaceExceptionAdapterService exceptionTranslatorService = new DefaultExceptionAdapterService();

    protected final TestEntiyBaseFacade facade = new TestEntiyBaseFacade();
    protected Manager manager01;
    protected Manager manager02;
    protected Department department01;
    protected Department department02;

    // -------------- no input operations
    protected final Consumer<Object[]> preFunctionNoInput = System.out::println;

    protected final BiFunction<Department, Object[], Department> posFunctionNoInput = (
            department, directives) -> department;

    protected final BiFunction<Exception, Object[], Exception> errorFunctionNoInput = (
            exception, directives) -> exception;

    // --------------- one input operation
    protected final BiFunction<Department, Object[], Department> preFunctionOneInput = (
            departmentIn, directives) -> departmentIn;

    protected final BiFunction<Department, Object[], Department> posFunctionOneInput = (
            departmentOut, directives) -> departmentOut;

    protected final TriFunction<Department, Exception, Object[], Exception> errorFunctionOneInput = (
            departmentIn, exception, directives) -> exception;

    // ---------------- two inputs
    protected final TriFunction<Department, Manager, Object[], Entry<Department, Manager>> preFunctionTwoInputs = (
            departmentIn, managerIn, directives) -> new AbstractMap.SimpleEntry<>(department02, manager02);

    protected final BiFunction<DepartmenDto, Object[], DepartmenDto> posFunctionTwoInputs = (
            departmentDtoIn, directives) -> departmentDtoIn;

    protected final OperationFunction4Args<Department, Manager, Exception, Object[], Exception> errorFunctionTwoInputs = (
            departmentIn, managerIn, exception, directives) -> exception;

    // ------------------------------------------------------

    @BeforeEach
    void setUpEach() {
        manager01 = new Manager("x1", "Business Happy");
        manager02 = new Manager("x2", "Mister B");
        department01 = new Department("00001", "Development 01", "Technology", manager01);
        department02 = new Department("00002", "Development 02", "Technology", manager02);
    }

    protected record DepartmenDto(String departmentName, String managerName) {
    }
}
