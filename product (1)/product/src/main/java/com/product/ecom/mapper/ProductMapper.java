package com.product.ecom.mapper;

import com.product.ecom.dto.ProductRequest;
import com.product.ecom.dto.ProductResponse;
import com.product.ecom.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest preq){

        Product p = new Product();
        p.setActive(preq.getActive());
        p.setCategory(preq.getCategory());
        p.setName(preq.getName());
        p.setDescription(preq.getDescription());
        p.setPrice(preq.getPrice());
        p.setStockQuantity(preq.getStockQuantity());
        p.setImageUrl(preq.getImageUrl());
        return p;
    }

    public ProductResponse toDto(Product p){

        ProductResponse pr = new ProductResponse();
        pr.setActive(p.getActive());
        pr.setCategory(p.getCategory());
        pr.setDescription(p.getDescription());
        pr.setName(p.getName());
        pr.setPrice(p.getPrice());
        pr.setImageUrl(p.getImageUrl());
        pr.setStockQuantity(p.getStockQuantity());
        pr.setId(p.getId());
        return pr;
    }
}
