package com.vladooha.data.validation;

import com.vladooha.data.validation.annotation.IsDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IsDateFormatValidator implements ConstraintValidator<IsDateFormat, String> {
    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        formatter.setLenient(false);
        try {
            Date tempDate = formatter.parse(date);

            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}