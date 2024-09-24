package org.application_example.infra;

import static org.reusablecomponents.base.messaging.operation.CommandOperation.*;
import static org.reusablecomponents.base.messaging.operation.QueryOperation.*;

import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.core.infra.exception.common.BaseApplicationException;
import org.reusablecomponents.base.core.infra.exception.common.ElementAlreadyExistsException;
import org.reusablecomponents.base.core.infra.exception.common.ElementConflictException;
import org.reusablecomponents.base.core.infra.exception.common.ElementInvalidException;
import org.reusablecomponents.base.core.infra.exception.common.ElementNotFoundException;
import org.reusablecomponents.base.messaging.operation.CommandOperation;
import org.reusablecomponents.base.translation.InterfaceI18nService;

public class ExceptionAdapterListService implements InterfaceExceptionAdapterService {

    @Override
    public BaseApplicationException convert(
            final Exception ex,
            final InterfaceI18nService i18nService,
            final Object... directives) {

        if (directives.length == 3
                && directives[0] instanceof CommandOperation commandOperation
                && directives[1] instanceof Class clazz
                && directives[2] instanceof Object object) {

            // (ex, i18nService, *SAVE_ENTITY, getEntityClazz(), saveEntityIn)

            return switch (commandOperation) {
                case SAVE_ENTITY, SAVE_ENTITIES ->
                    saveEntityAndSaveEntitiesExceptionHandler(ex, i18nService, object);

                case UPDATE_ENTITY, UPDATE_ENTITIES ->
                    updateEntityAndUpdateEntitiesExceptionHandler(ex, i18nService, object);

                case DELETE_ENTITY, DELETE_ENTITIES ->
                    deleteEntityAndDeleteEntitiesExceptionHandler(ex, i18nService, object);

                case DELETE_BY_ID, DELETE_BY_IDS ->
                    throw new UnsupportedOperationException("Unimplemented case: " + commandOperation);

                default -> throw new IllegalArgumentException("Unexpected value: " + commandOperation);
            };
        }

        throw new UnsupportedOperationException("Unimplemented method 'convert'");
    }

    private BaseApplicationException saveEntityAndSaveEntitiesExceptionHandler(
            final Exception ex,
            final InterfaceI18nService i18nService,
            final Object object) {

        return switch (ex) {
            case IllegalArgumentException ex2 -> new ElementAlreadyExistsException(i18nService, ex, object);
            case IllegalStateException ex2 -> new ElementInvalidException(i18nService, ex, object);

            default -> throw new IllegalArgumentException("Unexpected exception", ex);
        };
    }

    private BaseApplicationException updateEntityAndUpdateEntitiesExceptionHandler(
            final Exception ex,
            final InterfaceI18nService i18nService,
            final Object object) {

        return switch (ex) {
            case IllegalArgumentException ex2 -> new ElementNotFoundException(i18nService, ex, object);
            case IllegalStateException ex2 -> new ElementInvalidException(i18nService, ex, object);

            default -> throw new IllegalArgumentException("Unexpected exception", ex);
        };
    }

    private BaseApplicationException deleteEntityAndDeleteEntitiesExceptionHandler(
            final Exception ex,
            final InterfaceI18nService i18nService,
            final Object object) {

        return switch (ex) {
            case IllegalArgumentException ex2 -> new ElementNotFoundException(i18nService, ex, object);
            case IllegalStateException ex2 -> new ElementInvalidException(i18nService, ex, object);
            case ArrayStoreException ex2 -> new ElementConflictException(i18nService, ex, object);

            default -> throw new IllegalArgumentException("Unexpected exception", ex);
        };
    }

}