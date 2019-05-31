package com.vladooha.data.validator;

import com.vladooha.data.dto.HomeDTO;
import com.vladooha.data.entity.Home;
import com.vladooha.service.HomeService;
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
public class IsHomeValidatorTest {
    @MockBean
    private HomeService homeService;

    private IsHomeValidator isHomeValidator;

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        isHomeValidator = new IsHomeValidator(homeService);
    }

    @Test
    public void validTest_GoodExample() {
        HomeDTO fakeHome = new HomeDTO();
        fakeHome.setId(1L);
        fakeHome.setAdress("Adress");

        Home homeMock = mock(Home.class);
        homeMock.mergeDTOwithId(fakeHome);

        when(homeService.findById(anyLong())).thenReturn(homeMock);

        assertTrue(isHomeValidator.isValid(1L, null));
    }

    @Test
    public void validTest_BadExample() {
        when(homeService.findById(anyLong())).thenReturn(null);

        assertFalse(isHomeValidator.isValid(1L, null));
    }
}
