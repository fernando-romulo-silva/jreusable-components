package org.reusablecomponents.base.core.application.base;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;
import static org.reusablecomponents.base.core.application.base.BaseFacadeMessage.ERROR_ON_OPERATION_LOG;
import static org.reusablecomponents.base.core.application.base.BaseFacadeMessage.ERROR_ON_POS_OPERATION_LOG;
import static org.reusablecomponents.base.core.application.base.BaseFacadeMessage.ERROR_ON_PRE_OPERATION_LOG;
import static org.reusablecomponents.base.core.application.base.BaseFacadeMessage.FINAL_LOG;
import static org.reusablecomponents.base.core.application.base.BaseFacadeMessage.NON_NULL_DIRECTIVES_MSG;
import static org.reusablecomponents.base.core.application.base.BaseFacadeMessage.NON_NULL_ERROR_FUNCTION_MSG;
import static org.reusablecomponents.base.core.application.base.BaseFacadeMessage.NON_NULL_MAIN_FUNCTION_MSG;
import static org.reusablecomponents.base.core.application.base.BaseFacadeMessage.NON_NULL_POS_FUNCTION_MSG;
import static org.reusablecomponents.base.core.application.base.BaseFacadeMessage.NON_NULL_PRE_FUNCTION_MSG;
import static org.reusablecomponents.base.core.application.base.BaseFacadeMessage.OPERATION_EXECUTED_LOG;
import static org.reusablecomponents.base.core.application.base.BaseFacadeMessage.POS_OPERATION_LOG;
import static org.reusablecomponents.base.core.application.base.BaseFacadeMessage.PRE_LOG;
import static org.reusablecomponents.base.core.infra.util.function.FunctionCommonUtils.createNullPointerException;

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

		final var session = securityService.getSession();
		final var operationName = mainFunction.getName();

		final var outName = mainFunction.getName();
		final var preOutName = PRE_LOG.concat(outName);
		final var finalOutName = FINAL_LOG.concat(outName);

		LOGGER.debug("Pre executing {} operation session '{}', and directives '{}'",
				operationName, session, directives);

		final Object[] finalDirectives;
		try {
			finalDirectives = preFunction.apply(directives);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			LOGGER.debug(ERROR_ON_PRE_OPERATION_LOG, operationName, session, exceptionClass);
			throw exceptionAdapterService.convert(ex, i18nService, mainFunction, getEntityClazz());
		}

		LOGGER.debug("Executing {} operation session '{}', and directives '{}'",
				operationName, session, directives);

		final Out out;
		try {
			out = mainFunction.apply(finalDirectives);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			LOGGER.debug("Error on {} operation, session '{}', error '{}'", operationName, session, exceptionClass);

			final var baseException = exceptionAdapterService.convert(ex, i18nService, mainFunction, getEntityClazz());
			throw errorFunction.apply(baseException, directives);
		}

		LOGGER.debug(OPERATION_EXECUTED_LOG, operationName, outName, null, session, directives);

		final Out posOut;
		try {
			posOut = posFunction.apply(out, directives);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			LOGGER.debug(ERROR_ON_POS_OPERATION_LOG, operationName, finalOutName, session, exceptionClass);
			throw exceptionAdapterService.convert(ex, i18nService, mainFunction, getEntityClazz());
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
			final OperationFunction2Args<In, Object[], In> preFunction,
			final OperationFunction2Args<In, Object[], Out> mainFunction,
			final OperationFunction2Args<Out, Object[], Out> posFunction,
			final OperationFunction3Args<BaseException, In, Object[], BaseException> errorFunction,
			final Object... directives) {
		checkNotNull(in, "Please pass a non-null 'in'");
		checkParamsNotNull(preFunction, posFunction, mainFunction, errorFunction, directives);

		final var session = securityService.getSession();
		final var operationName = mainFunction.getName();

		final var inName = in.getClass().getSimpleName().concat("In");
		final var preInName = PRE_LOG.concat(inName);
		final var finalInName = FINAL_LOG.concat(inName);

		LOGGER.debug("Pre executing {} operation with {} '{}', session '{}', and directives '{}'",
				operationName, inName, in, session, directives);

		final In preIn;
		try {
			preIn = preFunction.apply(in, directives);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			LOGGER.debug(ERROR_ON_PRE_OPERATION_LOG, operationName, finalInName, in, session, exceptionClass);
			throw exceptionAdapterService.convert(ex, i18nService, mainFunction, getEntityClazz(), in);
		}

		final var finalIn = ofNullable(preIn)
				.orElseThrow(createNullPointerException(i18nService, preInName));

		LOGGER.debug("Executing {} operation with {} '{}', session '{}', and directives '{}'",
				operationName, finalInName, finalIn, session, directives);

		final Out out;
		try {
			out = mainFunction.apply(finalIn, directives);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			LOGGER.debug(ERROR_ON_OPERATION_LOG, operationName, finalInName, finalIn, session, exceptionClass);

			final var baseException = exceptionAdapterService.convert(
					ex, i18nService, mainFunction, getEntityClazz(), finalIn);

			throw errorFunction.apply(baseException, finalIn, directives);
		}

		final var outName = out.getClass().getSimpleName().concat("Out");
		final var preOutName = PRE_LOG.concat(outName);
		final var finalOutName = FINAL_LOG.concat(outName);

		LOGGER.debug(OPERATION_EXECUTED_LOG, operationName, outName, out, session, directives);

		final Out posOut;
		try {
			posOut = posFunction.apply(out, directives);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			LOGGER.debug(ERROR_ON_POS_OPERATION_LOG, operationName, finalOutName, out, session, exceptionClass);
			throw exceptionAdapterService.convert(ex, i18nService, mainFunction, getEntityClazz(), in);
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
			final OperationFunction3Args<In1, In2, Object[], In1> preFunction,
			final OperationFunction3Args<In1, In2, Object[], Out> mainFunction,
			final OperationFunction2Args<Out, Object[], Out> posFunction,
			final OperationFunction4Args<BaseException, In1, In2, Object[], BaseException> errorFunction,
			final Object... directives) {
		checkNotNull(in1, "Please pass a non-null 'in1'");
		checkNotNull(in2, "Please pass a non-null 'in2'");
		checkParamsNotNull(preFunction, posFunction, mainFunction, errorFunction, directives);

		final var session = securityService.getSession();
		final var operationName = mainFunction.getName();

		final var inName = in1.getClass().getSimpleName().concat("In");
		final var preInName = PRE_LOG.concat(inName);
		final var finalInName = FINAL_LOG.concat(inName);

		LOGGER.debug("Pre executing {} operation with {} '{}', session '{}', and directives '{}'",
				operationName, inName, in1, session, directives);

		var preIn1 = in1;
		try {
			preIn1 = preFunction.apply(preIn1, in2, directives);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			LOGGER.debug(ERROR_ON_PRE_OPERATION_LOG, operationName, finalInName, "", session, exceptionClass);
			throw exceptionAdapterService.convert(ex, i18nService, mainFunction, getEntityClazz(), in1, in2);
		}

		final var finalIn1 = ofNullable(preIn1)
				.orElseThrow(createNullPointerException(i18nService, preInName));

		LOGGER.debug("Executing {} operation with {} '{}', session '{}', and directives '{}'",
				operationName, finalInName, finalIn1, session, directives);

		final Out out;
		try {
			out = mainFunction.apply(finalIn1, in2, directives);
		} catch (final Exception ex) {
			final var finalException = exceptionAdapterService.convert(
					ex, i18nService, mainFunction, getEntityClazz(), finalIn1);

			errorFunction.apply(finalException, finalIn1, in2, directives);

			final var exceptionClass = finalException.getClass().getSimpleName();
			LOGGER.debug(ERROR_ON_OPERATION_LOG, operationName, finalInName, finalIn1, session, exceptionClass);
			throw finalException;
		}

		final var outName = out.getClass().getSimpleName().concat("Out");
		final var preOutName = PRE_LOG.concat(outName);
		final var finalOutName = FINAL_LOG.concat(outName);

		LOGGER.debug(OPERATION_EXECUTED_LOG, operationName, outName, out, session, directives);

		final Out posOut;
		try {
			posOut = posFunction.apply(out, directives);
		} catch (final Exception ex) {
			final var exceptionClass = getRootCause(ex).getClass().getSimpleName();
			LOGGER.debug(ERROR_ON_POS_OPERATION_LOG, operationName, finalOutName, out, session, exceptionClass);
			throw exceptionAdapterService.convert(ex, i18nService, mainFunction, getEntityClazz());
		}

		final var finalOut = ofNullable(posOut)
				.orElseThrow(createNullPointerException(i18nService, preOutName));

		LOGGER.debug(POS_OPERATION_LOG, operationName, finalOutName, finalOut, session, directives);
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
