package org.reusablecomponents.base.core.application.base;

/**
 * This class contains all the messages used in the BaseFacade class.
 */
public class BaseFacadeMessage {

    /**
     * Message indicating that the 'functions' parameter must not be null.
     */
    public static final String NON_NULL_FUNCTIONS_MSG = "Please pass a non-null 'functions'";

    /**
     * Message indicating that the 'directives' parameter must not be null.
     */
    public static final String NON_NULL_DIRECTIVES_MSG = "Please pass a non-null 'directives'";

    /**
     * Message indicating that the 'errorFunction' parameter must not be null.
     */
    public static final String NON_NULL_ERROR_FUNCTION_MSG = "Please pass a non-null 'errorFunction'";

    /**
     * Message indicating that the 'mainFunction' parameter must not be null.
     */
    public static final String NON_NULL_MAIN_FUNCTION_MSG = "Please pass a non-null 'mainFunction'";

    /**
     * Message indicating that the 'posFunction' parameter must not be null.
     */
    public static final String NON_NULL_POS_FUNCTION_MSG = "Please pass a non-null 'posFunction'";

    /**
     * Message indicating that the 'preFunction' parameter must not be null.
     */
    public static final String NON_NULL_PRE_FUNCTION_MSG = "Please pass a non-null 'preFunction'";

    /**
     * Message indicating that the 'operation' parameter must not be null.
     */
    public static final String NON_NULL_OPERATION_MSG = "Please pass a non-null 'operation'";

    /**
     * Message template for logging the execution of a POS operation, including the
     * operation name, parameters, session information, and directives.
     */
    public static final String POS_OPERATION_LOG = "Pos {} operation with {} '{}', session '{}', and directives '{}'";

    /**
     * Message template for logging the execution of an operation, including the
     * operation name, parameters, session information, and directives.
     */
    public static final String OPERATION_EXECUTED_LOG = "{} operation executed, with {} '{}', session '{}', and directives '{}'";

    /**
     * Private constructor to prevent instantiation of this utility class, as it
     * only contains static constants and should not be instantiated.
     */
    private BaseFacadeMessage() {
        throw new IllegalStateException("You cannot instantiate this class");
    }
}