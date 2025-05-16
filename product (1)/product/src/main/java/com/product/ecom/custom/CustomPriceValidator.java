package com.product.ecom.custom;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class CustomPriceValidator implements ConstraintValidator<PriceValidator, BigDecimal> {
    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {

        if(value==null){
            return false;
        }

        if(value.compareTo(BigDecimal.ZERO)<=0){
            return false;
        }

        return value.scale()<=2;
    }
}
