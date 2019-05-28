package com.vladooha.data.validation;

import com.vladooha.data.validation.annotation.PersonExists;
import com.vladooha.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PersonExistsValidator implements ConstraintValidator<PersonExists, Long> {
    @Autowired
    private PersonService personService;

    @Override
    public boolean isValid(Long personId, ConstraintValidatorContext context) {
        return personService.findByCustomId(personId) != null;
    }
}
