package org.reusablecomponents.base.core.application.command.function;

import org.reusablecomponents.base.core.infra.util.function.compose.ComposeFunction2Args;
import org.reusablecomponents.base.core.infra.util.function.compose.ComposeFunction3Args;

/**
 * 
 */
public class CommandSaveFunctions {

    /*
     * Default constructor
     */
    private CommandSaveFunctions() {
        throw new UnsupportedOperationException("You can't instanciate this class");
    }

    /**
     * 
     */
    @FunctionalInterface
    public static interface PreSaveFunction<Entity> extends ComposeFunction2Args<Entity> {

    }

    /**
     * 
     */
    @FunctionalInterface
    public static interface PosSaveFunction<Entity> extends ComposeFunction2Args<Entity> {

    }

    /**
     * 
     */
    @FunctionalInterface
    public static interface ErrorSaveFunction<Entity, Ex> extends ComposeFunction3Args<Entity, Ex> {

    }

    /**
    * 
    */
    @FunctionalInterface
    public static interface PreSaveAllFunction<Entity> extends ComposeFunction2Args<Entity> {

    }

    /**
    * 
    */
    @FunctionalInterface
    public static interface PosSaveAllFunction<Entity> extends ComposeFunction2Args<Entity> {

    }

    /**
    * 
    */
    @FunctionalInterface
    public static interface ErrorSaveAllFunction<Entity, Ex> extends ComposeFunction3Args<Entity, Ex> {

    }
}
