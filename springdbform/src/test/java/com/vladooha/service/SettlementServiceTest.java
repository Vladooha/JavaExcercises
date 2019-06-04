package com.vladooha.service;

import com.vladooha.data.dto.HomeDTO;
import com.vladooha.data.dto.SettlementDTO;
import com.vladooha.data.dto.ProfileDTO;
import org.h2.tools.Server;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class SettlementServiceTest {
    @Autowired
    private SettlementService settlementService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private HomeService homeService;

    @Test
    public void settleAndFindAllDTO_settleOnePair_settlePairFounded() {
        // Arrange
        HomeDTO fakeHomeDTO = new HomeDTO();
        fakeHomeDTO.setId(311);
        fakeHomeDTO.setAdress("AdressSettleTest");
        fakeHomeDTO.setStageCount(2);
        homeService.insertDTO(fakeHomeDTO);
        fakeHomeDTO = homeService.findAllDTO().get(0);

        ProfileDTO fakeProfileDTO = new ProfileDTO();
        fakeProfileDTO.setId(312);
        fakeProfileDTO.setName("NameSettleTest");
        fakeProfileDTO.setSurname("SurnameSettleTest");
        profileService.insertDTO(fakeProfileDTO);
        fakeProfileDTO = profileService.findAllDTO().get(0);

        SettlementDTO fakeSettlementDTO = new SettlementDTO();
        fakeSettlementDTO.setHomeId(fakeHomeDTO.getId());
        fakeSettlementDTO.setHomeDTO(fakeHomeDTO);
        fakeSettlementDTO.setProfileId(fakeProfileDTO.getId());
        fakeSettlementDTO.setProfileDTO(fakeProfileDTO);

        // Act
        settlementService.settle(fakeSettlementDTO);
        List<SettlementDTO> settleFromDb = settlementService.findAllDTO();

        assertTrue(settleFromDb.contains(fakeSettlementDTO));
    }
}
