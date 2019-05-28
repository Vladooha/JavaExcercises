package com.vladooha.data.validator.annotation;

import com.vladooha.data.validator.IsHomeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsHomeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsHome {
    String message() default "Нет дома с таким id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}