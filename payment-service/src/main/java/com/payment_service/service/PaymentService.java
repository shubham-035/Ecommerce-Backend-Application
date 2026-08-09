package com.payment_service.service;

import com.payment_service.client.OrderFeignClient;
import com.payment_service.dto.OrderResponse;
import com.payment_service.entity.Payment;
import com.payment_service.respository.PaymentRepository;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderFeignClient orderFeignClient;

    public PaymentService(PaymentRepository paymentRepository, OrderFeignClient orderFeignClient) {
        this.paymentRepository = paymentRepository;
        this.orderFeignClient = orderFeignClient;
    }

    public Payment createPayment(Long orderId) throws Exception {

        OrderResponse order = orderFeignClient.getOrder(orderId);

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/success")
                .setCancelUrl("http://localhost:3000/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder().setQuantity(1L)
                                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("inr")
                                                .setUnitAmount(order.getTotalAmount().multiply(BigDecimal.valueOf(100)).longValue())
                                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName("Order #" + orderId).build())
                                                .build())
                                .build())
                .build();

        Session session = Session.create(params);
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(order.getTotalAmount());
        payment.setStatus("PENDING");
        payment.setSessionId(session.getId());
        payment.setPaymentUrl(session.getUrl());
        payment.setCreatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }
}