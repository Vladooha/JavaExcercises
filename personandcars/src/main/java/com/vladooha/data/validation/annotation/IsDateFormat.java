package com.vladooha.data.validation.annotation;

import com.vladooha.data.validation.IsDateFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsDateFormatValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsDateFormat {
    String message() default "Неверный формат даты";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}