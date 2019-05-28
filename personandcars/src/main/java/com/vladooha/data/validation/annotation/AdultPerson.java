package com.vladooha.data.validation.annotation;


import com.vladooha.data.validation.AdultPersonValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AdultPersonValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdultPerson {
    String message() default "Пользователю меньше 18";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
