package com.vladooha.data.validation.annotation;

import com.vladooha.data.validation.CarNotExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CarNotExistsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CarNotExists {
    String message() default "Машина с таким customId уже существует";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}