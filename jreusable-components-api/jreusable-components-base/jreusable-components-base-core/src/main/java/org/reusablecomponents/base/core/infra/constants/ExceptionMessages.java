package org.reusablecomponents.base.core.infra.constants;

/**
 * Class with message code exceptions.
 */
public final class ExceptionMessages {

    public static final String ELEMENT_ALREADY_EXITS_EXCEPTION_MSG = "{exception.elementAlreadyExistsException}";

    public static final String INVALID_ELEMENT_EXCEPTION_MSG = "{exception.invalidElementException}";

    public static final String ELEMENT_NOT_FOUND_EXCEPTION_MSG = "{exception.elementNotFoundException}";

    public static final String ELEMENT_CONFLICT_EXCEPTION_MSG = "{exception.elementConflictException}";

    public static final String ELEMENT_WITH_ID_NOT_FOUND_MSG = "{exception.elementWithIdNotFound}";

    // --------

    public static final String NULL_POINTER_EXCEPTION_MSG = "{exception.nullPointerException}";

    public static final String ID_ALREADY_EXITS_EXCEPTION_MSG = "{exception.idAlreadyExistsException}";

    public static final String UNEXPECTED_ERROR_MSG = "{exception.unexpectedException}";

    private ExceptionMessages() {
        throw new IllegalStateException("You cannot instanciate this class");
    }
}
