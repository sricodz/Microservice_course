package com.order.ecom.service;

import com.order.ecom.configs.ProductServiceClient;
import com.order.ecom.configs.UserServiceClient;
import com.order.ecom.dto.CartItemRequest;
import com.order.ecom.dto.ProductResponse;
import com.order.ecom.dto.UserResponse;
import com.order.ecom.entity.CartItem;
import com.order.ecom.repository.CartItemRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepo;

    @Autowired
    private ProductServiceClient productServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    int retryCount = 0;

    //@CircuitBreaker(name = "productService", fallbackMethod = "addToCartFallBack")
    @Retry(name = "productService", fallbackMethod = "addToCartFallBack")
    public boolean addToCart(String userId, CartItemRequest req){

        //just to tack how many times retry happend
        System.out.println("Retry Happend :: "+ ++retryCount);
        //look for product
        ProductResponse prodRes = productServiceClient
                                    .getProductDetails(req.getProductId());
        if(prodRes == null || prodRes.getStockQuantity()<req.getQuantity()){
            return false;
        }

        //get user detail
        UserResponse userRes = userServiceClient.getUserDetails(userId);
        if(userRes == null){
            return false;
        }

        CartItem existingCartItem = cartItemRepo.findByUserIdAndProductId(userId,req.getProductId());
        if(existingCartItem!=null){
            //update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity()+req.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepo.save(existingCartItem);
        }else{
            //create a new cartItem
            CartItem item = new CartItem();
            item.setUserId(userId);
            item.setProductId(req.getProductId());
            item.setQuantity(req.getQuantity());
            item.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepo.save(item);
        }
        return true;
    }

    public boolean deleteItemFromCart(String userId,String productId){
        CartItem item = cartItemRepo.findByUserIdAndProductId(userId,productId);
        if(item != null){
            cartItemRepo.delete(item);
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(String userId) {
        return cartItemRepo.findByUserId(userId);
    }

    public void clearCart(String userId) {
        cartItemRepo.deleteByUserId(userId);
    }

    /*
     1.This is a fall back method to show some message to user when service is down
     2.Here in addToCart method we are making service-service calls to get product
        details from cart service and user details from user service.
     3.when any of service down we getting 500 internal server error
     4.But if one service down another working we cannot show 500 instead we have send
        fallback response to client.
     */
    public boolean addToCartFallBack(
            String userId, CartItemRequest request, Exception ex){
        System.out.println("FallBack Called");
        ex.printStackTrace();
        return false;

    }
}
