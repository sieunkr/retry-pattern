package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class OrderService {

    @Retryable(
            value = {CustomRuntimeException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000)
    )
    public double randomFail() {

        log.info("execute..");
        double random = getRandom();
        if (random <= 0.5) {
            throw new CustomRuntimeException();
        }
        return random;
    }

    @Recover
    public double recover(CustomRuntimeException e) {
        //TODO: fallBack 처리
        log.error("failed...");
        throw e;
    }

    protected double getRandom() {
        return Math.random();
    }
    
    /*
    //@Retryable(...)
    public String send() {
        //외부 API 를 호출한다고 가정해보자.
        //RestTemplate...
    }
     */
}

