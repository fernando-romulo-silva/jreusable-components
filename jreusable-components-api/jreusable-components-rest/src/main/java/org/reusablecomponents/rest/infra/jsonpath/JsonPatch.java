package org.reusablecomponents.rest.infra.jsonpath;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Structure to execute patch operations.
 * 
 * @author Fernando Romulo da Silva
 */
@Schema(name = "JsonPatch", description = "Response to convert image")
public record JsonPatch(
		@Schema(name = "op", requiredMode = REQUIRED, example = "replace, remove") //
		JsonPatchOperation op,

		@Schema(name = "path", requiredMode = REQUIRED, example = "name") //
		String path,

		@Schema(name = "value", requiredMode = REQUIRED, example = "Paul") //
		String value) {
}
