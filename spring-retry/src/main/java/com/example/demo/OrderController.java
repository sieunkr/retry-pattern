package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final DeliveryService deliveryService;

    @GetMapping("/test")
    public String test() {
        return "ok - " + orderService.randomFail();
    }

    @GetMapping("/test02")
    public String test02() {
        deliveryService.execute();
        return "ok";
    }
}