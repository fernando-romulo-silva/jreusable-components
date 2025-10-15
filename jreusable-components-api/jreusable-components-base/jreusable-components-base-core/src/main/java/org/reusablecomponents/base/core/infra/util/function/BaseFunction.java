package org.reusablecomponents.base.core.infra.util.function;

/*
 *  Commons methods used on functions
 */
public interface BaseFunction {

    /**
     * Return the function name, the default's instance simple name
     * 
     * @return A String with function name
     */
    default String getName() {
        return this.getClass().getSimpleName();
    }
}