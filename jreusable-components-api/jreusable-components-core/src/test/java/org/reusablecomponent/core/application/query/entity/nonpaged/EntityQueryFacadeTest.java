package org.reusablecomponent.core.application.query.entity.nonpaged;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mock;
import org.reusablecomponent.core.domain.Person;


public class EntityQueryFacadeTest {

    @Mock
    protected Function<Long, Boolean> existsByIdFunction;
    
    @Mock
    protected Function<Long, Optional<Person>> findByIdFunction;
    
    @Mock
    protected Supplier<List<Person>> findAllFunction;
    
    @Mock
    protected Supplier<Long> countAllFunction;
    
    @BeforeAll
    protected void setUp() {
	
	Person p1 = null;
	Person p2 = null;
	
	final var personList = Arrays.asList(p1, p2);    
	
	openMocks(this);
	
	when(existsByIdFunction.apply(1L))
		.thenReturn(TRUE);
	
	when(existsByIdFunction.apply(2L))
		.thenReturn(FALSE);
	
	when(findByIdFunction.apply(1L))
		.thenReturn(Optional.ofNullable(p1));
	
	when(findAllFunction.get())
		.thenReturn(personList);
	
    }
    
    
}
