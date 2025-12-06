package org.application_example.infra;

import org.reusablecomponents.base.core.application.command.entity.function.delete.DeleteFunction;
import org.reusablecomponents.base.core.application.command.entity.function.delete_all.DeleteAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save.SaveFunction;
import org.reusablecomponents.base.core.application.command.entity.function.save_all.SaveAllFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update.UpdateFunction;
import org.reusablecomponents.base.core.application.command.entity.function.update_all.UpdateAllFunction;
import org.reusablecomponents.base.core.application.query.entity.simple.function.find_by_id.FindByIdFunction;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.exception.common.ElementAlreadyExistsException;
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

        if (directives.length == 3
                && directives[0] instanceof OperationFunction operationFunction
                && directives[1] instanceof Class clazz
                && directives[2] instanceof Object object) {

            // (ex, i18nService, *SAVE_ENTITY, getEntityClazz(), saveEntityIn)

            return switch (operationFunction) {
                case SaveFunction _ ->
                    saveEntityAndSaveEntitiesExceptionHandler(ex, i18nService, object);
                case SaveAllFunction _ ->
                    saveEntityAndSaveEntitiesExceptionHandler(ex, i18nService, object);

                case UpdateFunction _ ->
                    updateEntityAndUpdateEntitiesExceptionHandler(ex, i18nService, object);
                case UpdateAllFunction _ ->
                    updateEntityAndUpdateEntitiesExceptionHandler(ex, i18nService, object);

                case DeleteFunction _ ->
                    deleteEntityAndDeleteEntitiesExceptionHandler(ex, i18nService, object);
                case DeleteAllFunction _ ->
                    deleteByIdAndDeleteByIdsExceptionHandler(ex, clazz, i18nService, object);

                default -> throw new IllegalArgumentException("Unexpected value: " + operationFunction);
            };

        } else if (directives.length >= 2
                && directives[0] instanceof OperationFunction operationFunction
                && directives[1] instanceof Class clazz) {

            return switch (operationFunction) {
                case FindByIdFunction _ -> queryExceptionHandler(ex, clazz, i18nService, directives[2]);

                // case FIND_ENTITY_BY_SPECIFICATION ->
                // new ElementNotFoundException(i18nService, ex, directives[2]);

                // case FIND_ALL_ENTITIES,
                // FIND_ENTITIES_BY_SPECIFICATION,
                // FIND_ALL_ENTITIES_PAGEABLE,
                // FIND_ENTITIES_BY_SPECIFICATION_PAGEABLE,
                // FIND_ENTITY_SORTED,
                // FIND_ENTITY_BY_SPECIFICATION_SORTED,
                // EXISTS_BY_ID,
                // EXISTS_ALL,
                // EXISTS_BY_SPECIFICATION,
                // COUNT_ALL,
                // COUNT_BY_SPECIFICATION ->
                // queryExceptionHandler(ex, i18nService);

                default -> throw new IllegalArgumentException("Unexpected value: " + operationFunction);
            };

        }

        throw new UnsupportedOperationException("Unimplemented method 'convert'");
    }

    private BaseException saveEntityAndSaveEntitiesExceptionHandler(
            final Exception ex,
            final InterfaceI18nService i18nService,
            final Object object) {

        return switch (ex) {

            case IllegalArgumentException _ -> new ElementAlreadyExistsException(i18nService, ex, object);
            case IllegalStateException _ -> new ElementInvalidException(i18nService, ex, object);

            default -> throw new IllegalArgumentException("Unexpected exception", ex);
        };
    }

    private BaseException updateEntityAndUpdateEntitiesExceptionHandler(
            final Exception ex,
            final InterfaceI18nService i18nService,
            final Object object) {

        return switch (ex) {

            case IllegalArgumentException _ -> new ElementNotFoundException(i18nService, ex, object);
            case IllegalStateException _ -> new ElementInvalidException(i18nService, ex, object);

            default -> throw new IllegalArgumentException("Unexpected exception", ex);
        };
    }

    private BaseException deleteEntityAndDeleteEntitiesExceptionHandler(
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

    private BaseException deleteByIdAndDeleteByIdsExceptionHandler(
            final Exception ex,
            final Class<?> clazz,
            final InterfaceI18nService i18nService,
            final Object object) {

        return switch (ex) {
            case IllegalArgumentException _ -> new ElementWithIdNotFoundException(clazz, i18nService, ex, object);
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

    private BaseException queryExceptionHandler(
            final Exception ex,
            final InterfaceI18nService i18nService) {

        return switch (ex) {
            case IllegalStateException _ -> new UnexpectedException(i18nService, ex);

            default -> throw new IllegalArgumentException("Unexpected exception", ex);
        };
    }

}