package com.kafka.consumer;


import com.kafka.model.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    @KafkaListener(topics = "order-events", groupId = "notification-group")
    public void handleOrder(
            OrderEvent event,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset) {

        log.info("======================================");
        log.info("New Order Received!");
        log.info("OrderId     : {}", event.getOrderId());
        log.info("Customer    : {}", event.getCustomerName());
        log.info("Email       : {}", event.getCustomerEmail());
        log.info("Amount      : {}", event.getAmount());
        log.info("Status      : {}", event.getStatus());
        log.info("Partition   : {}", partition);
        log.info("Offset      : {}", offset);
        log.info("======================================");

        // Simulate sending email
        sendEmail(event);
    }

    private void sendEmail(OrderEvent event) {
        log.info("Email sent to {} for order {}", event.getCustomerEmail(), event.getOrderId());
    }
}