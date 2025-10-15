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
import org.reusablecomponents.base.core.infra.util.function.compose.ComposeFunction2Args;
import org.reusablecomponents.base.core.infra.util.function.compose.ComposeFunction3Args;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction4Args;
import org.reusablecomponents.base.core.infra.util.operation.InterfaceOperation;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;

abstract class AbstractBaseFacadeTest {

    protected final InterfaceI18nService i18nService = (code, params) -> "translated!";
    protected final InterfaceSecurityService interfaceSecurityService = new DummySecurityService();
    protected final InterfaceExceptionAdapterService exceptionTranslatorService = new DefaultExceptionAdapterService();

    protected final TestEntiyBaseFacade facade = new TestEntiyBaseFacade();
    protected final TestOperation operation = new TestOperation();

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

    protected List<ComposeFunction2Args<Department>> getBiFunctions() {
        final var function01 = new ComposeFunction2Args<Department>() {
            @Override
            public Department apply(final Department department, final Object[] directives) {
                final var name = department.getName();
                final var sector = department.getSector();
                department.update(name + " function 01", sector);
                return department;
            }

            @Override
            public boolean isActice() {
                return false;
            }

            @Override
            public String getName() {
                return "Function 01";
            }
        };

        final var function02 = new ComposeFunction2Args<Department>() {
            @Override
            public Department apply(final Department department, final Object[] directives) {
                final var name = department.getName();
                final var sector = department.getSector();
                department.update(name + " function 02", sector);
                return department;
            }
        };

        final var function03 = new ComposeFunction2Args<Department>() {
            @Override
            public Department apply(final Department department, final Object[] directives) {
                throw new IllegalArgumentException("Some error");
            }
        };

        final var function04 = new ComposeFunction2Args<Department>() {
            @Override
            public Department apply(final Department department, final Object[] directives) {
                final var name = department.getName();
                final var sector = department.getSector();
                department.update(name + " function 04", sector);
                return department;
            }
        };

        return List.<ComposeFunction2Args<Department>>of(function01, function02, function03, function04);
    }

    protected List<ComposeFunction3Args<Exception, Department>> getTriFunctions() {
        final var function01 = new ComposeFunction3Args<Exception, Department>() {
            @Override
            public Exception apply(
                    final Exception exception,
                    final Department department,
                    final Object[] directives) {
                return new IllegalAccessException("Illegal Access Exception");
            }

            @Override
            public boolean isActice() {
                return false;
            }

            @Override
            public String getName() {
                return "Function 01";
            }
        };

        final ComposeFunction3Args<Exception, Department> function02 = (exception, department,
                directives) -> new IllegalStateException("Illegal State Exception");

        final var function03 = new ComposeFunction3Args<Exception, Department>() {
            @Override
            public Exception apply(final Exception exception, final Department department, final Object[] directives) {
                throw new IllegalArgumentException("Some error");
            }
        };

        final var function04 = new ComposeFunction3Args<Exception, Department>() {
            @Override
            public Exception apply(final Exception exception, final Department department, final Object[] directives) {
                return new IllegalStateException("Illegal State Exception");
            }
        };

        return List.<ComposeFunction3Args<Exception, Department>>of(function01, function02, function03, function04);
    }

    protected static class TestOperation implements InterfaceOperation {
    }

    protected record DepartmenDto(String departmentName, String managerName) {
    }
}
