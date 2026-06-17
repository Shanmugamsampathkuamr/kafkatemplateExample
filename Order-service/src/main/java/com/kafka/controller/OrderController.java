package com.kafka.controller;



import com.kafka.model.OrderEvent;
import com.kafka.producer.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderProducer orderProducer;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderEvent event) {

        // Auto generate orderId if not provided
        if (event.getOrderId() == null || event.getOrderId().isEmpty()) {
            event.setOrderId(UUID.randomUUID().toString());
        }

        event.setStatus("PLACED");
        orderProducer.sendOrder(event);

        return ResponseEntity.ok("Order placed successfully! OrderId: " + event.getOrderId());
    }
}
