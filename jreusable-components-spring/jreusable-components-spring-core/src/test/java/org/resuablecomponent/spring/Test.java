package org.resuablecomponent.spring;

import java.util.Optional;

import org.reusablecomponent.core.application.command.entity.InterfaceEntityCommandFacade;
import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponent.core.domain.AbstractEntity;
import org.reusablecomponent.spring.core.application.AbstractSpringEntityFacade;
import org.reusablecomponent.spring.core.infra.i18n.SpringI18nService;
import org.springframework.stereotype.Service;

public class Test {
    
    static class Person extends AbstractEntity<Long> {
	Long id;
    }
    
    @Service
    static class PersonFacade extends AbstractSpringEntityFacade<Person, Long> {

	protected PersonFacade() {
	    super(null, null, null);
	    // TODO Auto-generated constructor stub
	}
	
    }

    public Test() {
	
	final var facade = new PersonFacade();
	
	facade.existsBy(1L);
	
	final Long i = facade.count();
	
	facade.deleteAllBy(null);
    }

}
