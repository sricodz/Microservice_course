package com.order.ecom.controller;

import com.order.ecom.dto.CartItemRequest;
import com.order.ecom.entity.CartItem;
import com.order.ecom.service.CartService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private  CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest request) {
        if (!cartService.addToCart(userId, request)) {
            return ResponseEntity.badRequest().body("Product Out of Stock or User not found or Product not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable String productId) {
        boolean deleted = cartService.deleteItemFromCart(userId, productId);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(
            @RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    /*
    * Below method is just an example how to implement the
    * ratelimitor for one endpoint
    * */
    @RateLimiter(name="rateBreaker",fallbackMethod = "getMessageFallback")
    @GetMapping("/message")
    public String getMessage(){
        return "From RateLimitor Acutal Method!";
    }

    public String getMessageFallback(){
        return "From FallBack message!";
    }
}
