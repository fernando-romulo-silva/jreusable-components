package org.reusablecomponent.core.application.base;

import static java.util.Objects.nonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reusablecomponent.core.domain.Department;

import jakarta.validation.Validation;

@Tag("unit")
@DisplayName("Test the AbstractEntiyBaseFacade entity test, unhappy Path :( ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class AbstractEntiyBaseFacadeUnhappyPathTest {
    
    Stream<Arguments> checkEntityExistsMethodData() {
	
	final Predicate<Department> existsEntityFunction = (dep) -> nonNull(dep);
	final var department = new Department("asfdlkd1", "Dep1", "Account");
	
	// given
	return Stream.of(
			Arguments.of(null                 , department),
			Arguments.of(existsEntityFunction , null      )
	);
	
    }
    
    @ParameterizedTest(name = "Pos {index} : existsEntityFunction ''{0}'', department ''{1}''")
    @MethodSource("checkEntityExistsMethodData")
    @Order(1)
    @DisplayName("Test check entity exists method")
    void checkEntityExistsMethodTest(final Predicate<Department> existsEntityFunction, final Department department) throws NoSuchMethodException, SecurityException {

	final var facade = new TestEntiyBaseFacade();

	final var method = TestEntiyBaseFacade.class.getSuperclass().getDeclaredMethod("checkEntityExists", Predicate.class, Object.class);
	method.setAccessible(true);
	
	final Object[] parameterValues = { existsEntityFunction, department };

	final var factory = Validation.buildDefaultValidatorFactory();
	final var executableValidator = factory.getValidator().forExecutables();

	// when
	final var violations = executableValidator.validateParameters(facade, method, parameterValues);
	
	// then
	assertThat(violations).hasSize(1);
    }

}
