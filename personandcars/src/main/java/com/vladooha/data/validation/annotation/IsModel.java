package com.vladooha.data.validation.annotation;

import com.vladooha.data.validation.IsModelValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsModelValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsModel {
    String message() default "Неверный формат модели машины";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}