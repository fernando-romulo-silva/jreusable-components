package org.reusablecomponents.base.core.application.base;

public class BaseFacadeMessage {

    public static final String NON_NULL_FUNCTIONS_MSG = "Please pass a non-null 'functions'";

    public static final String NON_NULL_DIRECTIVES_MSG = "Please pass a non-null 'directives'";

    public static final String NON_NULL_ERROR_FUNCTION_MSG = "Please pass a non-null 'errorFunction'";

    public static final String NON_NULL_MAIN_FUNCTION_MSG = "Please pass a non-null 'mainFunction'";

    public static final String NON_NULL_POS_FUNCTION_MSG = "Please pass a non-null 'posFunction'";

    public static final String NON_NULL_PRE_FUNCTION_MSG = "Please pass a non-null 'preFunction'";

    public static final String NON_NULL_OPERATION_MSG = "Please pass a non-null 'operation'";

    public static final String POS_OPERATION_LOG = "Pos {} operation with {} '{}', session '{}', and directives '{}'";

    public static final String OPERATION_EXECUTED_LOG = "{} operation executed, with {} '{}', session '{}', and directives '{}'";

    public static final String FINAL_LOG = "final";

    public static final String PRE_LOG = "pre";

    public static final String ERROR_ON_OPERATION_LOG = "Error on {} operation with {} '{}', session '{}', error '{}'";

    public static final String ERROR_ON_PRE_OPERATION_LOG = "Error on pre {} operation with {} '{}', session '{}', error '{}'";

    public static final String ERROR_ON_POS_OPERATION_LOG = "Error on pos {} operation with {} '{}', session '{}', error '{}'";

    private BaseFacadeMessage() {
        throw new IllegalStateException("You cannot instanciate this class");
    }
}