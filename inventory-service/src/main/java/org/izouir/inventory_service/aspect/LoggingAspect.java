package org.izouir.inventory_service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.izouir.inventory_service.controller.advice.ControllerExceptionHandlingAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandlingAdvice.class);

    @Before("execution(* org.izouir.inventory_service.controller.advice.*.*(..))")
    public void beforeAllExceptionHandlingAdvices(final JoinPoint joinPoint) {
        final var e = (RuntimeException) joinPoint.getArgs()[0];
        logger.error(e.getMessage());
    }
}
