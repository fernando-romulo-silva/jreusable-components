package org.reusablecomponents.base.core.application.query.entity.simple;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.ArrayList;
import java.util.List;

import org.application_example.application.query.entity.nonpaged.DeparmentQueryFacade;
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
@DisplayName("Test the EntityQueryFacade entity test, happy Path :) ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@SuppressWarnings("null")
class QueryFacadeHappyPathTest {

    final List<Department> defaultData = new ArrayList<>();
    final DeparmentQueryFacade defaultQueryFacade = new DeparmentQueryFacade(defaultData);

    Department department01;
    Department department02;
    Department department03;

    Manager manager;

    @BeforeEach
    void setUp() {
        defaultData.clear();

        manager = new Manager("x2", "Business Happy");

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
    @DisplayName("Find by id test")
    void findByIdTest() {
        // given
        final var id = "x1";

        // when
        final var result = defaultQueryFacade.findById(id);

        // then
        assertThat(department01).isEqualTo(result);
        assertThat(defaultData).contains(result);
        assertThat(result).extracting("operation").isEqualTo(0);
    }

    @Test
    @Order(2)
    @DisplayName("Find all test")
    void findAllTest() {
        // given
        final var currentData = defaultData;

        // when
        final var result = defaultQueryFacade.findAll();

        // then
        assertThat(currentData).containsAll(result);
    }

    @Test
    @Order(3)
    @DisplayName("Count all test")
    void countAllTest() {
        // given
        final var currentSize = defaultData.size();

        // when
        final var result = defaultQueryFacade.countAll().intValue();

        // then
        assertThat(currentSize).isEqualTo(result);
    }

    @Test
    @Order(4)
    @DisplayName("Exists all test")
    void existsAllTest() {
        // given
        final var existsAll = defaultData.size() > 0;

        // when
        final var result = defaultQueryFacade.existsAll();

        // then
        assertThat(existsAll)
                .isEqualTo(result)
                .matches(e -> {
                    defaultData.clear();
                    return defaultQueryFacade.existsAll() != existsAll;
                });
    }

    @Test
    @Order(5)
    @DisplayName("Exists by id test")
    void existsByIdTest() {
        // given
        final var id = "x1";

        // when
        final var result = defaultQueryFacade.existsById(id);

        // then
        assertThat(result)
                .isTrue()
                .matches(e -> defaultQueryFacade.existsById("whatever") != result);
    }

}
