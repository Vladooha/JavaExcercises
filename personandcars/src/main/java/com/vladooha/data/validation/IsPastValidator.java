package com.vladooha.data.validation;

import com.vladooha.data.validation.annotation.IsDateFormat;
import com.vladooha.data.validation.annotation.IsPast;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IsPastValidator implements ConstraintValidator<IsPast, Date> {
    @Override
    public boolean isValid(Date date, ConstraintValidatorContext context) {
        Date now = new Date();
        if (now.before(date)) {
            return false;
        }

        return true;
    }
}
