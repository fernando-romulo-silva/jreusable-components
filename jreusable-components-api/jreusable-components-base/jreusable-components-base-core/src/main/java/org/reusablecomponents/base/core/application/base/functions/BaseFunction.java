package org.reusablecomponents.base.core.application.base.functions;

/*
 *  Commons methods used on functions
 */
sealed interface BaseFunction
        permits FacadeFunctionNoArgs, FacadeFunctionOneArg, FacadeFunctionTwoArgs, FacadeFunctionThreeArgs {

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
     * Return the function name, the default's instance simple name
     * 
     * @return A String with function name
     */
    default String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * A function flag control to check if in case of function error, throw the
     * exception or not, the deault's is false.
     * 
     * @return true if this function re throw the exception or false if don't.
     */
    default boolean reTrowException() {
        return false;
    }
}
