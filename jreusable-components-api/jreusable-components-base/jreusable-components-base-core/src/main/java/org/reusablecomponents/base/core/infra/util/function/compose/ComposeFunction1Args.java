package org.reusablecomponents.base.core.infra.util.function.compose;

import java.util.function.Function;

/**
 * Especialized function used by <code>BaseFacade.executeFunctions</code> to
 * execute code in operations.
 */
@FunctionalInterface
public non-sealed interface ComposeFunction1Args extends Function<Object[], Object[]>, ComposeFunction {

}
