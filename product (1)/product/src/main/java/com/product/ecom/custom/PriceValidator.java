package com.product.ecom.custom;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CustomPriceValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PriceValidator {

    String message() default "Invalid Price";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
