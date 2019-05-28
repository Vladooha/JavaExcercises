package com.vladooha.data.validation;

import com.vladooha.data.repo.PersonRepo;
import com.vladooha.data.validation.annotation.AdultPerson;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class AdultPersonValidator implements ConstraintValidator<AdultPerson, Date> {
    @Autowired
    private PersonRepo personRepo;

    @Override
    public boolean isValid(Date birthday, ConstraintValidatorContext context) {
        LocalDate now = LocalDate.now();
        LocalDate personBirtday =
                Instant.ofEpochMilli(birthday.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

        long age = ChronoUnit.YEARS.between(now, personBirtday);

        if (age < 18) {
            return false;
        }

        return true;
    }
}
