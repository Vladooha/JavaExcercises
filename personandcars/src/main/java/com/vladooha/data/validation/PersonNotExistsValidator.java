package com.vladooha.data.validation;

import com.vladooha.data.repo.PersonRepo;
import com.vladooha.data.validation.annotation.PersonNotExists;
import com.vladooha.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class PersonNotExistsValidator implements ConstraintValidator<PersonNotExists, Long> {
    @Autowired
    private PersonService personService;

    @Override
    public boolean isValid(Long personId, ConstraintValidatorContext context) {
        return personService.findByCustomId(personId) == null;
    }
}
