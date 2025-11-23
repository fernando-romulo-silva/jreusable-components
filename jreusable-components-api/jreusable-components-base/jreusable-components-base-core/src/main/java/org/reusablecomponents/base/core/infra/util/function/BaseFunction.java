package org.reusablecomponents.base.core.infra.util.function;

import org.apache.commons.lang3.StringUtils;

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
        final var simpleName = this.getClass().getSimpleName();

        if (simpleName.contains("$")) {
            return StringUtils.substringBefore(simpleName, "$");
        }

        return simpleName;
    }
}