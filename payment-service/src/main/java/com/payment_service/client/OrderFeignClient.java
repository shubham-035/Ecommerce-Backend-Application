package com.payment_service.client;

import com.payment_service.dto.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service",url="http://localhost:8084")
public interface OrderFeignClient {

    @GetMapping("/api/v1/order/{id}")
    public OrderResponse getOrder(@PathVariable long id);
}
