package org.reusablecomponent.core.application.full.entity;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.reusablecomponent.core.application.full.entity.nonpaged.EntityFacade;
import org.reusablecomponent.core.infra.exception.ElementAlreadyExistsException;

@Tag("integration")
public class AbstractEntityFacadeUnhappyPathTest {
    
    protected EntityFacade entityFacade;

    @Test
    @Order(1)
    @DisplayName("save a entity test")
    void saveRepeatedElement() {
	
//	// given
//	assertThat(defaultData).contains(department01);
//	
//	// when
//	assertThatThrownBy(() -> {
//	    
//	    entityFacade.save(department01);
//	    
//	}) // then 
//	.as(format("Check the repeated entity: ''{0}''", department01)) //
//	.isInstanceOf(ElementAlreadyExistsException.class);
	
    }

}
