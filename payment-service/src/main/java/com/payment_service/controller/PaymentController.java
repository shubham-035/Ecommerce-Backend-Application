package com.payment_service.controller;

import com.payment_service.entity.Payment;
import com.payment_service.service.PaymentService;
import com.payment_service.service.StripeService;
import com.stripe.model.PaymentIntent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final StripeService stripeService;

    public PaymentController(
            PaymentService paymentService,
            StripeService stripeService
    ) {
        this.paymentService = paymentService;
        this.stripeService = stripeService;
    }

    // Create payment record using orderId
    @PostMapping("/order/{orderId}")
    public ResponseEntity<Payment> createOrderPayment(@PathVariable Long orderId) throws Exception {
        Payment payment = paymentService.createPayment(orderId);
        return ResponseEntity.ok(payment);
    }
    // Create Stripe Payment Intent
    @PostMapping("/intent")
    public ResponseEntity<Map<String, Object>> createPaymentIntent(
            @RequestParam Long amount
    ) throws Exception {

        PaymentIntent paymentIntent =
                stripeService.createPaymentIntent(amount);

        Map<String, Object> response = new HashMap<>();

        response.put("id", paymentIntent.getId());
        response.put("clientSecret", paymentIntent.getClientSecret());
        response.put("amount", paymentIntent.getAmount());
        response.put("currency", paymentIntent.getCurrency());
        response.put("status", paymentIntent.getStatus());

        return ResponseEntity.ok(response);
    }
}