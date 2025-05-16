package com.order.ecom.configs;

import com.order.ecom.dto.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserServiceClient {

    @GetExchange("/api/users/v1/{email}")
    UserResponse getUserDetails(@PathVariable String email);
}
