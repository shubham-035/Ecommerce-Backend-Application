package com.order_service.controller;

import com.order_service.dto.CreateOrderResponse;
import com.order_service.entity.Order;
import com.order_service.service.OrderService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestHeader("X-CART-ID") String uuid){
        Order order = orderService.create(uuid);
        CreateOrderResponse response=new CreateOrderResponse();
        response.setOrderId(order.getId());
        response.setStatus(order.getStatus());
        response.setTotalAmount(order.getTotalAmount());
        response.setMessage("order placed successfully");
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(201));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable long id){
        return ResponseEntity.ok(orderService.getOrder(id));
    }
}
