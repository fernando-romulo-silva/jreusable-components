package org.reusablecomponents.rest.infra.openapi.annotations.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.reusablecomponents.rest.infra.openapi.annotations.response.error.EntityResponseError404OpenApi;
import org.reusablecomponents.rest.infra.openapi.annotations.response.error.EntityResponseError500OpenApi;
import org.reusablecomponents.rest.infra.openapi.annotations.response.error.EntityResponseRestPostError400OpenApi;
import org.reusablecomponents.rest.infra.openapi.annotations.response.success.OpenApiEntityUpdate204;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
//
//
@Tag(name = "BETA", description = "This API is currently in beta state")
@Operation( //
		summary = "Update a partial entity", //
		description = "This method update a entity's type part" //
)
@OpenApiEntityUpdate204
@EntityResponseRestPostError400OpenApi
@EntityResponseError404OpenApi
@EntityResponseError500OpenApi
public @interface EntityRestPatchOpenApi {

}
