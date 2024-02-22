package org.reusablecomponent.infra.jsonpath;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Structure to execute patch operations.
 * 
 * @author Fernando Romulo da Silva
 */
@Schema(name = "JsonPatch", description = "Response to convert image")
public record JsonPatch(
		@Schema(name = "op", requiredMode = REQUIRED, example = "replace, remove") //
		@JsonProperty(value = "op", required = true) //		
		JsonPatchOperation op,

		@Schema(name = "path", requiredMode = REQUIRED, example = "name") //
		@JsonProperty(value = "path", required = true) //	
		String path,

		@Schema(name = "value", requiredMode = REQUIRED, example = "Paul") //
		@JsonProperty(value = "value", required = false) //	
		String value) {
}
