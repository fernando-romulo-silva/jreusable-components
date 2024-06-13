package org.reusablecomponents.rest.infra.openapi.annotations.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.reusablecomponents.rest.infra.openapi.annotations.response.error.EntityResponseError500OpenApi;
import org.reusablecomponents.rest.infra.openapi.annotations.response.error.OpenApiEntityGetBySearchError400;
import org.reusablecomponents.rest.infra.openapi.annotations.response.success.OpenApiEntityGet200;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
//
//
@Tag(name = "BETA", description = "This API is currently in beta state")
@Operation( //
		summary = "Check the entity exists by criteria search", //
		description = "This method return don't return value only http status" //
)
@OpenApiEntityGet200
@OpenApiEntityGetBySearchError400
@EntityResponseError500OpenApi
public @interface EntityRestHeadOpenApi {

}
