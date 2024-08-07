package org.reusablecomponents.spring.infra.handler;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;
import static org.apache.commons.text.StringEscapeUtils.escapeJava;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.reusablecomponents.base.core.infra.exception.common.BaseApplicationException;
import org.slf4j.MDC;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Defaults handlers methods
 * 
 * @author Fernando Romulo da Silva
 */
abstract class AbstractRestExceptionHandler extends ResponseEntityExceptionHandler {

    protected final MessageSource messageSource;

    /**
     * Default constructor.
     * 
     * @param messageSource Object used to translate messages
     */
    protected AbstractRestExceptionHandler(final MessageSource messageSource) {
	super();
	this.messageSource = messageSource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
                                                        		    final MissingServletRequestParameterException ex, 
                                                        		    final HttpHeaders headers, 
                                                        		    final HttpStatusCode status,
                                                        		    final WebRequest request) {

	final var locale = request.getLocale();
	final Object[] params = { ex.getParameterName() };

	final var msg = messageSource.getMessage("exception.missingServletRequestParameter", params, locale);

	return handleObjectException(msg, List.of(), ex, request, HttpStatus.BAD_REQUEST);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
		    						    final HttpMessageNotReadableException ex, 
		    						    final HttpHeaders headers, 
		    						    final HttpStatusCode status, 
		    						    final WebRequest request) {

	return handleObjectException(ex, request, HttpStatus.BAD_REQUEST);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {

	final var subErrors = new ArrayList<Map<String, String>>();

	final var locale = request.getLocale();

	ex.getBindingResult().getFieldErrors().forEach((error) -> {

	    final var errors = new HashMap<String, String>();

	    errors.put("object", error.getObjectName());
	    errors.put("field", error.getField());
	    errors.put("error", messageSource.getMessage(error, locale));

	    subErrors.add(errors);
	});

	final var msg = messageSource.getMessage("exception.handleMethodArgumentNotValid", null, locale);

	return handleObjectException(msg, subErrors, ex, request, HttpStatus.BAD_REQUEST);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
	final var locale = request.getLocale();

	final var msg = messageSource.getMessage("exception.handleNoHandlerFound", null, locale);

	return handleObjectException(msg, List.of(), ex, request, status);
    }

    /**
     * Handle exceptions, especially {@link BaseApplicationException} and its descendents and use the exception's message.
     * 
     * @param ex      The exception
     * @param request Request object, to create response body.
     * @param status  The error status
     * @return A {@link ResponseEntity} object that's the response
     */
    protected ResponseEntity<Object> handleObjectException(final Throwable ex, final WebRequest request, final HttpStatus status) {

	final var msg = ex instanceof BaseApplicationException ? escapeJava(getMessage(ex)) : escapeJava(getRootCauseMessage(ex));

	final var body = buildResponseBody(msg, List.of(), status, ex, request);

	if (logger.isErrorEnabled()) {
	    logger.error(msg, getRootCause(ex));
	}

	return new ResponseEntity<>(body, status);
    }

    /**
     * Handle exceptions using the message on response.
     * 
     * @param msg     The message that it'll be used
     * @param ex      The exception
     * @param request Request object, to create response body.
     * @param status  The error status
     * @return A {@link ResponseEntity} object that's the response
     */
    protected ResponseEntity<Object> handleObjectException(final String msg, final Collection<Map<String, String>> subErrors, final Throwable ex, final WebRequest request, final HttpStatusCode status) {

	final var body = buildResponseBody(msg, subErrors, status, ex, request);

	if (logger.isErrorEnabled()) {
	    logger.error(escapeJava(getRootCauseMessage(ex)), getRootCause(ex));
	}

	return new ResponseEntity<>(body, status);
    }

    private Map<String, Object> buildResponseBody(final String message, final Collection<Map<String, String>> subErrors, final HttpStatusCode statusCode, final Throwable ex, final WebRequest request) {
	
	final var body = new LinkedHashMap<String, Object>();

	final var status = HttpStatus.valueOf(statusCode.value());
	
	body.put("timestamp", LocalDateTime.now().format(ISO_DATE_TIME));
	body.put("status", status.value());
	body.put("error", status.getReasonPhrase());
	body.put("message", message);

	if (CollectionUtils.isNotEmpty(subErrors)) {
	    body.put("subErrors", subErrors);
	}

	body.put("traceId", MDC.get("traceId"));
	body.put("spanId", MDC.get("spanId"));

	if (isTraceOn(request)) {
	    body.put("stackTrace", getStackTrace(ex));
	}

	return body;
    }

    private boolean isTraceOn(final WebRequest request) {

	final var value = request.getParameterValues("trace");

	return Objects.nonNull(value) //
			&& value.length > 0 //
			&& "true".contentEquals(value[0]);
    }

}