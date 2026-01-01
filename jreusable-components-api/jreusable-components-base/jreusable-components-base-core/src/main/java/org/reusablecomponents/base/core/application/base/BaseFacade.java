package org.reusablecomponents.base.core.application.base;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;

import static org.reusablecomponents.base.core.application.base.BaseFacadeMessage.NON_NULL_DIRECTIVES_MSG;
import static org.reusablecomponents.base.core.application.base.BaseFacadeMessage.NON_NULL_ERROR_FUNCTION_MSG;
import static org.reusablecomponents.base.core.application.base.BaseFacadeMessage.NON_NULL_MAIN_FUNCTION_MSG;
import static org.reusablecomponents.base.core.application.base.BaseFacadeMessage.NON_NULL_POS_FUNCTION_MSG;
import static org.reusablecomponents.base.core.application.base.BaseFacadeMessage.NON_NULL_PRE_FUNCTION_MSG;

import static org.reusablecomponents.base.core.infra.util.function.FunctionCommonUtils.createNullPointerException;

import java.util.Objects;

import org.reusablecomponents.base.core.application.command.entity.AbstractCommandFacade;
import org.reusablecomponents.base.core.application.empty.EmptyFacade;
import org.reusablecomponents.base.core.application.query.entity.paginationspecification.AbstractQueryPaginationSpecificationFacade;
import org.reusablecomponents.base.core.application.query.entity.simple.AbstractQueryFacade;
import org.reusablecomponents.base.core.application.query.entity.specification.AbstractQuerySpecificationFacade;
import org.reusablecomponents.base.core.application.query.entity.pagination.AbstractQueryPaginationFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.core.infra.exception.common.BaseException;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction1Args;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction2Args;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction3Args;
import org.reusablecomponents.base.core.infra.util.function.operation.OperationFunction4Args;
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
		permits EmptyFacade,
		AbstractCommandFacade, AbstractQueryFacade,
		AbstractQuerySpecificationFacade,
		AbstractQueryPaginationFacade,
		AbstractQueryPaginationSpecificationFacade {

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
		LOGGER.debug("Constructing BaseFacade with builder {}", builder);
		final var finalBuilder = ofNullable(builder)
				.orElseThrow(createNullPointerException("builder"));

		this.entityClazz = retrieveEntityClazz();
		this.idClazz = retrieveIdClazz();

		this.i18nService = finalBuilder.i18nService;
		this.securityService = finalBuilder.securityService;
		this.exceptionAdapterService = finalBuilder.exceptionAdapterService;

		LOGGER.debug("BaseFacade constructed");
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
			final OperationFunction1Args<Object[], Object[]> preFunction,
			final OperationFunction1Args<Object[], Out> mainFunction,
			final OperationFunction2Args<Out, Object[], Out> posFunction,
			final OperationFunction2Args<BaseException, Object[], BaseException> errorFunction,
			final Object... directives) {
		checkParamsNotNull(preFunction, posFunction, mainFunction, errorFunction, directives);

		final var preFunctionName = preFunction.getName();
		final var mainFunctionName = mainFunction.getName();
		final var posFunctionName = posFunction.getName();
		final var errorFunctionName = errorFunction.getName();
		final var session = securityService.getSession();

		LOGGER.debug(
				"Start the execute with session '{}', pre-function '{}', main-function '{}', pos-function '{}', error-function '{}', and directives '{}'",
				session, preFunctionName, mainFunctionName, posFunctionName, errorFunctionName, directives);

		final Object[] finalDirectives;
		try {
			LOGGER.debug("Executing {} pre-function with session '{}', and directives '{}'",
					preFunctionName, session, directives);
			finalDirectives = preFunction.apply(directives);
			LOGGER.debug("{} pre-function executed with session '{}', and directives '{}', and finalDirectives '{}'",
					preFunctionName, session, directives, finalDirectives);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			final var convertedException = exceptionAdapterService.convert(
					ex, i18nService, preFunction, getEntityClazz());

			LOGGER.debug("Error on {} pre-function with session '{}', exception '{}', converted exception '{}'",
					preFunctionName, session, exceptionClass, convertedException.getClass().getSimpleName());
			throw convertedException;
		}

		final Out out;
		try {
			LOGGER.debug("Executing {} main-function with session '{}', and directives '{}'",
					mainFunctionName, session, finalDirectives);
			out = mainFunction.apply(finalDirectives);
			LOGGER.debug("{} main-function executed with session '{}', directives '{}', and out '{}'",
					mainFunctionName, session, finalDirectives, out);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			final var convertedException = exceptionAdapterService.convert(
					ex, i18nService, mainFunction, getEntityClazz(), finalDirectives);

			LOGGER.debug("Error on {} main-function with session '{}', exception '{}', converted exception '{}'",
					mainFunctionName, session, exceptionClass);
			throw errorFunction.apply(convertedException, directives);
		}

		final Out finalOut;
		try {
			LOGGER.debug("Executing {} pos-function with session '{}', and directives '{}', out '{}'",
					posFunctionName, session, finalDirectives, out);
			finalOut = posFunction.apply(out, finalDirectives);
			LOGGER.debug("{} pos-function executed with session '{}', directives '{}', and finalOut '{}'",
					posFunctionName, session, finalDirectives, finalOut);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			final var convertedException = exceptionAdapterService.convert(
					ex, i18nService, posFunction, getEntityClazz(), out);

			LOGGER.debug(
					"Error on {} pos-function with session '{}', out '{}', exception '{}', and converted exception '{}'",
					posFunctionName, session, out, exceptionClass, convertedException.getClass().getSimpleName());
			throw convertedException;
		}

		if (Objects.isNull(finalOut)) {
			createNullPointerException(i18nService, "finalOut (from posOutFunction)");
		}

		LOGGER.debug(
				"Execute finalized with session '{}', out '{}', pre-function '{}', main-function '{}', pos-function '{}', error-function, and directives '{}'",
				session, finalOut, preFunctionName, mainFunctionName, posFunctionName, errorFunctionName, directives);
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
			final OperationFunction2Args<In, Object[], In> preFunction,
			final OperationFunction2Args<In, Object[], Out> mainFunction,
			final OperationFunction2Args<Out, Object[], Out> posFunction,
			final OperationFunction3Args<BaseException, In, Object[], BaseException> errorFunction,
			final Object... directives) {
		checkNotNull(in, "Please pass a non-null 'in'");
		checkParamsNotNull(preFunction, posFunction, mainFunction, errorFunction, directives);

		final var preFunctionName = preFunction.getName();
		final var mainFunctionName = mainFunction.getName();
		final var posFunctionName = posFunction.getName();
		final var errorFunctionName = errorFunction.getName();
		final var session = securityService.getSession();

		LOGGER.debug(
				"Start the execute with session '{}', in '{}', pre-function '{}', main-function '{}', pos-function '{}', error-function '{}', and directives '{}'",
				session, in, preFunctionName, mainFunctionName, posFunctionName, errorFunctionName, directives);

		final In finalIn;
		try {
			LOGGER.debug("Executing {} pre-function with session '{}', in '{}', and directives '{}'",
					preFunctionName, session, in, directives);
			finalIn = preFunction.apply(in, directives);
			LOGGER.debug("{} pre-function executed with session '{}', finalIn '{}', and directives '{}'",
					preFunctionName, session, finalIn, directives);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			final var convertedException = exceptionAdapterService.convert(
					ex, i18nService, mainFunction, getEntityClazz(), in);
			LOGGER.debug(
					"Error on {} pre-function with session '{}', in '{}', exception '{}', converted exception '{}'",
					preFunctionName, session, in, exceptionClass, convertedException.getClass().getSimpleName());
			throw convertedException;
		}

		final Out out;
		try {
			LOGGER.debug("Executing {} main-function with session '{}', finalIn '{}', and directives '{}'",
					mainFunctionName, session, in, directives);
			out = mainFunction.apply(finalIn, directives);
			LOGGER.debug("{} main-function executed with session '{}', finalIn '{}', out '{}', and directives '{}'",
					mainFunctionName, session, finalIn, directives, out);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			final var convertedException = exceptionAdapterService.convert(
					ex, i18nService, mainFunction, getEntityClazz(), finalIn);
			LOGGER.debug(
					"Error on {} main-function with session '{}', finalIn '{}', exception '{}', converted exception '{}'",
					mainFunctionName, session, finalIn, exceptionClass, convertedException.getClass().getSimpleName());
			throw errorFunction.apply(convertedException, finalIn, directives);
		}

		final Out finalOut;
		try {
			LOGGER.debug("Executing {} pos-function with session '{}', out '{}', and directives '{}'",
					posFunctionName, session, out, directives);
			finalOut = posFunction.apply(out, directives);
			LOGGER.debug("{} pos-function executed with session '{}', finalOut '{}', and directives '{}'",
					posFunctionName, session, finalOut, directives);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			final var convertedException = exceptionAdapterService.convert(
					ex, i18nService, mainFunction, getEntityClazz(), finalIn, out);
			LOGGER.debug(
					"Error on {} pos-function with session '{}', in '{}', exception '{}', converted exception '{}'",
					posFunctionName, session, out, exceptionClass, convertedException.getClass().getSimpleName());
			throw convertedException;
		}

		if (Objects.isNull(finalOut)) {
			createNullPointerException(i18nService, "finalOut (from posOutFunction)");
		}

		LOGGER.debug(
				"Execute finalized with session '{}', in '{}', out '{}', preFunction '{}', mainFunction '{}', posFunction '{}', errorFunction '{}', and directives '{}'",
				session, in, out, preFunctionName, mainFunctionName, posFunctionName, errorFunctionName, directives);
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
			final OperationFunction3Args<In1, In2, Object[], In1> preFunction,
			final OperationFunction3Args<In1, In2, Object[], Out> mainFunction,
			final OperationFunction2Args<Out, Object[], Out> posFunction,
			final OperationFunction4Args<BaseException, In1, In2, Object[], BaseException> errorFunction,
			final Object... directives) {
		checkNotNull(in1, "Please pass a non-null 'in1'");
		checkNotNull(in2, "Please pass a non-null 'in2'");
		checkParamsNotNull(preFunction, posFunction, mainFunction, errorFunction, directives);

		final var preFunctionName = preFunction.getName();
		final var mainFunctionName = mainFunction.getName();
		final var posFunctionName = posFunction.getName();
		final var errorFunctionName = errorFunction.getName();
		final var session = securityService.getSession();

		LOGGER.debug(
				"Start the execute with session '{}', in1 '{}', in2 '{}', pre-function '{}', main-function '{}', pos-function '{}', error-function '{}', and directives '{}'",
				session, in1, in2, preFunctionName, mainFunctionName, posFunctionName, errorFunctionName, directives);

		final In1 finalIn1;
		try {
			LOGGER.debug("Executing {} pre-function with session '{}', in1 '{}', in2 '{}', and directives '{}'",
					preFunctionName, session, in1, in2, directives);
			finalIn1 = preFunction.apply(in1, in2, directives);
			LOGGER.debug("{} pre-function executed with session '{}', finalIn1 '{}', in2 '{}', and directives '{}'",
					preFunctionName, session, finalIn1, in2, directives);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			final var convertedException = exceptionAdapterService.convert(
					ex, i18nService, mainFunction, getEntityClazz(), in1, in2);
			LOGGER.debug(
					"Error on {} pre-function with session '{}', in1 '{}', in2 '{}', exception '{}', converted exception '{}'",
					preFunctionName, session, in1, in2, exceptionClass, convertedException.getClass().getSimpleName());
			throw convertedException;
		}

		final Out out;
		try {
			LOGGER.debug("Executing {} main-function with session '{}', finalIn1 '{}', in2 '{}', and directives '{}'",
					mainFunctionName, session, in1, in2, directives);
			out = mainFunction.apply(finalIn1, in2, directives);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			final var convertedException = exceptionAdapterService.convert(
					ex, i18nService, mainFunction, getEntityClazz(), finalIn1, in2);
			LOGGER.debug(
					"Error on {} main-function with session '{}', finalIn1 '{}', in2 '{}', exception '{}', converted exception '{}'",
					mainFunctionName, session, finalIn1, exceptionClass, convertedException.getClass().getSimpleName());
			throw errorFunction.apply(convertedException, finalIn1, in2, directives);
		}

		final Out finalOut;
		try {
			LOGGER.debug("Executing {} pos-function with session '{}', out '{}', and directives '{}'",
					posFunctionName, session, out, directives);
			finalOut = posFunction.apply(out, directives);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			final var convertedException = exceptionAdapterService.convert(
					ex, i18nService, mainFunction, getEntityClazz(), finalIn1, in2, out);
			LOGGER.debug(
					"Error on {} pos-function with session '{}', in '{}', exception '{}', converted exception '{}'",
					posFunctionName, session, out, exceptionClass, convertedException.getClass().getSimpleName());
			throw convertedException;
		}

		if (Objects.isNull(finalOut)) {
			createNullPointerException(i18nService, "finalOut (from posOutFunction)");
		}

		LOGGER.debug(
				"Execute finalized with session '{}', finalIn1 '{}', in2 '{}', out '{}', preFunction '{}', mainFunction '{}', posFunction '{}', errorFunction '{}', and directives '{}'",
				session, finalIn1, in2, out, preFunctionName, mainFunctionName,
				posFunctionName, errorFunctionName, directives);
		return finalOut;
	}

	private void checkParamsNotNull(
			final Object preFunction,
			final Object posFunction,
			final Object mainFunction,
			final Object errorFunction,
			final Object[] directives) {
		checkNotNull(preFunction, NON_NULL_PRE_FUNCTION_MSG);
		checkNotNull(posFunction, NON_NULL_POS_FUNCTION_MSG);
		checkNotNull(mainFunction, NON_NULL_MAIN_FUNCTION_MSG);
		checkNotNull(errorFunction, NON_NULL_ERROR_FUNCTION_MSG);
		checkNotNull(directives, NON_NULL_DIRECTIVES_MSG);
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
