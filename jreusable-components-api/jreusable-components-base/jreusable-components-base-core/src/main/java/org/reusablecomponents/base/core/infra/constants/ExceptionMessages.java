package org.reusablecomponents.base.core.infra.constants;

/**
 * Class with message code exceptions.
 */
public final class ExceptionMessages {

    /**
     * Elemente already exists: The object ''{0}'' already exists
     */
    public static final String ELEMENT_ALREADY_EXITS_EXCEPTION_MSG = "{exception.elementAlreadyExistsException}";

    /**
     * Invalid element: The object ''{0}'' is invalid
     */
    public static final String INVALID_ELEMENT_EXCEPTION_MSG = "{exception.invalidElementException}";

    /**
     * Element not found: The object ''{0}'' not found
     */
    public static final String ELEMENT_NOT_FOUND_EXCEPTION_MSG = "{exception.elementNotFoundException}";

    /**
     * No result: No result found
     */
    public static final String NO_RESULT_EXCEPTION_MSG = "{exception.noResultFoundException}";

    /**
     * Element conflicts: The object ''{0}'' has conflict(s)
     */
    public static final String ELEMENT_CONFLICT_EXCEPTION_MSG = "{exception.elementConflictException}";

    /**
     * Element with id not found: The id ''{0}'' not found for ''{1}'' type
     */
    public static final String ELEMENT_WITH_ID_NOT_FOUND_MSG = "{exception.elementWithIdNotFound}";

    /**
     * Null pointer exception: The object ''{0}'' cannot be null
     */
    public static final String NULL_POINTER_EXCEPTION_MSG = "{exception.nullPointerException}";

    /**
     * Id already exists: The entity ''{0}'' with id ''{1}'' already exists
     */
    public static final String ID_ALREADY_EXITS_EXCEPTION_MSG = "{exception.idAlreadyExistsException}";

    /**
     * Unexpected error: Unexpecte error happened
     */
    public static final String UNEXPECTED_ERROR_MSG = "{exception.unexpectedException}";

    private ExceptionMessages() {
        throw new IllegalStateException("You cannot instanciate this class");
    }
}
