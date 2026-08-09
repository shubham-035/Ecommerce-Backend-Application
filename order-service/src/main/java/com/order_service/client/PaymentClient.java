package com.order_service.client;

import com.order_service.dto.PaymentRequest;
import com.order_service.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name="payment-service",
        url="http://localhost:8085/api/v1/payments"
)
public interface PaymentClient {
    @PostMapping("/process")
    public PaymentResponse processPayment(@RequestBody PaymentRequest request);
}
