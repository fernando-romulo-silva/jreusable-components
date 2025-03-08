package org.reusablecomponents.base.core.application.base;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;
import static org.reusablecomponents.base.core.infra.util.Functions.createNullPointerException;

import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.apache.commons.lang3.function.TriFunction;
import org.reusablecomponents.base.core.application.command.entity.CommandFacade;
import org.reusablecomponents.base.core.application.empty.EmptyFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.QueryFacade;
import org.reusablecomponents.base.core.application.query.entity.nonpaged.QuerySpecificationFacade;
import org.reusablecomponents.base.core.application.query.entity.paged.QueryPaginationFacade;
import org.reusablecomponents.base.core.application.query.entity.paged.QueryPaginationSpecificationFacade;
import org.reusablecomponents.base.core.domain.AbstractEntity;
import org.reusablecomponents.base.core.infra.exception.InterfaceExceptionAdapterService;
import org.reusablecomponents.base.core.infra.util.QuadFunction;
import org.reusablecomponents.base.core.infra.util.operation.InterfaceOperationType;
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

	private static final String POS_OPERATION_LOG = "Pos {} operation with {} '{}', session '{}', and directives '{}'";

	private static final String OPERATION_EXECUTED_LOG = "{} operation executed, with {} '{}', session '{}', and directives '{}'";

	private static final String FINAL_LOG = "final";

	private static final String PRE_LOG = "pre";

	private static final String ERROR_ON_OPERATION_LOG = "Error on {} operation with {} '{}', session '{}', error '{}'";

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseFacade.class);

	// -------

	protected final InterfaceSecurityService securityService;

	protected final InterfaceI18nService i18nService;

	protected final InterfaceExceptionAdapterService exceptionAdapterService;

	protected final Class<Entity> entityClazz;

	protected final Class<Id> idClazz;

	// ------

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

	// ------

	/**
	 * Capture the entity class type
	 * 
	 * @return A <code>Class<Entity></code> object
	 */
	@SuppressWarnings("unchecked")
	private final Class<Entity> retrieveEntityClazz() {

		final var entityTypeToken = new TypeToken<Entity>(getClass()) {
			private static final long serialVersionUID = 1L;
		};

		final var rawType = (Class<Entity>) entityTypeToken.getRawType();

		LOGGER.debug("Class type '{}'", rawType);

		return rawType;
	}

	/**
	 * Capture the entity id class type
	 * 
	 * @return A <code>Class<Id></code> object
	 */
	@SuppressWarnings("unchecked")
	private final Class<Id> retrieveIdClazz() {

		final var idTypeToken = new TypeToken<Id>(getClass()) {
			private static final long serialVersionUID = 1L;
		};

		final var rawType = (Class<Id>) idTypeToken.getRawType();

		LOGGER.debug("Class type '{}'", rawType);

		return rawType;
	}

	/**
	 * Execute commons operations like save, delete, saveAll, etc operations
	 * (functions) in a same execution structure.
	 * 
	 * @param <In>            The input type
	 * @param <Out>           The output type
	 * @param in              The operation input
	 * @param type            The operation type, used to categorize and log
	 *                        informations
	 * @param preOperation    The function executed before the operation
	 * @param posOperationThe The function executed after the operation
	 * @param operation       The main function
	 * @param errorOperation  The function executed after a error
	 * @param directives      A set objects used to configure the function
	 * 
	 * @return The function operation result
	 */
	protected <In, Out> Out executeOperation(
			final In in,
			final InterfaceOperationType type,
			final BiFunction<In, Object[], In> preOperation,
			final BiFunction<Out, Object[], Out> posOperation,
			final BiFunction<In, Object[], Out> operation,
			final TriFunction<In, Exception, Object[], Exception> errorOperation,
			final Object... directives) {

		final var session = securityService.getSession();
		final var operationName = type.getName();

		final var inName = type.getReceiver().concat("In");
		final var preInName = PRE_LOG.concat(inName);
		final var finalInName = FINAL_LOG.concat(inName);

		final var outName = type.getReceiver().concat("Out");
		final var preOutName = PRE_LOG.concat(outName);
		final var finalOutName = FINAL_LOG.concat(outName);

		LOGGER.debug("Pre executing {} operation with {} '{}', session '{}', and directives '{}'",
				operationName, inName, in, session, directives);

		final var preIn = preOperation.apply(in, directives);

		final var finalIn = ofNullable(preIn)
				.orElseThrow(createNullPointerException(i18nService, preInName));

		LOGGER.debug("Executing {} operation with {} '{}', session '{}', and directives '{}'",
				operationName, finalInName, finalIn, session, directives);

		final Out out;

		try {
			out = operation.apply(finalIn, directives);
		} catch (final Exception ex) {

			final var finalException = errorOperation.apply(finalIn, ex, directives);
			final var exceptionClass = getRootCause(finalException).getClass().getSimpleName();

			LOGGER.debug(ERROR_ON_OPERATION_LOG, operationName, finalInName, finalIn, session, exceptionClass);

			throw exceptionAdapterService.convert(
					finalException, i18nService, type, getEntityClazz(), finalIn);
		}

		LOGGER.debug(OPERATION_EXECUTED_LOG, operationName, outName, out, session, directives);

		final var posOut = posOperation.apply(out, directives);

		final var finalOut = ofNullable(posOut)
				.orElseThrow(createNullPointerException(i18nService, preOutName));

		LOGGER.debug(POS_OPERATION_LOG, operationName, finalOutName, finalOut, session, directives);

		return finalOut;
	}

	/**
	 * Execute commons operations like save, delete, saveAll, etc operations
	 * (functions) in a same execution structure, but with two inputs.
	 * 
	 * @param <In1>          The first input type
	 * @param <In2>          The second input type
	 * @param <Out>          The output type
	 * @param in1            The first operation input
	 * @param in2            The second operation input
	 * @param type           The operation type, used to categorize and log
	 *                       informations
	 * @param preOperation   The function executed before the operation
	 * @param posOperation   The function executed after the operation
	 * @param operation      The main function
	 * @param errorOperation The function executed after a error
	 * @param directives     A set objects used to configure the function
	 * 
	 * @return The function operation result
	 */
	protected <In1, In2, Out> Out executeOperation(
			final In1 in1,
			final In2 in2,
			final InterfaceOperationType type,
			final TriFunction<In1, In2, Object[], Entry<In1, In2>> preOperation,
			final BiFunction<Out, Object[], Out> posOperation,
			final TriFunction<In1, In2, Object[], Out> operation,
			final QuadFunction<In1, In2, Exception, Object[], Exception> errorOperation,
			final Object... directives) {

		final var session = securityService.getSession();
		final var operationName = type.getName();

		final var inName = type.getReceiver().concat("In");
		final var preInName = PRE_LOG.concat(inName);
		final var finalInName = FINAL_LOG.concat(inName);

		final var outName = type.getReceiver().concat("Out");
		final var preOutName = PRE_LOG.concat(outName);
		final var finalOutName = FINAL_LOG.concat(outName);

		LOGGER.debug("Pre executing {} operation with {} '{}', session '{}', and directives '{}'",
				operationName, inName, in1, session, directives);

		final var preInEntry = preOperation.apply(in1, in2, directives);

		final var preIn1 = preInEntry.getKey();
		final var preIn2 = preInEntry.getValue();

		final var finalIn1 = ofNullable(preIn1)
				.orElseThrow(createNullPointerException(i18nService, preInName));

		final var finalIn2 = ofNullable(preIn2)
				.orElseThrow(createNullPointerException(i18nService, preInName));

		LOGGER.debug("Executing {} operation with {} '{}', session '{}', and directives '{}'",
				operationName, finalInName, finalIn1, session, directives);

		final Out out;

		try {
			out = operation.apply(finalIn1, finalIn2, directives);
		} catch (final Exception ex) {

			final var finalException = errorOperation.apply(finalIn1, in2, ex, directives);
			final var exceptionClass = getRootCause(finalException).getClass().getSimpleName();

			LOGGER.debug(ERROR_ON_OPERATION_LOG, operationName, finalInName, finalIn1, session, exceptionClass);

			throw exceptionAdapterService.convert(finalException, i18nService, type, getEntityClazz(), finalIn1);
		}

		LOGGER.debug(OPERATION_EXECUTED_LOG, operationName, outName, out, session, directives);

		final var posOut = posOperation.apply(out, directives);

		final var finalOut = ofNullable(posOut)
				.orElseThrow(createNullPointerException(i18nService, preOutName));

		LOGGER.debug(POS_OPERATION_LOG, operationName, finalOutName, finalOut, session, directives);

		return finalOut;
	}

	/**
	 * Execute commons operations like save, delete, saveAll, etc operations
	 * (functions) in a same execution structure, but with no inputs.
	 * 
	 * @param <Out>          The output type
	 * @param type           The operation type, used to categorize and log
	 *                       informations
	 * @param preOperation   The function executed before the operation
	 * @param posOperation   The function executed after the operation
	 * @param operation      The main function
	 * @param errorOperation The function executed after a error
	 * @param directives     A set objects used to configure the function
	 * 
	 * @return The function operation result
	 */
	protected <Out> Out executeOperation(
			final InterfaceOperationType type,
			final UnaryOperator<Object[]> preOperation,
			final BiFunction<Out, Object[], Out> posOperation,
			final Function<Object[], Out> operation,
			final BiFunction<Exception, Object[], Exception> errorOperation,
			final Object... directives) {

		final var session = securityService.getSession();
		final var operationName = type.getName();

		final var inName = type.getReceiver().concat("In");
		final var preInName = PRE_LOG.concat(inName);
		final var finalInName = FINAL_LOG.concat(inName);

		final var outName = type.getReceiver().concat("Out");
		final var preOutName = PRE_LOG.concat(outName);
		final var finalOutName = FINAL_LOG.concat(outName);

		LOGGER.debug("Pre executing {} operation session '{}', and directives '{}'",
				operationName, session, directives);

		final var preIn = preOperation.apply(directives);

		final var finalIn = ofNullable(preIn)
				.orElseThrow(createNullPointerException(i18nService, preInName));

		LOGGER.debug("Executing {} operation session '{}', and directives '{}'",
				operationName, session, directives);

		final Out out;

		try {
			out = operation.apply(directives);
		} catch (final Exception ex) {

			final var finalException = errorOperation.apply(ex, directives);
			final var exceptionClass = getRootCause(finalException).getClass().getSimpleName();

			LOGGER.debug(ERROR_ON_OPERATION_LOG, operationName, finalInName, finalIn, session, exceptionClass);

			throw exceptionAdapterService.convert(
					finalException, i18nService, type, getEntityClazz(), finalIn);
		}

		LOGGER.debug(OPERATION_EXECUTED_LOG, operationName, outName, out, session, directives);

		final var posOut = posOperation.apply(out, directives);

		final var finalOut = ofNullable(posOut)
				.orElseThrow(createNullPointerException(i18nService, preOutName));

		LOGGER.debug(POS_OPERATION_LOG, operationName, finalOutName, finalOut, session, directives);

		return finalOut;
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
