package org.reusablecomponents.spring.core.infra.performance;

import static java.util.Map.of;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;
import static org.springframework.boot.logging.LogLevel.DEBUG;
import static org.springframework.boot.logging.LogLevel.ERROR;
import static org.springframework.boot.logging.LogLevel.INFO;
import static org.springframework.boot.logging.LogLevel.OFF;
import static org.springframework.boot.logging.LogLevel.TRACE;
import static org.springframework.boot.logging.LogLevel.WARN;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import org.apache.commons.lang3.Range;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import jakarta.annotation.PostConstruct;

/**
 * Component specialized to supports {@link PerformanceMonitorInterceptor}. <br />
 * This class contains default ranges for all log levels (OFF, TRACE, DEBUG, INFO, WARN, and ERROR, but you can change it. <br />
 * <pre>{@code
 *      @Bean  
 * 	Advisor performanceMonitorAdvisor(final PerformanceMonitorInterceptor performanceMonitorInterceptor) {
 * 	    final var pointcut = new AspectJExpresionPointcut();
 *         
 *          final var expression = new StringBuilder() 
 *                   .append(createPointcut(ManagerService.class, PersonService.class))
 *                   .append(" || ").append(createPointcut(SalesService.class))
 *                   .toString();
 *                   
 *          pointcut.setExpression(expression);
 *          
 *          return new DefaultPointcutAdvisor(pointcut, interceptor);
 *      }
 * }</pre>
 * 
 * On the code above a set o beans (ManagerService, SalesService) had its methods and super class (PersonService) intercepted with the performance monitor. <br />
 * If you need more than one performance monitor iterceptor instance, don't forget to use the @Qualifier annotation.
 * 
 */
@Configuration
public class PerformanceMonitorConfiguration {

    @Value("${performance.off-range:0,500}")
    private long[] offRangeList;

    @Value("${performance.trace-range:501,1000}")
    private long[] traceRangeList;
    
    @Value("${performance.debug-range:1001,2000}")
    private long[] debugRangeList;
    
    @Value("${performance.info-range:2001,4000}")
    private long[] infoRangeList;
    
    @Value("${performance.warn-range:4001,10000}")
    private long[] warnRangeList;
    
    @Value("${performance.error-range:10001,90000}")
    private long[] errorRangeList;    
    
    @Value("${spring.application.name:-undefined}")
    private String applicationName;
    
    private final Map<LogLevel, Range<Long>> defaultRanges = new EnumMap<>(LogLevel.class);
    
    /**
     * Init the default ranges.
     */
    @PostConstruct
    protected void init() {
	defaultRanges.putAll(of(
			OFF, Range.of(offRangeList[0], offRangeList[1]),
			TRACE, Range.of(traceRangeList[0], traceRangeList[1]),
			DEBUG, Range.of(debugRangeList[0], debugRangeList[1]),
			INFO, Range.of(infoRangeList[0], infoRangeList[1]),
			WARN, Range.of(warnRangeList[0], warnRangeList[1]),
			ERROR, Range.of(errorRangeList[0], errorRangeList[1])));
    }
    
    /**
     * Generate expression in or to be used with {@link org.springframework.aop.Advisor} object. <br />
     * For example a service <code>ManagerService</code> inherets from <code>PersonService</code>: <br /> 
     * The execution is:
     * 
     * <pre>
     * final var expression = createPointcut(ManagerService.class, PersonService.class);
     * </pre>
     * 
     * And the expression value is:
     * 
     * <pre>
     * 	(execution(public * com.company.service.ManagerService.*(..))
     *    || (
     *         execution(public * com.company.service.PersonService.*())
     *         && target(com.company.service.ManagerService)
     *       )
     * </pre>
     * 
     * @param clazz         The intercepted class used to create the aspect expression. It will only intercept public methods for this class.
     * @param parentClazzes The intercepted super classes used to add the aspect expression.
     * @return A string object with the expression.
     */
    public String createPointCut(final Class<?> clazz, final Class<?>... parentClazzes) {
	
	final var pointCut = new StringBuilder("execution( public * ").append(clazz.getName()).append(".*(..))");
	
	if (isNotEmpty(parentClazzes)) {
	    for (final var clazzIntern : parentClazzes) {
		pointCut.append(" || (").append("execution ( public * ").append(clazzIntern.getName()).append(".*(..))")
			.append(" && target (").append(clazz.getName()).append(")").append(")");
	    }
	}
	
	return pointCut.toString();
    }
    
    /**
     * @return
     */
    @Scope("singleton")
    @Bean(name = "performanceMonitorInterceptor")
    PerformanceMonitorInterceptor performanceMonitorInterceptor() {
	return new PerformanceMonitorInterceptor(applicationName, defaultRanges);
    }
    
    public Map<LogLevel, Range<Long>> getDefaultRanges() {
        return Collections.unmodifiableMap(defaultRanges);
    }

}
