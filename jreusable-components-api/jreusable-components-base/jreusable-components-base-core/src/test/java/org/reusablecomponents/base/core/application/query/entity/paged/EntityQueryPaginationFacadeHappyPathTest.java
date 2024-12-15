package org.reusablecomponents.base.core.application.query.entity.paged;

import static org.apache.commons.lang3.StringUtils.leftPad;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.application_example.application.query.entity.paged.DeparmentQueryPaginationFacade;
import org.application_example.application.query.entity.paged.PageList;
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
@DisplayName("Test the QueryPaginationFacade entity test, happy Path :) ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class EntityQueryPaginationFacadeHappyPathTest {

    final List<Department> defaultData = new ArrayList<>();
    final DeparmentQueryPaginationFacade defaultQueryFacade = new DeparmentQueryPaginationFacade(defaultData);

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
    }

    @Test
    @Order(1)
    @DisplayName("Find one sorted")
    void findOneSortedTest() {
        // given
        final var sort = (Comparator<Department>) Comparator.comparing(Department::getName).reversed();

        // when
        final var result = defaultQueryFacade.findOne(sort);

        // then
        assertThat(defaultData.getLast()).isEqualTo(result);
        assertThat(defaultData).contains(result);
    }

    @Test
    @Order(2)
    @DisplayName("Find all pageable test")
    void findAllPageableTest() {
        // given
        final var pageable = new PageList<>(5, 0, defaultData);
        final var departmentInsidePage = defaultData.get(2);
        final var departmentOusidePage = defaultData.get(5);

        // when
        final var result = defaultQueryFacade.findAll(pageable);

        // then
        assertThat(result)
                .size().isEqualTo(5)
                .returnToIterable()
                .contains(departmentInsidePage)
                .doesNotContain(departmentOusidePage);
    }
}
