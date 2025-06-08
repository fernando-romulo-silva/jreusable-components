package org.reusablecomponents.rest.rest.query.entity.nonpaged;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.function.Consumer;

import org.reusablecomponents.base.core.application.query.entity.simple.InterfaceQueryFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;

import com.google.common.base.Function;

public class EntityQueryHttpControllerBuilder<Entity extends AbstractEntity<Id>, Id, //
		QueryIdIn, // by id arg
		OneResult, // One result type
		MultipleResult, // multiple result type
		CountResult, // count result type
		ExistsResult, //
		HttpResponseVoid, HttpResponseOne, HttpResponseMultiple> {

	public InterfaceQueryFacade<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult> entityQueryFacade;

	public Function<OneResult, HttpResponseOne> createResponseGetOneFunction;

	public Function<ExistsResult, HttpResponseVoid> createResponseHeadFunction;

	public Function<MultipleResult, HttpResponseMultiple> createResponseGetMultipleFunction;

	public EntityQueryHttpControllerBuilder(
			final Consumer<EntityQueryHttpControllerBuilder<Entity, Id, QueryIdIn, OneResult, MultipleResult, CountResult, ExistsResult, HttpResponseVoid, HttpResponseOne, HttpResponseMultiple>> function) {
		super();

		// load the functions
		function.accept(this);

		checkNotNull(createResponseGetOneFunction, "Please pass a non-null 'createResponseGetOneFunction'");
		checkNotNull(createResponseGetMultipleFunction, "Please pass a non-null 'createResponseGetMultipleFunction'");
		checkNotNull(createResponseHeadFunction, "Please pass a non-null 'createResponseHeadFunction'");

		checkNotNull(entityQueryFacade, "Please pass a non-null 'entityCommandFacade'");
	}
}
