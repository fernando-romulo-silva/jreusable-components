package org.reusablecomponents.base.core.application.command.entity.function.save;

import org.reusablecomponents.base.core.application.command.CommandFunction;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;

/**
 * A functional interface representing a pre-save function for an entity. This
 * function is executed before saving an entity and can be used to perform any
 * necessary operations or transformations on the entity before it is persisted.
 *
 * @param <SaveEntityIn> the type of the entity being saved
 * @param <Object[]>     the type of the additional arguments that may be needed
 *                       for pre-save operations, such as context information or
 *                       related data
 * @return the entity after any pre-save operations have been applied
 * 
 * @author Fernando Romulo da Silva
 * @since 1.0
 */
@FunctionalInterface
public non-sealed interface PreSaveFunction<SaveEntityIn>
		extends CommandFunction, OperationFunction2Args<SaveEntityIn, Object[], SaveEntityIn> {
}