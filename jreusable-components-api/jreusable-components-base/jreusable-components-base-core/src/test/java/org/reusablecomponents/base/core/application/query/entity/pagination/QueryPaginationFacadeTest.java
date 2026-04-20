package org.reusablecomponents.base.core.application.query.entity.pagination;

import static org.apache.commons.lang3.StringUtils.leftPad;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
@DisplayName("Test the QueryPaginationFacade entity test")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@SuppressWarnings("null")
class QueryPaginationFacadeTest {

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
    Department department03;

    Manager manager;

    @BeforeAll
    void setUpAll() {
        manager = new Manager("x2", "Business Happy");
    }

    @BeforeEach
    void setUp() {
        defaultData.clear();

        manager = new Manager("x2", "Business Happy");

        department01 = new Department("x1", "Default 01", "Peopple", manager);
        department02 = new Department("x2", "Default 02", "Resource", manager);
        department03 = new Department("x3", "Default 03", "IT", manager);

        defaultData.addAll(List.of(department01, department02, department03));

        var i = 3;
        var total = 20;

        while (i++ < total) {
            final var counterString = leftPad("" + i, 2, "0");
            defaultData.add(new Department("x" + i, "Default " + counterString, "Sector " + counterString, manager));
        }
    }

    @AfterAll
    void tearDown() {
        // No requirements yet
        VALIDATOR_FACTORY.close();
    }

    @Test
    @Order(1)
    @DisplayName("Given sort object when find one sorted then return sorted result test")
    void givenSortObject_whenFindOneSorted_thenReturnSortedResult() {
        // given
        final var sort = (Comparator<Department>) Comparator.comparing(Department::getName).reversed();

        // when
        final var result = assertDoesNotThrow(() -> defaultQueryFacade.findOneSorted(sort));

        // then
        assertThat(defaultData.getLast()).isEqualTo(result);
        assertThat(defaultData).contains(result);
    }

    @Test
    @Order(2)
    @DisplayName("Given pageable object when find all paged then return paged result test")
    void givenPageableObject_whenFindAllPaged_thenReturnPagedResult() {
        // given
        final var pageable = new PageList<>(5, 0, defaultData);
        final var departmentInsidePage = defaultData.get(2);
        final var departmentOusidePage = defaultData.get(5);

        // when
        final var result = assertDoesNotThrow(() -> defaultQueryFacade.findAllPaged(pageable));

        // then
        assertThat(result)
                .size().isEqualTo(5)
                .returnToIterable()
                .contains(departmentInsidePage)
                .doesNotContain(departmentOusidePage);
    }

    @Test
    @Order(3)
    @DisplayName("Given null sort object when find one sorted then throw NullPointerException test")
    void givenNullSortObject_whenFindOneSorted_thenThrowNullPointerException() {

        // given
        final Comparator<Department> sort = null;

        // when
        assertThatThrownBy(() -> defaultQueryFacade.findOneSorted(sort))
                // then
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Please pass a non-null '%s'", "in");
    }

    @Test
    @Order(4)
    @DisplayName("Given sort object when find one sorted with unexpected error then throw UnexpectedException test")
    void givenSortObject_whenFindOneSortedWithUnexpectedError_thenThrowUnexpectedException() {

        // given
        final var directives = new Object[] { "error" };
        final var sort = (Comparator<Department>) Comparator.comparing(Department::getName).reversed();

        // when
        assertThatThrownBy(() -> defaultQueryFacade.findOneSorted(sort, directives))
                // then
                .isInstanceOf(UnexpectedException.class)
                .hasMessageContaining("Unexpected error happened");
    }

    @Test
    @Order(5)
    @DisplayName("Given null pageable object when find all paged then throw NullPointerException test")
    void givenNullPageableObject_whenFindAllPaged_thenThrowNullPointerException() {

        // given
        final PageList<Department> pageable = null;

        // when
        assertThatThrownBy(() -> defaultQueryFacade.findAllPaged(pageable))
                // then
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Please pass a non-null '%s'", "in");
    }

    @Test
    @Order(6)
    @DisplayName("Given pageable object when find all paged with unexpected error then throw UnexpectedException test")
    void givenPageableObject_whenFindAllPagedWithUnexpectedError_thenThrowUnexpectedException() {

        // given
        final var directives = new Object[] { "error" };
        final var pageable = new PageList<>(5, 0, defaultData);

        // when
        assertThatThrownBy(() -> defaultQueryFacade.findAllPaged(pageable, directives))
                // then
                .isInstanceOf(UnexpectedException.class)
                .hasMessageContaining("Unexpected error happened");
    }
}
