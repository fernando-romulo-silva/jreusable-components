package org.reusablecomponent.rest.infra.jsonpath;

/**
 * Possible operations to jsonPath
 */
public enum JsonPatchOperation {

//    ADD,
//    MOVE

    REMOVE,

    REPLACE;

    public static JsonPatchOperation fromString(final String key) {

	for (final var type : JsonPatchOperation.values()) {
	    if (type.name().equalsIgnoreCase(key)) {
		return type;
	    }
	}

	return null;
    }
}
