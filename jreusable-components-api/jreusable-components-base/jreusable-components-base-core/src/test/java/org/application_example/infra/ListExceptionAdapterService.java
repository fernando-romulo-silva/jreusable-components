package org.application_example.infra;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_by_id.DeleteByIdFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_by_id_all.DeleteByIdsFunction;
import org.reusablecomponents.base.core.application.query.QueryFunction;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.exception.common.ElementConflictException;
import org.reusablecomponents.base.core.infra.exception.common.ElementInvalidException;
import org.reusablecomponents.base.core.infra.exception.common.ElementNotFoundException;
import org.reusablecomponents.base.core.infra.exception.common.ElementWithIdNotFoundException;
import org.reusablecomponents.base.core.infra.exception.common.UnexpectedException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction;
import org.reusablecomponents.base.translation.InterfaceI18nService;

public class ListExceptionAdapterService implements InterfaceExceptionAdapterService {

    @SuppressWarnings("rawtypes")
    @Override
    public BaseException convert(
            final Exception ex,
            final InterfaceI18nService i18nService,
            final Object... directives) {

        if (directives.length >= 3
                && directives[0] instanceof OperationFunction operationFunction
                && directives[1] instanceof Class clazz
                && directives[2] instanceof Object object) {

            return switch (operationFunction) {
                case DeleteByIdFunction _ ->
                    deleteByIdAndDeleteByIdsExceptionHandler(ex, i18nService, object, clazz);
                case DeleteByIdsFunction _ ->
                    deleteByIdAndDeleteByIdsExceptionHandler(ex, i18nService, object, clazz);
                case CommandFunction _ ->
                    commandExceptionHandler(ex, i18nService, object);
                case QueryFunction _ ->
                    queryExceptionHandler(ex, clazz, i18nService, directives[2]);

                default -> throw new IllegalArgumentException("Unexpected value: " + operationFunction);
            };

        } else if (directives.length >= 2
                && directives[0] instanceof OperationFunction operationFunction
                && directives[1] instanceof Class clazz) {

            return switch (operationFunction) {
                case QueryFunction _ ->
                    queryExceptionHandler(ex, clazz, i18nService, directives[2]);

                default -> throw new IllegalArgumentException("Unexpected value: " + operationFunction);
            };

        }

        throw new UnsupportedOperationException("Unimplemented method 'convert'");
    }

    private BaseException deleteByIdAndDeleteByIdsExceptionHandler(
            final Exception ex,
            final InterfaceI18nService i18nService,
            final Object object,
            final Class<?> clazz) {
        return switch (ex) {
            case IllegalArgumentException _ -> new ElementWithIdNotFoundException(clazz, i18nService, ex, object);
            case IllegalStateException _ -> new ElementInvalidException(i18nService, ex, object);
            case ArrayStoreException _ -> new ElementConflictException(i18nService, ex, object);
            default -> throw new IllegalArgumentException("Unexpected exception", ex);
        };
    }

    private BaseException commandExceptionHandler(
            final Exception ex,
            final InterfaceI18nService i18nService,
            final Object object) {
        return switch (ex) {
            case IllegalArgumentException _ -> new ElementNotFoundException(i18nService, ex, object);
            case IllegalStateException _ -> new ElementInvalidException(i18nService, ex, object);
            case ArrayStoreException _ -> new ElementConflictException(i18nService, ex, object);
            default -> throw new IllegalArgumentException("Unexpected exception", ex);
        };
    }

    private BaseException queryExceptionHandler(
            final Exception ex,
            final Class<?> clazz,
            final InterfaceI18nService i18nService,
            final Object object) {
        return switch (ex) {
            case IllegalArgumentException _ ->
                new ElementWithIdNotFoundException(clazz, i18nService, ex, object);
            case IllegalStateException _ -> new UnexpectedException(i18nService, ex);
            default -> throw new IllegalArgumentException("Unexpected exception", ex);
        };
    }
}