package com.vladooha.data.validation;

import com.vladooha.data.entity.Car;
import com.vladooha.data.entity.Person;
import com.vladooha.data.validation.annotation.HasCars;
import com.vladooha.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

public class HasCarsValidator implements ConstraintValidator<HasCars, Long> {
    @Autowired
    private PersonService personService;

    @Override
    public boolean isValid(Long personId, ConstraintValidatorContext context) {
        Person person = personService.findByCustomId(personId);
        Set<Car> personCars = person.getCars();

        if (personCars == null || personCars.size() < 1) {
            return false;
        }

        return true;
    }
}