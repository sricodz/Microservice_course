package com.order.ecom.service;

import com.order.ecom.dto.OrderItemDto;
import com.order.ecom.dto.OrderResponse;
import com.order.ecom.entity.CartItem;
import com.order.ecom.entity.Order;
import com.order.ecom.entity.OrderItem;
import com.order.ecom.entity.OrderStatus;
import com.order.ecom.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private CartService cartService;
    private OrderRepository orderRepo;

    @Transactional
    public Optional<OrderResponse> createOrder(String userId){
        //validate for cartItems
        List<CartItem> cartItems = cartService.getCart(userId);
        if(cartItems.isEmpty()){
            return Optional.empty();
        }

        //calculate totatl price
        BigDecimal totalPrice = cartItems
                                    .stream()
                                    .map(CartItem::getPrice)
                                    .reduce(BigDecimal.ZERO,BigDecimal::add);
        //create an order
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItems.stream()
                .map(item->new OrderItem(
                        null,
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                        ))
                .toList();
        order.setItem(orderItems);
        Order savedOrder = orderRepo.save(order);

        //clear the cart
        cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getItem().stream()
                        .map(orderItem -> new OrderItemDto(
                                orderItem.getId(),
                                orderItem.getProductId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice(),
                                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
                        ))
                        .toList(),
                order.getCreateAt()
        );
    }
}
