package org.reusablecomponents.rest.rest.base;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @param <Id>
 * @param <OneResult>
 */
@SecurityRequirement(name = "BASIC")
@Tag( //
		name = "Image Convert", //
		description = """
				Entity API - If something went wrong, please put 'trace' (for all Http methods)
				at the end of the call to receive the stackStrace.
				Ex: http://127.0.0.1:8080/image-converter/rest/images/convert?trace=true
				     """ //
)
//
public interface InterfaceEntityBaseHttpController {
    
}
