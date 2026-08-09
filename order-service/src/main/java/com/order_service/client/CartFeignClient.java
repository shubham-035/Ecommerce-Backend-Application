package com.order_service.client;

import com.order_service.dto.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "cart-service",
        url = "http://localhost:8083"
)
public interface CartFeignClient {

    @GetMapping("/api/v1/cart/{uuid}")
    CartResponse getCart(
            @PathVariable("uuid") String uuid
    );
    @DeleteMapping("/api/v1/cart/{uuid}/clear")
    public ResponseEntity<String> clearCart(
            @PathVariable String uuid
    );
}
