package com.vladooha.data.validator;

import com.vladooha.data.validator.annotation.IsProfile;
import com.vladooha.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class IsProfileValidator implements ConstraintValidator<IsProfile, Long> {
    private ProfileService profileService;

    @Autowired
    public IsProfileValidator(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public boolean isValid(Long profileId, ConstraintValidatorContext context) {
        if (profileId == null || profileService.findById(profileId) == null) {
            return false;
        }

        return true;
    }
}
