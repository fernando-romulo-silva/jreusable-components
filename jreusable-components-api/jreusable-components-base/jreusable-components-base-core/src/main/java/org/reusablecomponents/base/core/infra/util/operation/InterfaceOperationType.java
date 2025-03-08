package org.reusablecomponents.base.core.infra.util.operation;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.substringAfterLast;
import static org.reusablecomponents.base.core.infra.util.Const.CAMEL_REGEX;

/**
 * Describe a operation on framework, event, etc.
 */
public interface InterfaceOperationType {

    /**
     * Return operation's name
     * 
     * @return A <code>String</code> object
     */
    default String getName() {
        return this.toString();
    }

    /**
     * Return operation's description.
     * 
     * @return A <code>String</code> object
     */
    default String getDescription() {
        return this.getClass().getSimpleName();
    }

    /**
     * Return the operation receiver's object. <br />
     * Ex 1:
     * 
     * <pre>
     * enum SAVE_ENTITY returns "Entity"
     * </pre>
     * 
     * Ex 2:
     * 
     * <pre>
     * enum FIND_BY_ID returns "Id"
     * </pre>
     * 
     * Ex 3:
     * 
     * <pre>
     * class ProcessPayment returns "Payment"
     * </pre>
     * 
     * @return A <code>String</code> object
     */
    default String getReceiver() {

        if (this instanceof Enum<?> varEnum) {
            final var name = substringAfterLast(varEnum.name(), "_");
            return capitalize(name.toLowerCase());
        }

        final var className = this.getClass().getSimpleName();
        final var str = asList(className.split(CAMEL_REGEX)).getLast();

        return capitalize(str.toLowerCase());
    }
}
