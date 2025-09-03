package org.reusablecomponents.base.core.application.base;

import java.util.function.Consumer;

/**
 * Especialized function used by <code>BaseFacade.executeFunctions</code> to
 * execute code in operations.
 */
@FunctionalInterface
public non-sealed interface FacadeFunction extends Consumer<Object[]>, BaseFunction {

}
