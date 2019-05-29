package com.vladooha.data.validation.annotation;

import com.vladooha.data.validation.HasCarsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HasCarsValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface HasCars {
    String message() default "У пользователя нет машин";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}