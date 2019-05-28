package com.vladooha.data.validator.annotation;

import com.vladooha.data.validator.IsProfileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsProfileValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsProfile {
    String message() default "Нет профиля с таким id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}