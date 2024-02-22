package org.reusablecomponent.infra.jsonpath;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Possible operations to jsonPath
 */
public enum JsonPatchOperation {

//    ADD,
//    MOVE

    REMOVE,

    REPLACE;

    @JsonCreator
    public static JsonPatchOperation fromString(final String key) {

	for (final var type : JsonPatchOperation.values()) {
	    if (type.name().equalsIgnoreCase(key)) {
		return type;
	    }
	}

	return null;
    }
}
