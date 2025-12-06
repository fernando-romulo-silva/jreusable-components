package org.reusablecomponents.base.core.application.base;

import java.util.function.Consumer;

import org.application_example.application.TestEntiyBaseFacade;
import org.application_example.domain.Department;
import org.application_example.domain.Manager;
import org.application_example.infra.DummySecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.reusablecomponents.base.core.infra.exception.DefaultExceptionAdapterService;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.custom.CustomOperation1Args;
import org.reusablecomponents.base.core.infra.util.function.operation.custom.CustomOperation2Args;
import org.reusablecomponents.base.core.infra.util.function.operation.custom.CustomOperation3Args;
import org.reusablecomponents.base.core.infra.util.function.operation.custom.CustomOperation4Args;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;

abstract class AbstractBaseFacadeTest {

	protected static final String ERROR_MSG = "Error Msg";
	protected final InterfaceI18nService i18nService = (code, params) -> "translated!";
	protected final InterfaceSecurityService interfaceSecurityService = new DummySecurityService();
	protected final InterfaceExceptionAdapterService exceptionTranslatorService = new DefaultExceptionAdapterService();

	protected final TestEntiyBaseFacade facade = new TestEntiyBaseFacade();
	protected Manager manager01;
	protected Manager manager02;
	protected Department department01;
	protected Department department02;

	// --------------

	protected final Consumer<Object[]> preFunctionNoInput = System.out::println;

	// ---------------
	protected final CustomOperation1Args<Object[], Department> oneInputFunction01 = directives -> department01;

	protected final CustomOperation1Args<Department, Department> oneInputFunction02 = department -> department;

	protected final CustomOperation1Args<Object[], Object[]> oneInput03Function = directives -> directives;

	protected final CustomOperation1Args<Object[], Department> oneInputExceptionFunction01 = directives -> {
		throw new IllegalArgumentException(ERROR_MSG);
	};

	protected final CustomOperation1Args<Department, Department> oneInputExceptionFunction02 = department -> {
		throw new IllegalArgumentException(ERROR_MSG);
	};

	protected final CustomOperation1Args<Object[], Object[]> oneInputExceptionFunction03 = directives -> {
		throw new IllegalArgumentException(ERROR_MSG);
	};

	// ------------------------------------------------------

	protected final CustomOperation2Args<Department, Object[], Department> twoInputsFunction = (
			departmentIn, directives) -> departmentIn;

	protected final CustomOperation3Args<Department, Manager, Object[], Department> threeInputsFunction = (
			departmentIn, managerIn, directives) -> departmentIn;

	// ------------------------------------------------------

	protected final CustomOperation2Args<Department, Object[], Department> twoInputsExceptionFunction = (
			departmentIn, directives) -> {
		throw new IllegalArgumentException(ERROR_MSG);
	};

	protected final CustomOperation3Args<Department, Manager, Object[], Department> threeInputsExceptionFunction = (
			departmentIn, managerIn, directives) -> {
		throw new IllegalArgumentException(ERROR_MSG);
	};

	// ------------------------------------------------------

	protected final CustomOperation2Args<BaseException, Object[], BaseException> twoInputsErrorFunction = (
			exception, directives) -> exception;

	protected final CustomOperation3Args<BaseException, Department, Object[], BaseException> threeInputsErrorFunction = (
			exception, departmentIn, directives) -> exception;

	protected final CustomOperation4Args<BaseException, Department, Manager, Object[], BaseException> fourInputsErrorFunction = (
			exception, departmentIn, managerIn, directives) -> exception;

	// ------------------------------------------------------

	@BeforeEach
	void setUpEach() {
		manager01 = new Manager("x1", "Business Happy");
		manager02 = new Manager("x2", "Mister B");
		department01 = new Department("00001", "Development 01", "Technology", manager01);
		department02 = new Department("00002", "Development 02", "Technology", manager02);
	}
}
