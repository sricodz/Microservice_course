package com.product.ecom.controller;

import com.product.ecom.dto.ProductRequest;
import com.product.ecom.dto.ProductResponse;
import com.product.ecom.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService prodServ;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest prodReq){
        return new ResponseEntity<>(prodServ.createProduct(prodReq), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(){
        return ResponseEntity.ok(prodServ.fetchAllProducts());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductResponse> getProductsById(@PathVariable String id){
        return prodServ.fetchProductById(Long.parseLong(id))
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id, @Valid @RequestBody ProductRequest prodReq){
        return prodServ.updateProduct(id,prodReq)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        boolean deleted = prodServ.deleteProduct(id);
        return deleted?ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
