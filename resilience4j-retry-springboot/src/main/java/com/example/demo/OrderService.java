package com.example.demo;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    @Retry(name = "retryOrder")
    public double randomFail() {

        double random = getRandom();
        if (random <= 0.5) {
            throw new RuntimeException("Value <= 0.5");
        }
        return random;
    }

    protected double getRandom() {
        return Math.random();
    }
}