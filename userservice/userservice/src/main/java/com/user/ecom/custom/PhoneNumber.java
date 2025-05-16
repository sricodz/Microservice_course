package com.user.ecom.custom;


/*
    In this Package we are creating the custom annotation for phoneNumber validation

* **/

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {

    String message() default "Invalid phone Number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
