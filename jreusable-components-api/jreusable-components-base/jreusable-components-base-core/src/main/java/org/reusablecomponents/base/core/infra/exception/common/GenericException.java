package org.reusablecomponents.base.core.infra.exception.common;

import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;

public final class GenericException extends BaseApplicationException {

    private static final long serialVersionUID = 1L;

    public GenericException(final Exception exception) {
        super(getRootCauseMessage(exception));
    }
}
