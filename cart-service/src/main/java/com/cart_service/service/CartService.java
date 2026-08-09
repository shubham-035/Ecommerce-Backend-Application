package com.cart_service.service;

import com.cart_service.dto.AddToCartRequest;
import com.cart_service.entity.Cart;
import com.cart_service.entity.CartItem;
import com.cart_service.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {
    private CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    public Cart addToCart(String uuid , AddToCartRequest request){
        Cart cart;

        //check uuid
        if(uuid==null || uuid.isEmpty()){
            uuid = UUID.randomUUID().toString();
            cart=new Cart();
            cart.setUuid(uuid);
        }else{
            Optional<Cart> optionalCart = cartRepository.findByUuid(uuid);

            if (optionalCart.isPresent()) {
                cart = optionalCart.get();
            } else {
                Cart newCart = new Cart();
                newCart.setUuid(uuid);
                cart = newCart;
            }
        }

        //if the product already exist
        Optional<CartItem>  existItem= cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProductId()==request.getProductId())
                .findFirst();

        //check exist item
        if(existItem.isPresent()){
            CartItem item=existItem.get();
            item.setQuantity(item.getQuantity()+ request.getQuantity());
        }else{
            CartItem item=new CartItem();
            item.setProductId(request.getProductId());
            item.setPrice(request.getPrice());
            item.setQuantity(request.getQuantity());
            item.setBrandId(request.getBrandId());
            item.setCart(cart);

            cart.getCartItems().add(item);
        }
        return cartRepository.save(cart);
    }

    public Cart getCart(String uuid) {

        return cartRepository.findByUuid(uuid)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cart not found"));
    }

    public void clearCart(String uuid){
        Cart cart = cartRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("cart not found"));

        cartRepository.delete(cart);
    }
}
