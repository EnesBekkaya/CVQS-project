package com.cvqs.defectsaveservice.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ServiceAspect {
    private static final Logger LOGGER = LogManager.getLogger(ServiceAspect.class);


    @Around("execution(* com.cvqs.defectsaveservice.service.concretes.*.*(..)) || execution(* com.cvqs.defectsaveservice.controller.*.*(..))")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        String className = joinPoint.getTarget().getClass().getName();

        LOGGER.info("Started Method {}() in class {}  with arguments {}", methodName, className, Arrays.toString(args));

        Object result = joinPoint.proceed();

        long elapsedTime = System.currentTimeMillis() - start;
        if (result == null) {
            LOGGER.info("Finished method {}() in class {} returned null", methodName,className);
        }else
            LOGGER.info("Finished method {}() in class {}  with result {} in {} ms", methodName, className, result.toString(), elapsedTime);

        return result;
    }

    @AfterThrowing(pointcut = "execution(* com.cvqs.defectsaveservice.service.concretes.*.*(..)) || execution(* com.cvqs.defectsaveservice.controller.*.*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        LOGGER.error("Exception occurred in {} class, method {}: {}", className, methodName, ex.getMessage());
    }
}