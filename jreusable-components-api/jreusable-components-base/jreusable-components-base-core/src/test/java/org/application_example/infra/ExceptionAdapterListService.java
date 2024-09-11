package org.application_example.infra;

import static org.reusablecomponents.base.messaging.operation.CommandOperation.*;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.*;

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
                && directives.length == 3
                && directives[0] instanceof CommandOperation commandOperation
                && directives[1] instanceof Class clazz
                && directives[2] instanceof Object object) {

            // (ex, i18nService, *SAVE_ENTITY, getEntityClazz(), saveEntityIn)

            return switch (commandOperation) {
                case SAVE_ENTITY, SAVE_ENTITIES -> new ElementAlreadyExistsException(clazz, i18nService, object);

                case DELETE_BY_ID, DELETE_BY_IDS ->
                    throw new UnsupportedOperationException("Unimplemented case: " + commandOperation);

                case DELETE_ENTITY, DELETE_ENTITIES ->
                    throw new UnsupportedOperationException("Unimplemented case: " + commandOperation);

                case UPDATE_ENTITY, UPDATE_ENTITIES ->
                    throw new UnsupportedOperationException("Unimplemented case: " + commandOperation);

                default -> throw new IllegalArgumentException("Unexpected value: " + commandOperation);
            };
        }

        throw new UnsupportedOperationException("Unimplemented method 'convert'");
    }

}