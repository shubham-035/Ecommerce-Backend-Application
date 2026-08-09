package com.order_service.service;

import com.order_service.client.CartFeignClient;
import com.order_service.client.InventoryClient;
import com.order_service.client.PaymentClient;
import com.order_service.dto.*;
import com.order_service.entity.Order;
import com.order_service.entity.OrderItem;
import com.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartFeignClient cartFeignClient;
    private final InventoryClient inventoryClient;
    private final PaymentClient paymentClient;


    public Order create(String cartUuid) {

        // 1 Fetch cart
        CartResponse cart =
                cartFeignClient
                        .getCart(cartUuid);

        // 2 Validate
        if (cart == null ||
                cart.getCartItems() == null ||
                cart.getCartItems().isEmpty()) {

            throw new RuntimeException(
                    "cart is empty");
        }

        // 3 Calculate total
        BigDecimal total =
                cart.getCartItems()
                        .stream()
                        .map(item ->
                                item.getPrice()
                                        .multiply(BigDecimal.valueOf(item.getQuantity()))
                        ).reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4 Create order
        Order order = new Order();

        order.setCartUuid(cart.getUuid());

        order.setUserId(cart.getUserId());

        order.setStatus("CREATED");

        order.setTotalAmount(total);

        order.setCreatedAt(LocalDateTime.now());

        // 5 Copy cart items
        for (CartItemResponse item : cart.getCartItems()) {

            OrderItem orderItem = new OrderItem();

            orderItem.setProductId(item.getProductId());

            orderItem.setBrandId(item.getBrandId());

            orderItem.setQuantity(item.getQuantity());

            orderItem.setPrice(item.getPrice());

            orderItem.setOrder(order);

            order.getOrderItems().add(orderItem);
        }

        // 6 Save
        Order savedOrder = orderRepository.save(order);

        // 7 Clear cart
        cartFeignClient.clearCart(cartUuid);

        try{
            //inventory reverse
            List<InventoryRequest> inventoryRequests = cart.getCartItems().stream()
                    .map(item -> {
                        InventoryRequest req = new InventoryRequest();
                        req.setProductId(item.getProductId());
                        req.setQuantity(item.getQuantity());
                        return req;

                    }).toList();

            inventoryClient.reserveInventory(inventoryRequests);

            //payment process
            PaymentRequest paymentRequest=new PaymentRequest();
            paymentRequest.setOrderId(order.getId());
            paymentRequest.setAmount(total);
            PaymentResponse paymentResponse= paymentClient.processPayment(paymentRequest);

            if("SUCCESS".equalsIgnoreCase(paymentResponse.getStatus())){
                savedOrder.setStatus("CONFIRMED");
                orderRepository.save(savedOrder);
                cartFeignClient.clearCart(cartUuid);
                return savedOrder;
            }
            throw new RuntimeException("payment failed");
        }catch (Exception e){
            //compensation rollback

            try{
                List<InventoryRollbackRequest> inventoryRollbackRequest = cart.getCartItems().stream()
                        .map(item -> {
                            InventoryRollbackRequest request = new InventoryRollbackRequest();
                            request.setProductId(item.getProductId());
                            request.setQuantity(item.getQuantity());
                            return request;
                        }).toList();
                inventoryClient.rollbackInventory(inventoryRollbackRequest);
            }catch (Exception ex){
                ex.printStackTrace();
            }

            savedOrder.setStatus("CANCELLED");
            orderRepository.save(savedOrder);
            throw new RuntimeException("saga failed :"+e.getMessage());
        }
    }

    public Order getOrder(long id){
        return orderRepository.findById(id).orElseThrow(()->new RuntimeException("order not found"));
    }
}
