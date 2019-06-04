package com.vladooha.service;

import com.vladooha.data.dto.HomeDTO;
import com.vladooha.data.dto.ProfileDTO;
import com.vladooha.data.entity.Profile;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class ProfileServiceTest {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private HomeService homeService;

    @Test
    public void insertDTOAndFindAllDTO_1DTOinsert_ReturnIDAndSameEntityAndIncCountBy1() {
        // Arrange
        ProfileDTO fakeProfileDTO = new ProfileDTO();
        fakeProfileDTO.setId(1);
        fakeProfileDTO.setName("1");
        fakeProfileDTO.setSurname("1");

        int expectedCount = profileService.findAllDTO().size() + 1; // + 1 because of inserted entity

        final long INSERT_DENIED_CODE = -1;

        // Act
        long returnCode = profileService.insertDTO(fakeProfileDTO);
        List<ProfileDTO> allDTOfromDB = profileService.findAllDTO();

        // Assert
        assertNotEquals(returnCode, INSERT_DENIED_CODE);
        assertTrue(allDTOfromDB.contains(fakeProfileDTO));
        assertEquals(expectedCount, allDTOfromDB.size());
    }

    @Test
    public void insertDTOAndFindAllSettledDTOAndAddHome_1DTOinsert_ReturnIDsAndContainsOnlyWithHomeAndIncCountBy1() {
        // Arrange
        ProfileDTO fakeProfileDTOWithHome = new ProfileDTO();
        fakeProfileDTOWithHome.setId(2);
        fakeProfileDTOWithHome.setName("2");
        fakeProfileDTOWithHome.setSurname("2");
        HomeDTO fakeHomeDTO = new HomeDTO();
        fakeHomeDTO.setId(1);
        fakeHomeDTO.setAdress("AdressProfile");
        fakeHomeDTO.setStageCount(2);
        fakeProfileDTOWithHome.setHome(fakeHomeDTO);

        ProfileDTO fakeProfileDTOWithouthHome = new ProfileDTO();
        fakeProfileDTOWithouthHome.setId(3);
        fakeProfileDTOWithouthHome.setName("3");
        fakeProfileDTOWithouthHome.setSurname("3");

        int expectedCount = profileService.findAllDTO().size() + 1; // + 1 because of inserted entity

        final long INSERT_DENIED_CODE = -1;

        // Act
        homeService.insertDTO(fakeHomeDTO);
        long returnCodeWithHome = profileService.insertDTO(fakeProfileDTOWithHome);
        long returnCodeWithouthHome = profileService.insertDTO(fakeProfileDTOWithouthHome);
        List<ProfileDTO> allDTOfromDB = profileService.findAllSettledDTO();

        // Assert
        assertNotEquals(returnCodeWithHome, INSERT_DENIED_CODE);
        assertNotEquals(returnCodeWithouthHome, INSERT_DENIED_CODE);
        assertTrue(allDTOfromDB.contains(fakeProfileDTOWithHome));
        assertFalse(allDTOfromDB.contains(fakeProfileDTOWithouthHome));
        assertEquals(expectedCount, allDTOfromDB.size());
    }

    @Test
    public void insertDTOAndFindById_1DTOinsert_ReturnIDAndReturnedNotNullAndSameEntity() {
        // Arrange
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(5666);
        profileDTO.setName("Sas");
        profileDTO.setSurname("Asasasas");

        final long INSERT_DENIED_CODE = -1;
        List<ProfileDTO> profilesBefore = profileService.findAllDTO();

        // Act and Assert
        long returnCode = profileService.insertDTO(profileDTO);
        List<ProfileDTO> profilesAfter = profileService.findAllDTO();
        List<ProfileDTO> addedProfile = profilesAfter
                .stream()
                .filter(p -> !profilesBefore.contains(p))
                .collect(Collectors.toList());

        assertEquals(1, addedProfile.size()); // 1 because of one added entity

        final long EXPECTED_ID = addedProfile.get(0).getId();
        Profile profile = profileService.findById(EXPECTED_ID);

//        try {
//            Thread.sleep(240000);
//        } catch (InterruptedException e) { }

        assertNotEquals(INSERT_DENIED_CODE, returnCode);
        assertNotEquals(null, profile);
        assertEquals(profileDTO, new ProfileDTO(profile));
    }
}
