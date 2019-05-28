package com.vladooha.data.validation;

import com.vladooha.data.validation.annotation.IsModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsModelValidator implements ConstraintValidator<IsModel, String> {
    @Override
    public boolean isValid(String fullModel, ConstraintValidatorContext context) {
        String[] nameParts = fullModel.split("-");
        if (nameParts.length < 2) {
            return false;
        }

        String vendor = nameParts[0];
        if (vendor == null || vendor.length() < 1) {
            return false;
        }

        StringBuilder modelBuff = new StringBuilder();
        for (int i = 1; i < nameParts.length; ++i) {
            modelBuff.append(nameParts[i]);
        }
        String model = modelBuff.toString();

        if (model == null || model.length() < 1) {
            return false;
        }

        return true;
    }
}