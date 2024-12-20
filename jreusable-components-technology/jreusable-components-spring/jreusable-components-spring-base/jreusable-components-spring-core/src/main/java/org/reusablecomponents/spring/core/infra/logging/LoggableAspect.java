package org.reusablecomponents.spring.core.infra.logging;

import static java.lang.String.format;
import static java.util.Locale.ROOT;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.containsAny;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;
import static org.springframework.boot.logging.LogLevel.OFF;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringJoiner;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

/**
 * The Aspect to log the method enters and method exists.
 * 
 * @author Fernando Romulo da Silva
 */
@Aspect
@Component
public class LoggableAspect {

	/**
	 * The pointcut to annotated class.
	 */
	@Pointcut("@annotation(org.resusablecomponent.spring.infra.logging.Loggable)")
	public void annotatedMethod() {
		// just to execute the Around
	}

	/**
	 * The pointcut to annotated method.
	 */
	@Pointcut("@within(org.resusablecomponent.spring.infra.logging.Loggable)")
	public void annotatedClass() {
		// just to execute the Around
	}

	/**
	 * Execute the wrap method.
	 * 
	 * @param point The object that support around advice in @AJ aspects
	 * @return The methot's return
	 * @throws Throwable If something wrong happens
	 */
	@Around("execution(* *(..)) && (annotatedMethod() || annotatedClass())")
	public Object log(final ProceedingJoinPoint point) throws Throwable {

		final var codeSignature = (CodeSignature) point.getSignature();
		final var methodSignature = (MethodSignature) point.getSignature();
		final var method = methodSignature.getMethod();

		final var declaringClass = method.getDeclaringClass();

		final var target = point.getTarget();

		final var logger = getLogger(declaringClass, target);

		final var annotation = ofNullable(method.getAnnotation(Loggable.class)) //
				.orElse(declaringClass.getAnnotation(Loggable.class));

		final var level = annotation.value();
		final var errorLevel = annotation.errorValue();

		final var unit = annotation.unit();
		final var showArgs = annotation.showArgs();
		final var showResult = annotation.showResult();
		final var showExecutionTime = annotation.showExecutionTime();

		final var methodName = method.getName();
		final var methodArgs = point.getArgs();
		final var methodParams = codeSignature.getParameterNames();

		executeLog(logger, level, entryMessage(methodName, showArgs, methodParams, methodArgs));

		final var start = Instant.now();

		final var lowerCaseUnit = unit.name().toLowerCase(ROOT);

		try {

			final var response = ofNullable(point.proceed()) //
					.orElse("void");

			final var end = Instant.now();

			final var duration = format("%s %s", unit.between(start, end), lowerCaseUnit);

			executeLog(logger, level, exitMessage(methodName, duration, response, showResult, showExecutionTime));

			return response;

		} catch (final Exception ex) { // NOPMD - point.proceed() throw it

			final var end = Instant.now();

			final var duration = format("%s %s", unit.between(start, end), lowerCaseUnit);

			executeLog(logger, errorLevel, errorMessage(methodName, duration, ex, showExecutionTime));

			throw ex;
		}

	}

	private String entryMessage(final String methodName, final boolean showArgs, final String[] params,
			final Object... args) {
		final var message = new StringJoiner(" ").add("Started").add(methodName).add("method");

		if (showArgs && nonNull(params) && nonNull(args) && params.length == args.length) {

			final var values = new HashMap<String, Object>(params.length);

			for (int i = 0; i < params.length; i++) {
				values.put(params[i], args[i]);
			}

			message.add("with args:").add(values.toString());
		}

		return message.toString();
	}

	private String exitMessage(final String methodName, final String duration, final Object result,
			final boolean showResult, final boolean showExecutionTime) {
		final var message = new StringJoiner(" ").add("Finished").add(methodName).add("method");

		if (showExecutionTime) {
			message.add("in").add(duration);
		}

		if (showResult) {
			message.add("with return:").add(result.toString());
		}

		return message.toString();
	}

	private String errorMessage(final String methodName, final String duration, final Throwable ex,
			final boolean showExecutionTime) {

		final var message = new StringJoiner(" ").add("Finished").add(methodName).add("method").add("with").add("error")
				.add(getRootCauseMessage(ex));

		if (showExecutionTime) {
			message.add("in").add(duration);
		}

		return message.toString();
	}

	private void executeLog(final Logger logger, final LogLevel level, final String message) {

		if (OFF.equals(level)) {
			return;
		}

		final var finalMsg = StringEscapeUtils.escapeJava(message);

		switch (level) { // NOPMD - SwitchStmtsShouldHaveDefault: Actually we have a 'default'
			case TRACE -> logger.trace(finalMsg);
			case DEBUG -> logger.debug(finalMsg);
			case INFO -> logger.info(finalMsg);
			case WARN -> logger.warn(finalMsg);
			case ERROR, FATAL -> logger.error(finalMsg);
			default -> logger.debug(finalMsg);
		}
	}

	private Logger getLogger(final Class<?> clazz, final Object object) {

		try {

			final var fields = clazz.getDeclaredFields();

			final var fieldOptional = Arrays.stream(fields)
					.map(Field::getName)
					.filter(field -> containsAny(field, "logger", "LOGGER", "log", "LOG"))
					.findFirst();

			if (fieldOptional.isEmpty()) {
				return LoggerFactory.getLogger(clazz);
			}

			return (Logger) FieldUtils.readDeclaredField(object, fieldOptional.get(), true);

		} catch (final IllegalAccessException | SecurityException ex) {
			return LoggerFactory.getLogger(clazz);
		}
	}
}
