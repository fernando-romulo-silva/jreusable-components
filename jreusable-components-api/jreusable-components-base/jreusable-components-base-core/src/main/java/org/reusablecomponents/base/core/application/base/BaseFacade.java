package org.reusablecomponents.base.core.application.base;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;
import static org.reusablecomponents.base.core.application.base.BaseFacadeMessage.*;
import static org.reusablecomponents.base.core.infra.util.function.Functions.createNullPointerException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.function.TriFunction;
import org.reusablecomponents.base.core.application.base.functions.BaseFunction;
import org.reusablecomponents.base.core.application.base.functions.FacadeFunctionNoArgs;
import org.reusablecomponents.base.core.application.base.functions.FacadeFunctionOneArg;
import org.reusablecomponents.base.core.application.base.functions.FacadeFunctionTwoArgs;
import org.reusablecomponents.base.core.application.command.entity.CommandFacade;
import org.reusablecomponents.base.core.application.empty.EmptyFacade;
import org.reusablecomponents.base.core.application.query.entity.pagination.QueryPaginationFacade;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.QueryPaginationSpecificationFacade;
import org.reusablecomponents.base.core.application.query.entity.simple.QueryFacade;
import org.reusablecomponents.base.core.application.query.entity.specification.QuerySpecificationFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.core.infra.util.function.QuadFunction;
import org.reusablecomponents.base.core.infra.util.operation.InterfaceOperation;
import org.reusablecomponents.base.security.InterfaceSecurityService;
import org.reusablecomponents.base.translation.InterfaceI18nService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.TypeToken;

import jakarta.validation.constraints.NotNull;

/**
 * The <code>InterfaceEntityBaseFacade</code> common implementation using
 * event-driven architecture.
 * 
 * @param <Entity> The facade entity type
 * @param <Id>     The facade entity id type
 */
public sealed class BaseFacade<Entity extends AbstractEntity<Id>, Id>
		implements InterfaceBaseFacade<Entity, Id>
		permits EmptyFacade, CommandFacade, QueryFacade,
		QuerySpecificationFacade, QueryPaginationFacade, QueryPaginationSpecificationFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseFacade.class);

	protected final InterfaceSecurityService securityService;

	protected final InterfaceI18nService i18nService;

	protected final InterfaceExceptionAdapterService exceptionAdapterService;

	protected final Class<Entity> entityClazz;

	protected final Class<Id> idClazz;

	protected final StackWalker walker = StackWalker.getInstance();

	/**
	 * Default constructor
	 * 
	 * @param builder Object attribute constructor.
	 */
	protected BaseFacade(@NotNull final BaseFacadeBuilder builder) {
		super();

		final var finalBuilder = ofNullable(builder)
				.orElseThrow(createNullPointerException("builder"));

		this.entityClazz = retrieveEntityClazz();
		this.idClazz = retrieveIdClazz();

		this.i18nService = finalBuilder.i18nService;
		this.securityService = finalBuilder.securityService;
		this.exceptionAdapterService = finalBuilder.exceptionAdapterService;
	}

	/**
	 * Capture the generic class type
	 * 
	 * @return A <code>Class<Entity></code> object
	 */
	@SuppressWarnings("unchecked")
	protected final Class<Entity> retrieveEntityClazz() {
		final var entityTypeToken = new TypeToken<Entity>(getClass()) {
			private static final long serialVersionUID = 1L;
		};
		final var rawType = (Class<Entity>) entityTypeToken.getRawType();
		LOGGER.debug("Class Entity '{}'", rawType);
		return rawType;
	}

	/**
	 * Capture the generic class type
	 * 
	 * @return A <code>Class<Entity></code> object
	 */
	@SuppressWarnings("unchecked")
	protected final Class<Id> retrieveIdClazz() {
		final var entityTypeToken = new TypeToken<Id>(getClass()) {
			private static final long serialVersionUID = 1L;
		};
		final var rawType = (Class<Id>) entityTypeToken.getRawType();
		LOGGER.debug("Class Id '{}'", rawType);
		return rawType;
	}

	/**
	 * Execute operations like save, delete, saveAll, etc.
	 * An operation is built of a main function, pre-function, pos-function, and
	 * error-function.
	 * 
	 * @param <Out>         The output type
	 * 
	 * @param operation     The operation data, used for logs
	 * @param preFunction   The function executed before the main function
	 * @param posFunction   The function executed after the main function
	 * @param mainFunction  The main function
	 * @param errorFunction The function executed after main function error
	 * @param directives    A set objects used to configure pre, main, pos, and
	 *                      error functions
	 * 
	 * @throws NullPointerException If any parameter is null
	 * 
	 * @return The function operation result
	 */
	protected <Out> Out execute(
			final InterfaceOperation operation,
			final Consumer<Object[]> preFunction,
			final BiFunction<Out, Object[], Out> posFunction,
			final Function<Object[], Out> mainFunction,
			final BiFunction<Exception, Object[], Exception> errorFunction,
			final Object... directives) {
		checkParamsNotNull(operation, preFunction, posFunction, mainFunction, errorFunction, directives);

		final var session = securityService.getSession();
		final var operationName = operation.getName();

		final var outName = operation.getReceiver().concat("Out");
		final var preOutName = PRE_LOG.concat(outName);
		final var finalOutName = FINAL_LOG.concat(outName);

		LOGGER.debug("Pre executing {} operation session '{}', and directives '{}'",
				operationName, session, directives);

		try {
			preFunction.accept(directives);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			LOGGER.debug(ERROR_ON_PRE_OPERATION_LOG, operationName, session, exceptionClass);
			throw exceptionAdapterService.convert(ex, i18nService, operation, getEntityClazz());
		}

		LOGGER.debug("Executing {} operation session '{}', and directives '{}'",
				operationName, session, directives);

		final Out out;
		try {
			out = mainFunction.apply(directives);
		} catch (final Exception ex) {
			final var finalException = errorFunction.apply(ex, directives);
			final var exceptionClass = getRootCause(finalException).getClass().getSimpleName();

			LOGGER.debug("Error on {} operation, session '{}', error '{}'", operationName, session, exceptionClass);
			throw exceptionAdapterService.convert(finalException, i18nService, operation, getEntityClazz());
		}

		LOGGER.debug(OPERATION_EXECUTED_LOG, operationName, outName, out, session, directives);

		final Out posOut;
		try {
			posOut = posFunction.apply(out, directives);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			LOGGER.debug(ERROR_ON_POS_OPERATION_LOG, operationName, finalOutName, out, session, exceptionClass);
			throw exceptionAdapterService.convert(ex, i18nService, operation, getEntityClazz());
		}

		final var finalOut = ofNullable(posOut)
				.orElseThrow(createNullPointerException(i18nService, preOutName));

		LOGGER.debug(POS_OPERATION_LOG, operationName, finalOutName, finalOut, session, directives);
		return finalOut;
	}

	/**
	 * Execute operations like save, delete, saveAll, etc.
	 * An operation is built of a main function, pre-function, pos-function, and
	 * error-function.
	 * 
	 * @param <In>          The input type
	 * @param <Out>         The output type
	 * 
	 * @param in            The operation input
	 * @param operation     The operation data, used for logs
	 * @param preFunction   The function executed before the main function
	 * @param posFunction   The function executed after the main function
	 * @param mainFunction  The main function
	 * @param errorFunction The function executed after main function error
	 * @param directives    A set objects used to configure pre, main, pos, and
	 *                      error functions
	 * 
	 * @throws NullPointerException If any parameter is null
	 * 
	 * @return The function operation result
	 */
	protected <In, Out> Out execute(
			final In in,
			final InterfaceOperation operation,
			final BiFunction<In, Object[], In> preFunction,
			final BiFunction<Out, Object[], Out> posFunction,
			final BiFunction<In, Object[], Out> mainFunction,
			final TriFunction<In, Exception, Object[], Exception> errorFunction,
			final Object... directives) {
		checkNotNull(in, "Please pass a non-null 'in'");
		checkParamsNotNull(operation, preFunction, posFunction, mainFunction, errorFunction, directives);

		final var session = securityService.getSession();
		final var operationName = operation.getName();

		final var inName = operation.getReceiver().concat("In");
		final var preInName = PRE_LOG.concat(inName);
		final var finalInName = FINAL_LOG.concat(inName);

		final var outName = operation.getReceiver().concat("Out");
		final var preOutName = PRE_LOG.concat(outName);
		final var finalOutName = FINAL_LOG.concat(outName);

		LOGGER.debug("Pre executing {} operation with {} '{}', session '{}', and directives '{}'",
				operationName, inName, in, session, directives);

		final In preIn;
		try {
			preIn = preFunction.apply(in, directives);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			LOGGER.debug(ERROR_ON_PRE_OPERATION_LOG, operationName, finalInName, in, session, exceptionClass);
			throw exceptionAdapterService.convert(ex, i18nService, operation, getEntityClazz(), in);
		}

		final var finalIn = ofNullable(preIn)
				.orElseThrow(createNullPointerException(i18nService, preInName));

		LOGGER.debug("Executing {} operation with {} '{}', session '{}', and directives '{}'",
				operationName, finalInName, finalIn, session, directives);

		final Out out;
		try {
			out = mainFunction.apply(finalIn, directives);
		} catch (final Exception ex) {
			final var finalException = errorFunction.apply(finalIn, ex, directives);
			final var exceptionClass = getRootCause(finalException).getClass().getSimpleName();

			LOGGER.debug(ERROR_ON_OPERATION_LOG, operationName, finalInName, finalIn, session, exceptionClass);

			throw exceptionAdapterService.convert(
					finalException, i18nService, operation, getEntityClazz(), finalIn);
		}

		LOGGER.debug(OPERATION_EXECUTED_LOG, operationName, outName, out, session, directives);

		final Out posOut;
		try {
			posOut = posFunction.apply(out, directives);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			LOGGER.debug(ERROR_ON_POS_OPERATION_LOG, operationName, finalOutName, out, session, exceptionClass);
			throw exceptionAdapterService.convert(ex, i18nService, operation, getEntityClazz(), in);
		}

		final var finalOut = ofNullable(posOut)
				.orElseThrow(createNullPointerException(i18nService, preOutName));

		LOGGER.debug(POS_OPERATION_LOG, operationName, finalOutName, finalOut, session, directives);
		return finalOut;
	}

	/**
	 * Execute operations like save, delete, saveAll, etc.
	 * An operation is built of a main function, pre-function, pos-function, and
	 * error-function.
	 * 
	 * @param <In1>         The first input type
	 * @param <In2>         The second input type
	 * @param <Out>         The output type
	 * 
	 * @param in1           The first operation input
	 * @param in2           The second operation input
	 * @param operation     The operation data, used for logs
	 * @param preFunction   The function executed before the main function
	 * @param posFunction   The function executed after the main function
	 * @param mainFunction  The main function
	 * @param errorFunction The function executed after main function error
	 * @param directives    A set objects used to configure pre, main, pos, and
	 *                      error functions
	 * 
	 * @throws NullPointerException If any parameter is null
	 * 
	 * @return The function operation result
	 */
	protected <In1, In2, Out> Out execute(
			final In1 in1,
			final In2 in2,
			final InterfaceOperation operation,
			final TriFunction<In1, In2, Object[], Entry<In1, In2>> preFunction,
			final BiFunction<Out, Object[], Out> posFunction,
			final TriFunction<In1, In2, Object[], Out> mainFunction,
			final QuadFunction<In1, In2, Exception, Object[], Exception> errorFunction,
			final Object... directives) {
		checkNotNull(in1, "Please pass a non-null 'in1'");
		checkNotNull(in2, "Please pass a non-null 'in2'");
		checkParamsNotNull(operation, preFunction, posFunction, mainFunction, errorFunction, directives);

		final var session = securityService.getSession();
		final var operationName = operation.getName();

		final var inName = operation.getReceiver().concat("In");
		final var preInName = PRE_LOG.concat(inName);
		final var finalInName = FINAL_LOG.concat(inName);

		final var outName = operation.getReceiver().concat("Out");
		final var preOutName = PRE_LOG.concat(outName);
		final var finalOutName = FINAL_LOG.concat(outName);

		LOGGER.debug("Pre executing {} operation with {} '{}', session '{}', and directives '{}'",
				operationName, inName, in1, session, directives);

		final In1 preIn1;
		final In2 preIn2;
		try {
			final var preInEntry = preFunction.apply(in1, in2, directives);
			preIn1 = preInEntry.getKey();
			preIn2 = preInEntry.getValue();
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			LOGGER.debug(ERROR_ON_PRE_OPERATION_LOG, operationName, finalInName, "", session, exceptionClass);
			throw exceptionAdapterService.convert(ex, i18nService, operation, getEntityClazz(), in1, in2);
		}

		final var finalIn1 = ofNullable(preIn1)
				.orElseThrow(createNullPointerException(i18nService, preInName));

		final var finalIn2 = ofNullable(preIn2)
				.orElseThrow(createNullPointerException(i18nService, preInName));

		LOGGER.debug("Executing {} operation with {} '{}', session '{}', and directives '{}'",
				operationName, finalInName, finalIn1, session, directives);

		final Out out;
		try {
			out = mainFunction.apply(finalIn1, finalIn2, directives);
		} catch (final Exception ex) {
			final var finalException = errorFunction.apply(finalIn1, finalIn2, ex, directives);
			final var exceptionClass = getRootCause(finalException).getClass().getSimpleName();
			LOGGER.debug(ERROR_ON_OPERATION_LOG, operationName, finalInName, finalIn1, session, exceptionClass);
			throw exceptionAdapterService.convert(finalException, i18nService, operation, getEntityClazz(), finalIn1);
		}

		LOGGER.debug(OPERATION_EXECUTED_LOG, operationName, outName, out, session, directives);

		final Out posOut;
		try {
			posOut = posFunction.apply(out, directives);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			LOGGER.debug(ERROR_ON_POS_OPERATION_LOG, operationName, finalOutName, out, session, exceptionClass);
			throw exceptionAdapterService.convert(ex, i18nService, operation, getEntityClazz());
		}

		final var finalOut = ofNullable(posOut)
				.orElseThrow(createNullPointerException(i18nService, preOutName));

		LOGGER.debug(POS_OPERATION_LOG, operationName, finalOutName, finalOut, session, directives);
		return finalOut;
	}

	/**
	 * Execute a collection of functions in sequence, used in pre and pos operations
	 * (preSave, posUpdate, etc.)
	 * 
	 * @param functions  A collection of <code>FacadeFunction</code> objects
	 * @param directives Objects used to perform the functions
	 * 
	 * @throws NullPointerException If any parameter is null
	 */
	protected void compose(
			final Collection<FacadeFunctionNoArgs> functions,
			final Object... directives) {
		checkNotNull(functions, NON_NULL_FUNCTIONS_MSG);
		checkNotNull(directives, NON_NULL_DIRECTIVES_MSG);

		if (ObjectUtils.isEmpty(functions)) {
			LOGGER.debug("No functions to execute with directires {}", directives);
			return;
		}

		final var allFunctionsName = functions.stream()
				.map(BaseFunction::getName)
				.collect(joining(", "));

		final var skippedFunctions = new ArrayList<String>();
		final var withErrorFunctions = new ArrayList<String>();
		final var executedFunctions = new ArrayList<String>();

		LOGGER.debug("Execute functions {} with directires {}", allFunctionsName, directives);

		for (final var function : functions) {
			final var functionName = function.getName();
			try {
				if (!function.isActice()) {
					LOGGER.debug("Function {} disabled, it won't execute", functionName);
					skippedFunctions.add(functionName);
					continue;
				}
				function.accept(directives);
				LOGGER.debug("Function {} executed", functionName);
				executedFunctions.add(functionName);
			} catch (final Exception ex) {
				if (function.reTrowException()) {
					throw ex;
				}
				withErrorFunctions.add(functionName);
				final var rootCause = ExceptionUtils.getRootCause(ex);
				final var rootCauseMessage = rootCause.getMessage();
				LOGGER.debug("Function {} with execution error {}", functionName, rootCauseMessage);
			}
		}

		LOGGER.debug("Functions {} executed, skipped {}, with errors {}",
				executedFunctions, skippedFunctions, withErrorFunctions);
	}

	/**
	 * Execute a collection of functions in sequence, used in pre and pos operations
	 * (preSave, posUpdate, etc.)
	 * 
	 * @param <In>       The function input type
	 * 
	 * @param in         The function input object, the value used to create the
	 *                   result
	 * @param functions  A collection of <code>FacadeBiFunction</code> objects
	 * @param directives Objects used to perform the functions
	 * 
	 * @throws NullPointerException If any parameter is null
	 * 
	 * @return A updated <code>in</code> object
	 */
	@NotNull
	protected <In> In compose(
			final In in,
			final Collection<FacadeFunctionOneArg<In>> functions,
			final Object... directives) {
		checkNotNull(in, "Please pass a non-null 'in'");
		checkNotNull(functions, NON_NULL_FUNCTIONS_MSG);
		checkNotNull(directives, NON_NULL_DIRECTIVES_MSG);

		if (ObjectUtils.isEmpty(functions)) {
			LOGGER.debug("No functions to execute with input {} and directires {}", in, directives);
			return in;
		}

		final var allFunctionsName = functions.stream()
				.map(BaseFunction::getName)
				.collect(joining(", "));

		final var skippedFunctions = new ArrayList<String>();
		final var withErrorFunctions = new ArrayList<String>();
		final var executedFunctions = new ArrayList<String>();

		LOGGER.debug("Execute functions {} with input {} and directires {}", allFunctionsName, in, directives);

		var nextIn = in;
		for (final var function : functions) {
			final var functionName = function.getName();
			try {
				if (!function.isActice()) {
					LOGGER.debug("Function {} with input {} disabled, it won't execute", functionName, nextIn);
					skippedFunctions.add(functionName);
					continue;
				}
				nextIn = function.apply(nextIn, directives);
				LOGGER.debug("Function {} executed, result {}", functionName, nextIn);
				executedFunctions.add(functionName);
			} catch (final Exception ex) {
				if (function.reTrowException()) {
					throw ex;
				}
				withErrorFunctions.add(functionName);
				final var rootCause = ExceptionUtils.getRootCause(ex);
				final var rootCauseMessage = rootCause.getMessage();
				LOGGER.debug("Function {} with execution error {}", functionName, rootCauseMessage);
			}
		}

		LOGGER.debug("Functions {} executed, skipped {}, with errors {}",
				executedFunctions,
				skippedFunctions,
				withErrorFunctions);
		return nextIn;
	}

	/**
	 * Execute a collection of functions in sequence, used in pre, pos, and error
	 * operations (preSave, posUpdate, errorDelete, etc.)
	 * 
	 * @param <In1>      The first function input type
	 * @param <In2>      The second function input type
	 * 
	 * @param in1        The first function input object, the value used to create
	 *                   the result
	 * @param in2        The second function input object, it is used to help the
	 *                   result
	 * @param functions  A collection of <code>	</code> objects
	 * @param directives Objects used to perform the functions
	 * 
	 * @throws NullPointerException If any parameter is null
	 * 
	 * @return A updated <code>in1</code> object
	 */
	@NotNull
	protected <In1, In2> In1 compose(
			final In1 in1,
			final In2 in2,
			final Collection<FacadeFunctionTwoArgs<In1, In2>> functions,
			final Object... directives) {
		checkNotNull(in1, "Please pass a non-null 'in1'");
		checkNotNull(in2, "Please pass a non-null 'in2'");
		checkNotNull(functions, NON_NULL_FUNCTIONS_MSG);
		checkNotNull(directives, NON_NULL_DIRECTIVES_MSG);

		if (ObjectUtils.isEmpty(functions)) {
			LOGGER.debug("No functions to execute with inputs [{} {}] and directires {}",
					in1, in2, directives);
			return in1;
		}

		final var allFunctionsName = functions.stream()
				.map(BaseFunction::getName)
				.collect(joining(", "));

		final var skippedFunctions = new ArrayList<String>();
		final var withErrorFunctions = new ArrayList<String>();
		final var executedFunctions = new ArrayList<String>();

		LOGGER.debug("Execute functions {} with inputs [{} {}] and directires {}",
				allFunctionsName, in1, in2, directives);

		In1 nextIn1 = in1;
		for (final var function : functions) {
			final var functionName = function.getName();
			try {
				if (!function.isActice()) {
					LOGGER.debug("Function {} with input {} disabled, it won't execute", functionName, nextIn1);
					skippedFunctions.add(functionName);
					continue;
				}
				nextIn1 = function.apply(nextIn1, in2, directives);
				LOGGER.debug("Function {} executed, result {}", functionName, nextIn1);
				executedFunctions.add(functionName);
			} catch (final Exception ex) {
				if (function.reTrowException()) {
					throw ex;
				}
				withErrorFunctions.add(functionName);
				final var rootCauseMessage = ExceptionUtils.getRootCauseMessage(ex);
				LOGGER.debug("Function {} with execution error {}", functionName, rootCauseMessage);
			}
		}

		LOGGER.debug("Functions {} executed, skipped {}, with errors {}",
				executedFunctions, skippedFunctions, withErrorFunctions);
		return nextIn1;
	}

	private void checkParamsNotNull(
			final Object operation,
			final Object preFunction,
			final Object posFunction,
			final Object mainFunction,
			final Object errorFunction,
			final Object[] directives) {
		checkNotNull(operation, NON_NULL_OPERATION_MSG);
		checkNotNull(preFunction, NON_NULL_PRE_FUNCTION_MSG);
		checkNotNull(posFunction, NON_NULL_POS_FUNCTION_MSG);
		checkNotNull(mainFunction, NON_NULL_MAIN_FUNCTION_MSG);
		checkNotNull(errorFunction, NON_NULL_ERROR_FUNCTION_MSG);
		checkNotNull(directives, NON_NULL_DIRECTIVES_MSG);
	}

	/**
	 * Return the method's caller name.
	 * 
	 * @return A String with caller name or empty string if didn't
	 */
	protected final String getCallerName() {
		return walker.walk(s -> s
				.skip(2) // Skip getCallerName() and clientMethod() itself
				.findFirst()
				.map(StackWalker.StackFrame::getMethodName))
				.orElse(StringUtils.EMPTY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Class<Id> getIdClazz() {
		return idClazz;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Class<Entity> getEntityClazz() {
		return entityClazz;
	}

	@NotNull
	public final InterfaceI18nService getI18nService() {
		return i18nService;
	}

	@NotNull
	public final InterfaceSecurityService getSecurityService() {
		return securityService;
	}

	@NotNull
	public final InterfaceExceptionAdapterService getExceptionTranslatorService() {
		return exceptionAdapterService;
	}
}
