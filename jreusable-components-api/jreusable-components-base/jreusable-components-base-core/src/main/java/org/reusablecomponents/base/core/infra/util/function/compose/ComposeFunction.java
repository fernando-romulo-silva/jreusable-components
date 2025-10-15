package org.reusablecomponents.base.core.infra.util.function.compose;

import org.reusablecomponents.base.core.infra.util.function.BaseFunction;

/*
 *  Commons methods used on composed functions. <br />
 *  To compose functions, you combine two functions, say f(x) and g(x), by using the output of one function as the input for the other. 
 *  For example, f(g(x)) means you first find the result of g(x) and then use that result as the input for f(x). 
 */
public sealed interface ComposeFunction extends BaseFunction
        permits ComposeFunction1Args, ComposeFunction2Args, ComposeFunction3Args, ComposeFunction4Args {

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
        return false;
    }
}
