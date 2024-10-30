package org.reusablecomponents.base.core.infra.util.operation;

/**
 * Describe a operation on framework, event, etc.
 */
public interface InterfaceOperation {

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
}
