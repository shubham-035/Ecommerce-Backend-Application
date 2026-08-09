package com.payment_service.controller;

import com.payment_service.entity.Payment;
import com.payment_service.respository.PaymentRepository;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/webhook")
public class WebhookController {

    private final PaymentRepository paymentRepository;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    public WebhookController(
            PaymentRepository paymentRepository
    ) {
        this.paymentRepository = paymentRepository;
    }

    @PostMapping
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature")
            String sigHeader
    ) {

        Event event;
        try {

            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

        } catch (
                SignatureVerificationException e
        ) {
            return ResponseEntity.badRequest().body("Invalid signature");
        }

        if ("checkout.session.completed".equals(event.getType())) {

            Session session = (Session) event
                            .getDataObjectDeserializer()
                            .getObject()
                            .orElse(null);

            if (session != null) {
                Payment payment = paymentRepository.findBySessionId(session.getId()).orElse(null);
                if (payment != null) {

                    payment.setStatus("SUCCESS");
                    paymentRepository.save(payment);
                }
            }
        }

        return ResponseEntity.ok("Webhook received");
    }
}