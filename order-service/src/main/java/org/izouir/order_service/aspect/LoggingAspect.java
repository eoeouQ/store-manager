package org.izouir.order_service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* org.izouir.order_service.exception.*.*(..))")
    public void beforeAllExceptionHandlingAdvices(final JoinPoint joinPoint) {
        final var exception = (RuntimeException) joinPoint.getArgs()[0];
        logger.error(exception.getMessage());
    }
}
