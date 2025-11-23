package org.reusablecomponents.base.core.application.query.entity.specification;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang3.math.NumberUtils;
import org.application_example.application.query.entity.nonpaged.DeparmentQuerySpecificationFacade;
import org.application_example.domain.Department;
import org.application_example.domain.Manager;
import org.junit.jupiter.api.AfterAll;
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

@Tag("unit")
@DisplayName("Test the EntityQuerySpecificationFacade entity test, happy Path :)")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class QuerySpecificationFacadeHappyPathTest {

    final List<Department> defaultData = new ArrayList<>();
    final DeparmentQuerySpecificationFacade defaultQueryFacade = new DeparmentQuerySpecificationFacade(defaultData);

    Department department01;
    Department department02;
    Department department03;

    Manager manager;

    @BeforeEach
    void setUp() {
        defaultData.clear();

        manager = new Manager("x2", "Business Manager");

        department01 = new Department("x1", "Default 01", "Peopple", manager);
        department02 = new Department("x2", "Default 02", "Resource", manager);
        department03 = new Department("x3", "Default 03", "IT", manager);

        defaultData.addAll(List.of(department01, department02, department03));
    }

    @AfterAll
    void tearDown() {
        // No requirements yet
    }

    @Test
    @Order(1)
    @DisplayName("Find one by specification test")
    void findOneBySpecTest() {
        // given
        final Predicate<Department> spec = department -> equalsIgnoreCase(department.getName(), "Default 01");

        // when
        final var result = defaultQueryFacade.findOneBySpecification(spec);

        // then
        assertThat(department01).isEqualTo(result);
        assertThat(defaultData).contains(result);
        assertThat(result).extracting("operation").isEqualTo(0);
    }

    @Test
    @Order(2)
    @DisplayName("Find by specification test")
    void findBySpecTest() {
        // given
        final Predicate<Department> spec01 = department -> containsIgnoreCase(department.getName(), "Default");
        final Predicate<Department> spec02 = department -> equalsIgnoreCase(department.getName(), "Whatever");

        // when
        final var result = defaultQueryFacade.findBySpecification(spec01);

        // then
        assertThat(result)
                .containsAll(defaultData)
                .matches(e -> defaultQueryFacade.findBySpecification(spec02).isEmpty());
    }

    @Test
    @Order(3)
    @DisplayName("Count by specification test")
    void countAllTest() {
        // given
        final Predicate<Department> spec = department -> equalsIgnoreCase(department.getName(), "Default 01");

        // when
        final var result = defaultQueryFacade.countBySpecification(spec);

        // then
        assertThat(result).isEqualTo(NumberUtils.LONG_ONE);
    }

    @Test
    @Order(4)
    @DisplayName("Exists all test")
    void existsAllTest() {
        // given
        final Predicate<Department> spec01 = department -> equalsIgnoreCase(department.getName(), "Default 01");
        final Predicate<Department> spec02 = department -> equalsIgnoreCase(department.getName(), "Whatever");

        final var existsAll = defaultData.size() > 0;

        // when
        final var result = defaultQueryFacade.existsBySpecification(spec01);

        // then
        assertThat(existsAll)
                .isEqualTo(result)
                .matches(e -> defaultQueryFacade.existsBySpecification(spec02) != existsAll);
    }
}
