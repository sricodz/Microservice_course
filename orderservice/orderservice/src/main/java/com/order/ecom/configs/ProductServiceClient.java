package com.order.ecom.configs;

import com.order.ecom.dto.ProductResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProductServiceClient {

    @GetExchange("/api/products/id/{id}")
    ProductResponse getProductDetails(@PathVariable String id);
}
