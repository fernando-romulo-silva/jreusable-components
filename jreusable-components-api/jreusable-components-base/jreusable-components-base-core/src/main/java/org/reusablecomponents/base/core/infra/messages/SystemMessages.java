package org.reusablecomponents.base.core.infra.messages;

/**
 * Class with message code exceptions.
 */
public final class SystemMessages {

    public static final String NULL_POINTER_EXCEPTION_MSG = "{exception.nullPointerException}";

    public static final String ELEMENT_ALREADY_EXITS_EXCEPTION_MSG = "{exception.elementAlreadyExistsException}";

    public static final String ID_ALREADY_EXITS_EXCEPTION_MSG = "{exception.idAlreadyExists}";

    private SystemMessages() {
        throw new IllegalStateException("You cannot instanciate this class");
    }
}
