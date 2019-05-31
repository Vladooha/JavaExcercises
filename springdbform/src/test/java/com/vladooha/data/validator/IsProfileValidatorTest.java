package com.vladooha.data.validator;

import com.vladooha.data.dto.ProfileDTO;
import com.vladooha.data.entity.Profile;
import com.vladooha.service.ProfileService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class IsProfileValidatorTest {
    @MockBean
    private ProfileService profileService;

    private IsProfileValidator isProfileValidator;

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        isProfileValidator = new IsProfileValidator(profileService);
    }

    @Test
    public void validTest_GoodExample() {
        ProfileDTO fakeProfile = new ProfileDTO();
        fakeProfile.setId(1L);
        fakeProfile.setName("Name");
        fakeProfile.setSurname("Surname");

        Profile profileMock = mock(Profile.class);
        profileMock.mergeDTOwithId(fakeProfile);

        when(profileService.findById(anyLong())).thenReturn(profileMock);

        assertTrue(isProfileValidator.isValid(1L, null));
    }

    @Test
    public void validTest_BadExample() {
        when(profileService.findById(anyLong())).thenReturn(null);

        assertFalse(isProfileValidator.isValid(1L, null));
    }
}
