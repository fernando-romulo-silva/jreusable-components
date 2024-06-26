package org.reusablecomponents.spring.core.infra.logging;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;

import org.springframework.boot.logging.LogLevel;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Loggable {

  LogLevel value() default LogLevel.DEBUG;
  
  LogLevel errorValue() default LogLevel.ERROR;

  ChronoUnit unit() default ChronoUnit.MILLIS;

  boolean showArgs() default true;

  boolean showResult() default true;

  boolean showExecutionTime() default true;
}
