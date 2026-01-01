package org.reusablecomponents.base.core.application.query.entity.pagination;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.application_example.application.query.entity.paged.DeparmentQueryPaginationFacade;
import org.application_example.application.query.entity.paged.PageList;
import org.application_example.domain.Department;
import org.application_example.domain.Manager;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reusablecomponents.base.core.infra.exception.common.UnexpectedException;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.executable.ExecutableValidator;

@Tag("unit")
@DisplayName("Test the EntityQueryFacade entity test, unhappy path :( ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@SuppressWarnings("null")
class QueryPaginationFacadeUnhappyPathTest {

    static final ResourceBundleMessageInterpolator INTERPOLATOR = new ResourceBundleMessageInterpolator(
            new AggregateResourceBundleLocator(Arrays.asList("ValidationMessages")));

    static final ValidatorFactory VALIDATOR_FACTORY = Validation
            .byDefaultProvider()
            .configure()
            // .messageInterpolator(new ParameterMessageInterpolator())
            .messageInterpolator(INTERPOLATOR)
            .buildValidatorFactory();

    static final Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();

    static final ExecutableValidator EXECUTABLE_VALIDATOR = VALIDATOR.forExecutables();

    final List<Department> defaultData = new ArrayList<>();
    final DeparmentQueryPaginationFacade defaultQueryFacade = new DeparmentQueryPaginationFacade(defaultData);

    Department department01;
    Department department02;

    Manager manager;

    @BeforeAll
    void setUpAll() {
        manager = new Manager("x2", "Business Happy");
    }

    @BeforeEach
    void setUp() {
        defaultData.clear();

        department01 = new Department("x1", "Default 01", "Peopple", manager);
        department02 = new Department("x2", "Default 02", "Resource", manager);

        defaultData.addAll(List.of(department01, department02));
    }

    @AfterAll
    void tearDown() {
        // No requirements yet
        VALIDATOR_FACTORY.close();
    }

    @Test
    @Order(1)
    @DisplayName("Find one sorted with null sort")
    void findOneSortedTest() {

        // given
        final Comparator<Department> sort = null;

        // when
        assertThatThrownBy(() -> defaultQueryFacade.findOneSorted(sort))
                // then
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Please pass a non-null '%s'", "in");
    }

    @Test
    @Order(2)
    @DisplayName("Find one sorted with unexpected error test")
    void findOneSortedWithUnexpectedErrorTest() {

        // given
        final var directives = new Object[] { "error" };
        final var sort = (Comparator<Department>) Comparator.comparing(Department::getName).reversed();

        // when
        assertThatThrownBy(() -> defaultQueryFacade.findOneSorted(sort, directives))
                // then
                .isInstanceOf(UnexpectedException.class)
                .hasMessageContaining("Unexpecte error happened");
    }

    @Test
    @Order(3)
    @DisplayName("Find all test with null pageable test")
    void findAllWitNullPageableTest() {

        // given
        final PageList<Department> pageable = null;

        // when
        assertThatThrownBy(() -> defaultQueryFacade.findAllPaged(pageable))
                // then
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Please pass a non-null '%s'", "in");
    }

    @Test
    @Order(4)
    @DisplayName("Find all test with unexpected error test")
    void findAllWithUnexpectedErrorTest() {

        // given
        final var directives = new Object[] { "error" };
        final var pageable = new PageList<>(5, 0, defaultData);

        // when
        assertThatThrownBy(() -> defaultQueryFacade.findAllPaged(pageable, directives))
                // then
                .isInstanceOf(UnexpectedException.class)
                .hasMessageContaining("Unexpecte error happened");
    }

}
