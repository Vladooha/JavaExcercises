package com.vladooha.controller;

import com.vladooha.data.dto.HomeDTO;
import com.vladooha.data.dto.SettlementDTO;
import com.vladooha.data.dto.ProfileDTO;
import com.vladooha.service.HomeService;
import com.vladooha.service.ProfileService;
import com.vladooha.service.SettlementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SettlementController.class)
public class SettlementControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HomeService homeService;
    @MockBean
    private ProfileService profileService;
    @MockBean
    private SettlementService settlementService;

    @Test
    public void getSettlements_2Settles_2SettlesData() throws Exception {
        // Arrange
        ProfileDTO fakeProfile1 = new ProfileDTO();
        fakeProfile1.setName("Name1");
        fakeProfile1.setSurname("Surname1");

        ProfileDTO fakeProfile2 = new ProfileDTO();
        fakeProfile2.setName("Name2");
        fakeProfile2.setSurname("Surname2");

        HomeDTO fakeHome1 = new HomeDTO();
        fakeHome1.setId(1);
        fakeHome1.setAdress("Adress1");
        fakeHome1.setStageCount(1);

        SettlementDTO fakeProfilesHome1 = new SettlementDTO();
        fakeProfilesHome1.setProfileDTO(fakeProfile1);
        fakeProfilesHome1.setHomeDTO(fakeHome1);

        SettlementDTO fakeProfilesHome2 = new SettlementDTO();
        fakeProfilesHome2.setProfileDTO(fakeProfile2);
        fakeProfilesHome2.setHomeDTO(fakeHome1);

        List<SettlementDTO> fakeSettlements = new ArrayList<>();
        fakeSettlements.add(fakeProfilesHome1);
        fakeSettlements.add(fakeProfilesHome2);

        when(settlementService.findAllDTO())
                .thenReturn(fakeSettlements);

        // Act and Assert
        mockMvc.perform(get("/settlement"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(fakeProfile1.getName())))
                .andExpect(content().string(containsString(fakeProfile1.getSurname())))
                .andExpect(content().string(containsString(fakeProfile2.getName())))
                .andExpect(content().string(containsString(fakeProfile2.getSurname())))
                .andExpect(content().string(containsString(fakeHome1.getAdress())))
                .andExpect(content().string(not(containsString("Random data"))));
    }

    @Test
    public void addSettlement_WrongSettlement_NoAttemptionToAdd() throws Exception {
        // Arrange
        SettlementDTO fakeProfilesHome = new SettlementDTO();
        fakeProfilesHome.setHomeId(-2);
        fakeProfilesHome.setProfileId(9999);

        // Act and Assert
        mockMvc.perform(
                post("/settlement")
                        .flashAttr(SettlementController.NEW_SETTLE, fakeProfilesHome))
                .andDo(print())
                .andExpect(status().isOk());

        verify(profileService, times(0)).insertDTO(any());
    }

    @Test
    public void addSettlement_GoodSettlement_OneAttemptionToAdd() throws Exception {
        // Arrange
        ProfileDTO fakeProfile1 = new ProfileDTO();
        fakeProfile1.setId(2);
        fakeProfile1.setName("Name2");
        fakeProfile1.setSurname("Surname2");

        profileService.insertDTO(fakeProfile1);

        HomeDTO fakeHome1 = new HomeDTO();
        fakeHome1.setId(1);
        fakeHome1.setAdress("Adress1");
        fakeHome1.setStageCount(1);

        homeService.insertDTO(fakeHome1);

        SettlementDTO fakeProfilesHome = new SettlementDTO();
        fakeProfilesHome.setHomeId(1);
        fakeProfilesHome.setProfileId(2);

        // Act and Assert
        mockMvc.perform(
                post("/settlement")
                        .flashAttr(SettlementController.NEW_SETTLE, fakeProfilesHome))
                .andDo(print())
                .andExpect(status().isOk());

        verify(profileService, times(1)).insertDTO(any());
    }
}
