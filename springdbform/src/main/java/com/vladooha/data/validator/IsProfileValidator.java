package com.vladooha.data.validator;

import com.vladooha.data.validator.annotation.IsProfile;
import com.vladooha.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsProfileValidator implements ConstraintValidator<IsProfile, Long> {
    @Autowired
    private ProfileService profileService;

    @Override
    public boolean isValid(Long profileId, ConstraintValidatorContext context) {
        if (profileId == null || profileService.findById(profileId) == null) {
            return false;
        }

        return true;
    }
}
