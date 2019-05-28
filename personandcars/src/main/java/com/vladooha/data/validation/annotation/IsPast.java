package com.vladooha.data.validation.annotation;

import com.vladooha.data.validation.AdultPersonValidator;
import com.vladooha.data.validation.IsPastValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsPastValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsPast {
    String message() default "Неверная дата";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
