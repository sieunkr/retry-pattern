package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @CustomRetry
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