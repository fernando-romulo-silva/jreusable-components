package org.reusablecomponent.spring.infra.performance;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.range;
import static net.logstash.logback.argument.StructuredArguments.keyValue;
import static org.apache.commons.lang3.StringUtils.LF;
import static org.apache.commons.lang3.StringUtils.containsAnyIgnoreCase;
import static org.apache.commons.lang3.StringUtils.substringBefore;
import static org.springframework.boot.logging.LogLevel.DEBUG;
import static org.springframework.boot.logging.LogLevel.ERROR;
import static org.springframework.boot.logging.LogLevel.FATAL;
import static org.springframework.boot.logging.LogLevel.INFO;
import static org.springframework.boot.logging.LogLevel.OFF;
import static org.springframework.boot.logging.LogLevel.TRACE;
import static org.springframework.boot.logging.LogLevel.WARN;

import java.lang.StackWalker.StackFrame;
import java.lang.reflect.Parameter;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.Range;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;

/**
 * This method intercept class in order to measures how much time was spent executing a intercepted method and based on configurations, defines if it was slow or not. <br />
 * The main idea is to log informations about slow executions on critical methods. <br />
 * The logs will be like this example: <br />
 * <pre>
 * 	
 * </pre>
 * 
 * To connect this interceptor with an aspect advisor, please access (@link PerformanceMonitorConfiguration} <br /> <br />
 * 
 * The log level (OFF, TRACE, DEBUG, INFO, etc) is defined by the time of intercepted method spent. <br />
 * For example, an intercepted method tha spent between 0 and 500 milliseconds shows a WARN level. <br />
 * All these configurations can be defined as your necessity.
 */
public class PerformanceMonitorInterceptor implements MethodInterceptor {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceMonitorInterceptor.class);
    
    private String applicationName;
    
    private final Map<LogLevel, Range<Long>> performanceRanges = new EnumMap<>(LogLevel.class);
    
    private boolean enabled = true;
    
    /**
     * Default constructor.
     * 
     * @param applicationName Application name.
     * @param performanceRanges A map of log levels and its ranges.
     */
    public PerformanceMonitorInterceptor(
		    final String applicationName,
		    final Map<LogLevel, Range<Long>> performanceRanges) {
	
	this.applicationName = applicationName;
	this.performanceRanges.putAll(performanceRanges);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
	
	final var start = Instant.now();
	
	try {
	    
	    return invocation.proceed();
	    
	} finally {
	    
	    final var end = Instant.now();
	    
	    final var unit = ChronoUnit.MILLIS;
	    
	    final var duration = unit.between(start, end);
	    
	    final var logLevel = getLevel(duration);
	    
	    if (isLogging(logLevel)) {
		
		final var message = "Perfomance issue: time '{}' milliseconds, project '{}', thread '{}', class '{}', method '{}', return type '{}', parameters '[{}]', caller '{}', stackTrace: {} ";
		
		final var method = invocation.getMethod();
		
		final var clazz = invocation.getThis().getClass();
		
		final var methodName = method.getName();
		
		final var methodArgs = Stream.of(invocation.getArguments())
					.map(p -> isNull(p) ? "null" : p.toString())
					.toList();
		
		final var methodArgsType = Stream.of(method.getParameters())
					.map(Parameter::getType)
					.map(Class::getSimpleName)
					.toList();
		
		final var parameters = range(0, methodArgs.size())
					.boxed()
					.map(i -> methodArgsType.get(i) + ":" + methodArgs.get(i))
					.collect(joining(","));
		
		final var methodReturnType = method.getReturnType();
		
		final var callerAndStack = findCallerAndStack();
		
		final var threadId = Thread.currentThread().getId();
		
		final Object[] params = {
			keyValue("duration", duration), // duration
			keyValue("applicationName", applicationName), // application name
			keyValue("threadId", threadId), // thread id
			clazz,
			methodName, // method
			methodReturnType.getSimpleName(), // return type
			parameters, // parameters
			keyValue("caller", callerAndStack.getKey()), //
			LF.concat(callerAndStack.getValue())
		};
		
		executeLog(logLevel, message, params);
	    }
	}
    }

    private void executeLog(final LogLevel logLevel, final String message, final Object... params) {
	
	final var finalMsg = StringEscapeUtils.escapeJava(message);
	
	switch(logLevel) {
		case TRACE -> LOGGER.trace(finalMsg, params);
		case DEBUG -> LOGGER.debug(finalMsg, params);
		case INFO -> LOGGER.info(finalMsg, params);
		case WARN -> LOGGER.warn(finalMsg, params);
		case ERROR,  FATAL -> LOGGER.error(finalMsg, params);
		default -> LOGGER.debug(finalMsg, params);
	}
    }

    private AbstractMap.SimpleEntry<String, String> findCallerAndStack() {
	
	final var stackList = StackWalker.getInstance()
				.walk(this::walkThroughStack)
				.stream()
				.filter(this::filterNonMatter)
				.map(trace -> substringBefore(trace.getClassName(), "$$EnhancerBySpringCGLIB") + "." + trace.getMethodName() + ":" + trace.getLineNumber())
				.toList();
	
	final var caller = stackList.isEmpty() 
			? this.getClass().getSimpleName()
		        : stackList.get(stackList.size() - 1);			
	
	final var stackTrace = stackList.stream().collect(joining(LF));
	
	return new AbstractMap.SimpleEntry<>(caller, stackTrace);
    }
    
    private boolean filterNonMatter(final StackFrame trace) {
	return !containsAnyIgnoreCase(trace.getClassName(), "PerformanceMonitorInterceptor")
	   && !containsAnyIgnoreCase(trace.getClassName(), "$$FastClassBySpringCGLIB");	
    }
    
    private List<StackFrame> walkThroughStack(final Stream<StackFrame> stackFrameStream) {
	return stackFrameStream
			.filter(frame -> frame.getClassName()
			.contains("org"))
			.toList();
    }

    private boolean isLogging(final LogLevel logLevel) {
	
	if (!enabled || performanceRanges.isEmpty()) {
	    return false;
	}
	
	if (logLevel == OFF) {
	    return false;
	}
	
	if (logLevel == FATAL) {
	    return true;
	}
	
	return (logLevel == TRACE && LOGGER.isTraceEnabled())
	    || (logLevel == DEBUG && LOGGER.isDebugEnabled())
	    || (logLevel == INFO && LOGGER.isInfoEnabled())
	    || (logLevel == WARN && LOGGER.isWarnEnabled())
	    || (logLevel == ERROR && LOGGER.isErrorEnabled());
    }

    private LogLevel getLevel(final long duration) {
	return performanceRanges.entrySet().stream()
			.filter(performance -> performance.getValue().contains(duration))
			.map(Entry::getKey)
			.findFirst()
			.orElse(DEBUG);
    }
    
    /**
     * Add or update a range by log level.
     * 
     * @param logLevel The log level
     * @param range A Range<Long> instance
     * @return returns itself
     */
    public PerformanceMonitorInterceptor defineLogLevelRange(final LogLevel logLevel, final Range<Long> range) {
	performanceRanges.put(logLevel, range);
	return this;
    }

    /**
     * Enable the interceptor, and now it's going to check the performance.
     * 
     * @return returns itself
     */
    public PerformanceMonitorInterceptor enable() {
	enabled = true;
	return this;
    }

    /**
     * Disable the interceptor, and now it's going to skip the performance measuring.
     * 
     * @return returns itself
     */
    public PerformanceMonitorInterceptor disable() {
	enabled = false;
	return this;
    }
    
    public boolean isEnabled() {
	return enabled;
    }
}
