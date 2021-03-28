package com.example.demo;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.function.Supplier;

import static java.time.temporal.ChronoUnit.SECONDS;


@Slf4j
@Aspect
@Component
public class CustomRetryAspect {


    @Around("@annotation(com.example.demo.CustomRetry) && @annotation(target)")
    public Object retry(ProceedingJoinPoint point, CustomRetry target) throws Throwable {

        //레퍼런스 : https://resilience4j.readme.io/docs/retry

        var methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();

        RetryConfig config = RetryConfig.custom()
                .maxAttempts(target.attempts())
                .waitDuration(Duration.of(target.delay(), SECONDS))
                .build();
        var retryRegistry = RetryRegistry.of(config);
        var retry = retryRegistry.retry(method.getName(), config);

        Supplier<Object> supplier = Retry.decorateSupplier(retry, () -> {
            try {
                log.info("point.proceed");
                return point.proceed();
            } catch (Throwable throwable) {
                throw new RuntimeException();
            }
        });

        return supplier.get();
    }
}
