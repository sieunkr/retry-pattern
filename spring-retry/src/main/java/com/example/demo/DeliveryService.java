package com.example.demo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final RetryTemplate retryTemplate;

    public void execute() {
        retryTemplate.execute(context -> {

            log.info("execute..");
            double random = getRandom();
            if (random <= 1) {
                throw new CustomRuntimeException();
            }
            return null;
        });
    }

    protected double getRandom() {
        return Math.random();
    }
}
