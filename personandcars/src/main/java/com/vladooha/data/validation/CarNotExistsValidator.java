package com.vladooha.data.validation;

import com.vladooha.data.validation.annotation.CarNotExists;
import com.vladooha.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CarNotExistsValidator implements ConstraintValidator<CarNotExists, Long> {
    @Autowired
    private CarService carService;

    @Override
    public boolean isValid(Long carId, ConstraintValidatorContext context) {
        return carService.findByCustomId(carId) == null;
    }
}
