package com.product.ecom.service;

import com.product.ecom.dto.ProductRequest;
import com.product.ecom.dto.ProductResponse;
import com.product.ecom.entity.Product;
import com.product.ecom.exception.ProductNotFoundException;
import com.product.ecom.mapper.ProductMapper;
import com.product.ecom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository prodRepo;

    @Autowired
    private ProductMapper prodMapper;

    public ProductResponse createProduct(ProductRequest prodReq){

        Product p = prodMapper.toEntity(prodReq);
        p.setCreatedAt(LocalDateTime.now());
        Product savedAProduct = prodRepo.save(p);
        return prodMapper.toDto(savedAProduct);
    }

    public Optional<ProductResponse>  updateProduct(Long id , ProductRequest prodReq){

        return Optional.of(prodRepo.findById(id)
                .map(p -> {
                    p.setImageUrl(prodReq.getImageUrl());
                    p.setUpdatedAt(LocalDateTime.now());
                    p.setName(prodReq.getName());
                    p.setPrice(prodReq.getPrice());
                    p.setDescription(prodReq.getDescription());
                    p.setStockQuantity(prodReq.getStockQuantity());
                    p.setCategory(prodReq.getCategory());
                    p.setActive(prodReq.getActive());
                    Product savedProduct = prodRepo.save(p);
                    return prodMapper.toDto(savedProduct);
                })
                .orElseThrow(()->new ProductNotFoundException(String.valueOf(id))));
    }

    public Optional<ProductResponse> fetchProductById(Long id){
        return Optional.ofNullable(prodRepo.findById(id)
                .map(prodMapper::toDto)
                .orElseThrow(() -> new ProductNotFoundException(String.valueOf(id))));
    }

    public List<ProductResponse> fetchAllProducts(){
        return prodRepo.findAll()
                .stream().map(prodMapper::toDto)
                .collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id){
        return prodRepo.findById(id)
                .map(p->{
                    p.setActive(false);
                    prodRepo.save(p);
                    return true;
                }).orElse(false);
    }
}
