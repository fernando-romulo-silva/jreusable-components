package org.reusablecomponent.rest.rest.query.entity.nonpaged;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.function.Consumer;

import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQueryFacade;
import org.reusablecomponent.core.application.query.entity.nonpaged.InterfaceEntityQuerySpecificationFacade;
import org.reusablecomponent.core.domain.AbstractEntity;

import com.google.common.base.Function;

public class EntityQuerySpecificationHttpBuilder<Entity extends AbstractEntity<Id>, Id, //
		QueryIdIn, // by id arg
		OneResult, // One result type
		MultipleResult, // multiple result type
		CountResult, // count result type
		ExistsResult, //
		Specification, // query specification
		HttpResponseVoid, HttpResponseOne, HttpResponseMultiple> {
    
    public InterfaceEntityQuerySpecificationFacade<Entity, Id, OneResult, MultipleResult, CountResult, ExistsResult, Specification> entityQuerySpecificationFacade;
    
    public InterfaceEntityQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> entityQueryFacade;
    
    public Function<OneResult, HttpResponseOne> createResponseGetOneFunction;
    
    public Function<ExistsResult, HttpResponseVoid> createResponseHeadFunction;
    
    public Function<MultipleResult, HttpResponseMultiple> createResponseGetMultipleFunction;
    
    public EntityQuerySpecificationHttpBuilder(final Consumer<EntityQuerySpecificationHttpBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult, Specification, HttpResponseVoid, HttpResponseOne, HttpResponseMultiple>> function) {
	super();
	
	// load the functions
	function.accept(this);

	checkNotNull(createResponseGetOneFunction, "Please pass a non-null 'createResponseGetOneFunction'");
	checkNotNull(createResponseHeadFunction, "Please pass a non-null 'createResponseHeadFunction'");
	checkNotNull(createResponseGetMultipleFunction, "Please pass a non-null 'createResponseGetMultipleFunction'");
	
	checkNotNull(entityQueryFacade, "Please pass a non-null 'entityQueryFacade'");
    }

}
