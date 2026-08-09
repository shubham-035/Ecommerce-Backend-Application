package com.payment_service.controller;

import com.payment_service.dto.PaymentEvent;
import com.payment_service.service.PaymentProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaTestController {

    private final PaymentProducer paymentProducer;

    @GetMapping("/test-kafka")
    public String testKafka() throws Exception {

        PaymentEvent event =
                new PaymentEvent(
                        "101",
                        "shubham@gmail.com",
                        "deepak",
                        "+919307373123",
                        1500.0,
                        "SUCCESS"
                );

        paymentProducer.sendPaymentEvent(
                event);

        return "Kafka Event Sent";
    }
}