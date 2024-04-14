package org.reusablecomponent.rest.infra.openapi.annotations.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.reusablecomponent.rest.infra.openapi.annotations.response.error.EntityResponseError500OpenApi;
import org.reusablecomponent.rest.infra.openapi.annotations.response.error.EntityResponseRestPostError400OpenApi;
import org.reusablecomponent.rest.infra.openapi.annotations.response.error.EntityResponseRestPostError409OpenApi;
import org.reusablecomponent.rest.infra.openapi.annotations.response.success.OpenApiEntityCreate201;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
//
//
@Tag(name = "BETA", description = "This API is currently in beta state")
@Operation( //
		summary = "Create a new entity", //
		description = "This method create a new entity" //
)
@OpenApiEntityCreate201
@EntityResponseRestPostError400OpenApi
@EntityResponseRestPostError409OpenApi
@EntityResponseError500OpenApi
public @interface OpenApiEntityRestPost {

}
