package com.cart_service.controller;

import com.cart_service.dto.AddToCartRequest;
import com.cart_service.dto.ApiResponse;
import com.cart_service.entity.Cart;
import com.cart_service.service.CartService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(exposedHeaders = "X-CART-ID")
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addToCart(
            @RequestHeader(value = "X-CART-ID",required = false)String uuid,
            @RequestBody AddToCartRequest request
            ){
        Cart cart= cartService.addToCart(uuid,request);

        HttpHeaders header=new HttpHeaders();
        header.set("X-CART-ID",cart.getUuid());

        ApiResponse<String> response=new ApiResponse<>();
        response.setMessage("Added");
        response.setStatus(201);
        response.setData("product added to cart");
        return new ResponseEntity<>(response,header, HttpStatus.OK);

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Cart> getCart(
            @PathVariable String uuid
    ) {
        return ResponseEntity.ok(
                cartService.getCart(uuid)
        );
    }

    @DeleteMapping("/{uuid}/clear")
    public ResponseEntity<String> clearCart(
            @PathVariable String uuid
    ) {
        cartService.clearCart(uuid);
        return ResponseEntity.ok("Cart cleared");
    }

}
