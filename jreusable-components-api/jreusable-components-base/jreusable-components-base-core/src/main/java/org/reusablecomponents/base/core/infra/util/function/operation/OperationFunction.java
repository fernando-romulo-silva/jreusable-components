package org.reusablecomponents.base.core.infra.util.function.operation;

import org.reusablecomponents.base.core.infra.util.function.BaseFunction;

/**
 * Describe a operation on framework, event, etc.
 */
public sealed interface OperationFunction extends BaseFunction
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
    default boolean isActice() {
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
}
