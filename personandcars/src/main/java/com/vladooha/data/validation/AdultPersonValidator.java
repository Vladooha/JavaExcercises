package com.vladooha.data.validation;

import com.vladooha.data.entity.Person;
import com.vladooha.data.validation.annotation.AdultPerson;
import com.vladooha.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class AdultPersonValidator implements ConstraintValidator<AdultPerson, Long> {
    @Autowired
    private PersonService personService;

    @Override
    public boolean isValid(Long personId, ConstraintValidatorContext context) {
        Person person = personService.findByCustomId(personId);
        Date birthday = person.getBirthday();

        LocalDate now = LocalDate.now();
        LocalDate personBirtday =
                Instant.ofEpochMilli(birthday.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

        long age = ChronoUnit.YEARS.between(now, personBirtday);

        if (Math.abs(age)< 18) {
            return false;
        }

        return true;
    }
}
