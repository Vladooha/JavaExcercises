package com.vladooha.data.validator;

import com.vladooha.data.validator.annotation.IsHome;
import com.vladooha.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class IsHomeValidator implements ConstraintValidator<IsHome, Long> {
    private HomeService homeService;

    @Autowired
    public IsHomeValidator(HomeService homeService) {
        this.homeService = homeService;
    }

    @Override
    public boolean isValid(Long homeId, ConstraintValidatorContext context) {
        if (homeId == null || homeService.findById(homeId) == null) {
            return false;
        }

        return true;
    }
}
