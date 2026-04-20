package org.reusablecomponents.base.core.infra.util.function.operation;

import org.apache.commons.lang3.StringUtils;

/**
 * OperationFunction is a functional interface that represents an operation
 * function in the application.
 * 
 * It provides additional methods for describing the operation, controlling its
 * execution, and handling exceptions.
 * 
 * This interface can be used as a base for creating specific operation function
 * implementations that require a description, execution control, and exception
 * handling.
 */
public sealed interface OperationFunction
        permits OperationFunction1Args, OperationFunction2Args, OperationFunction3Args, OperationFunction4Args {

    /**
     * Return operation's description.
     * 
     * @return A <code>String</code> object
     */
    default String getDescription() {
        return this.getClass().getSimpleName();
    }

    /**
     * A function flag control to check if execute or not it, the default's true
     * 
     * @return true if is active, the function will be executed or false, the
     *         function won't be executed
     */
    default boolean isActive() {
        return true;
    }

    /**
     * A function flag control to check if in case of function error, throw the
     * exception or not, the deault's is false.
     * 
     * @return true if this function re throw the exception or false if don't.
     */
    default boolean reTrowException() {
        return true;
    }

    /**
     * Return the function name, the default's instance simple name
     * 
     * @return A String with function name
     */
    default String getName() {
        final var simpleName = this.getClass().getSimpleName();

        if (simpleName.contains("$")) {
            return StringUtils.substringBefore(simpleName, "$");
        }

        return simpleName;
    }
}
