package com.example.batchSample.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by alokkulkarni on 25/04/17.
 */
@Aspect
@Component
public class RetryAspectConfiguration {


    private RetryTemplate retryTemplate;

    @Autowired
    public RetryAspectConfiguration(RetryTemplate retryTemplate) {
        this.retryTemplate = retryTemplate;
    }


    @Around("execution(* com..Batch*.*(..))")
    public Object retryAspect(final ProceedingJoinPoint point) throws Throwable {
        return retryTemplate.execute(retryCallback -> point.proceed());
    }

}
