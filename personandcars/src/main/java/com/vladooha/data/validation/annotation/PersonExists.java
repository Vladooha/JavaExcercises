package com.vladooha.data.validation.annotation;

import com.vladooha.data.validation.PersonExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PersonExistsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PersonExists {
    String message() default "Нет пользователя с таким customId";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
