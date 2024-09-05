package org.application_example.infra;

import static org.reusablecomponents.base.messaging.operation.CommandOperation.*;

import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.core.infra.exception.common.BaseApplicationException;
import org.reusablecomponents.base.core.infra.exception.common.ElementAlreadyExistsException;
import org.reusablecomponents.base.messaging.operation.CommandOperation;
import org.reusablecomponents.base.translation.InterfaceI18nService;

public class ExceptionAdapterListService implements InterfaceExceptionAdapterService {

    @Override
    public BaseApplicationException convert(
            final Exception ex,
            final InterfaceI18nService i18nService,
            final Object... directives) {

        if (ex instanceof IllegalArgumentException
                && directives.length > 1
                && directives[0] instanceof CommandOperation commandOperation
                && directives[1] instanceof Class clazz) {

            return switch (commandOperation) {
                case SAVE_ENTITY -> new ElementAlreadyExistsException(clazz, null);
                case SAVE_ENTITIES -> new ElementAlreadyExistsException(null, directives);
                case DELETE_BY_ID -> throw new UnsupportedOperationException("Unimplemented case: " + commandOperation);
                case DELETE_BY_IDS ->
                    throw new UnsupportedOperationException("Unimplemented case: " + commandOperation);
                case DELETE_ENTITIES ->
                    throw new UnsupportedOperationException("Unimplemented case: " + commandOperation);
                case DELETE_ENTITY ->
                    throw new UnsupportedOperationException("Unimplemented case: " + commandOperation);
                case UPDATE_ENTITIES ->
                    throw new UnsupportedOperationException("Unimplemented case: " + commandOperation);
                case UPDATE_ENTITY ->
                    throw new UnsupportedOperationException("Unimplemented case: " + commandOperation);
                default -> throw new IllegalArgumentException("Unexpected value: " + commandOperation);
            };
        }

        throw new UnsupportedOperationException("Unimplemented method 'convert'");
    }

}