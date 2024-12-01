package org.reusablecomponents.base.core.application.base;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;
import static org.reusablecomponents.base.core.infra.util.Functions.createNullPointerException;

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
	 * 
	 * @param <In>
	 * @param <Out>
	 * @param in
	 * @param operation
	 * @param preExecuteFunction
	 * @param posExecuteFunction
	 * @param executeFunction
	 * @param errorFunction
	 * @param directives
	 * @return
	 */
	protected <In, Out> Out executeOperation(
			final In in,
			final InterfaceOperation operation,
			final BiFunction<In, Object[], In> preExecuteFunction,
			final BiFunction<Out, Object[], Out> posExecuteFunction,
			final BiFunction<In, Object[], Out> executeFunction,
			final TriFunction<In, Exception, Object[], Exception> errorFunction,
			final Object... directives) {

		final var session = securityService.getSession();
		final var operationName = operation.getName();

		final var inName = operation.getReceiver().concat("In");
		final var preInName = "pre".concat(inName);
		final var finalInName = "final".concat(inName);

		final var outName = operation.getReceiver().concat("Out");
		final var preOutName = "pre".concat(outName);
		final var finalOutName = "final".concat(outName);

		LOGGER.debug("Pre executing {} operation with {} '{}', session '{}', and directives '{}'",
				operationName, inName, in, session, directives);

		final var preIn = preExecuteFunction.apply(in, directives);

		final var finalIn = ofNullable(preIn)
				.orElseThrow(createNullPointerException(i18nService, preInName));

		LOGGER.debug("Executing {} operation with {} '{}', session '{}', and directives '{}'",
				operationName, finalInName, finalIn, session, directives);

		final Out out;

		try {
			out = executeFunction.apply(finalIn, directives);
		} catch (final Exception ex) {

			final var finalException = errorFunction.apply(finalIn, ex, directives);
			final var exceptionClass = getRootCause(finalException).getClass().getSimpleName();

			LOGGER.debug("Error on {} operation with {} '{}', session '{}', error '{}'",
					operationName, finalInName, finalIn, session, exceptionClass);

			throw exceptionAdapterService.convert(
					finalException, i18nService, operation, getEntityClazz(), finalIn);
		}

		LOGGER.debug("{} operation executed, with {} '{}', session '{}', and directives '{}'",
				operationName, outName, out, session, directives);

		final var posOut = posExecuteFunction.apply(out, directives);

		final var finalOut = ofNullable(posOut)
				.orElseThrow(createNullPointerException(i18nService, preOutName));

		LOGGER.debug("Pos {} operation with {} '{}', session '{}', and directives '{}'",
				operationName, finalOutName, finalOut, session, directives);

		return finalOut;
	}

	/**
	 * 
	 * @param <Out>
	 * @param operation
	 * @param preExecuteFunction
	 * @param posExecuteFunction
	 * @param executeFunction
	 * @param errorFunction
	 * @param directives
	 * @return
	 */
	protected <Out> Out executeOperation(
			final InterfaceOperation operation,
			final UnaryOperator<Object[]> preExecuteFunction,
			final BiFunction<Out, Object[], Out> posExecuteFunction,
			final Function<Object[], Out> executeFunction,
			final BiFunction<Exception, Object[], Exception> errorFunction,
			final Object... directives) {

		final var session = securityService.getSession();
		final var operationName = operation.getName();

		final var inName = operation.getReceiver().concat("In");
		final var preInName = "pre".concat(inName);
		final var finalInName = "final".concat(inName);

		final var outName = operation.getReceiver().concat("Out");
		final var preOutName = "pre".concat(outName);
		final var finalOutName = "final".concat(outName);

		LOGGER.debug("Pre executing {} operation session '{}', and directives '{}'",
				operationName, session, directives);

		final var preIn = preExecuteFunction.apply(directives);

		final var finalIn = ofNullable(preIn)
				.orElseThrow(createNullPointerException(i18nService, preInName));

		LOGGER.debug("Executing {} operation session '{}', and directives '{}'",
				operationName, session, directives);

		final Out out;

		try {
			out = executeFunction.apply(directives);
		} catch (final Exception ex) {

			final var finalException = errorFunction.apply(ex, directives);
			final var exceptionClass = getRootCause(finalException).getClass().getSimpleName();

			LOGGER.debug("Error on {} operation with {} '{}', session '{}', error '{}'",
					operationName, finalInName, finalIn, session, exceptionClass);

			throw exceptionAdapterService.convert(
					finalException, i18nService, operation, getEntityClazz(), finalIn);
		}

		LOGGER.debug("{} operation executed, with {} '{}', session '{}', and directives '{}'",
				operationName, outName, out, session, directives);

		final var posOut = posExecuteFunction.apply(out, directives);

		final var finalOut = ofNullable(posOut)
				.orElseThrow(createNullPointerException(i18nService, preOutName));

		LOGGER.debug("Pos {} operation with {} '{}', session '{}', and directives '{}'",
				operationName, finalOutName, finalOut, session, directives);

		return finalOut;
	}

	// ------

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
