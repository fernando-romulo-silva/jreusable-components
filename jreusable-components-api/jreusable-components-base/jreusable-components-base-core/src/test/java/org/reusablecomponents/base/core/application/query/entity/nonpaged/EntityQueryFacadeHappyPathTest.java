package org.reusablecomponents.base.core.application.query.entity.nonpaged;

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
class EntityQueryFacadeHappyPathTest {

    final List<Department> defaultData = new ArrayList<>();
    final DeparmentQueryFacade defaultFacade = new DeparmentQueryFacade(defaultData);

    Department department01;
    Department department02;

    Manager manager;

    @BeforeEach
    void setUp() {
        defaultData.clear();

        manager = new Manager("x2", "Business Happy");

        department01 = new Department("x1", "Default 01", "Peopple", manager);
        department02 = new Department("x2", "Default 02", "Resource", manager);

        defaultData.addAll(List.of(department01, department02));
    }

    @AfterAll
    void tearDown() {
        // No requirements yet
    }

    @Test
    @Order(1)
    @DisplayName("find by id test")
    void findByIdTest() {
        // given
        final var id = "x1";

        // when
        final var result = defaultFacade.findBy(id);

        // then
        assertThat(department01).isEqualTo(result);
        assertThat(defaultData).contains(result);
        assertThat(result).extracting("operation").isEqualTo(0);
    }

    @Test
    @Order(2)
    @DisplayName("find all test")
    void findAllTest() {
        // given
        final var currentData = defaultData;

        // when
        final var result = defaultFacade.findAll();

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
        final var result = defaultFacade.countAll().intValue();

        // then
        assertThat(currentSize).isEqualTo(result);
    }

    @Test
    @Order(4)
    @DisplayName("Count all test")
    void existsAllTest() {
        // given
        final var existsAll = defaultData.size() > 0;

        // when
        final var result = defaultFacade.existsAll();

        // then
        assertThat(existsAll)
                .isEqualTo(result)
                .matches(e -> {
                    defaultData.clear();
                    return defaultFacade.existsAll() != existsAll;
                });
    }

    @Test
    @Order(5)
    @DisplayName("find by id test")
    void existsByIdTest() {
        // given
        final var id = "x1";

        // when
        final var result = defaultFacade.existsBy(id);

        // then
        assertThat(result)
                .isTrue()
                .matches(e -> defaultFacade.existsBy("whatever") != result);
    }

}
