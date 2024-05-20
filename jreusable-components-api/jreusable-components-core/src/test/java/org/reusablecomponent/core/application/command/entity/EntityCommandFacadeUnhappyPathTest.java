package org.reusablecomponent.core.application.command.entity;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import java.util.ArrayList;
import java.util.List;

import org.application_example.application.DepartmentFacade;
import org.application_example.domain.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reusablecomponent.core.infra.exception.ElementAlreadyExistsException;

@Tag("unit")
@DisplayName("Test the EntityCommandFacade entity test, unhappy path :( ")
@ExtendWith(MockitoExtension.class)
@TestInstance(PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class EntityCommandFacadeUnhappyPathTest {
    
    final List<Department> defaultData = new ArrayList<>();
    final DepartmentFacade defaultFacade = new DepartmentFacade(defaultData);
    
    Department department01;
    Department department02;
    
    @BeforeEach
    void setUp() {
	defaultData.clear();
	
	department01 = new Department("x1", "Default 01", "Peopple");
	department02 = new Department("x2", "Default 02", "Resource");
	
	defaultData.addAll(List.of(department01, department02));
    }
    

}
