package com.product.ecom.entity;

import com.product.ecom.custom.PriceValidator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Product should not be null")
    private String name;

    private String description;

    @NotNull(message = "Price should not be null")
    @PriceValidator(message = "Price Cannot be less than two digits and No negative values allowed!")
    private BigDecimal price;

    @NotNull(message = "Quantity should not be null")
    private Integer stockQuantity;

    @NotBlank(message = "category should not be blank")
    private String category;

    private String imageUrl;

    private Boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
