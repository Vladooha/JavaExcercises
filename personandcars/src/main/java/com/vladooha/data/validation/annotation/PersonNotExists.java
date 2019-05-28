package com.vladooha.data.validation.annotation;

import com.vladooha.data.validation.PersonNotExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PersonNotExistsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PersonNotExists {
    String message() default "Уже есть пользователь с таким customId";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}