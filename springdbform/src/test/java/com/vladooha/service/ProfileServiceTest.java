package com.vladooha.service;

import com.vladooha.data.dto.HomeDTO;
import com.vladooha.data.dto.ProfileDTO;
import com.vladooha.data.entity.Profile;
import org.assertj.core.api.Fail;
import org.h2.tools.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    @Autowired
    private DataSource dataSource;

    @After
    public void tearDown() {
        try {
            clearDatabase();
        } catch (Exception e) {
            Fail.fail(e.getMessage());
        }
    }

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


        assertNotEquals(INSERT_DENIED_CODE, returnCode);
        assertNotEquals(null, profile);
        assertEquals(profileDTO, new ProfileDTO(profile));
    }

    public void clearDatabase() throws SQLException {
        Connection c = dataSource.getConnection();
        Statement s = c.createStatement();

        // Disable FK
        s.execute("SET REFERENTIAL_INTEGRITY FALSE");

        // Find all tables and truncate them
        Set<String> tables = new HashSet<String>();
        ResultSet rs = s.executeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES  where TABLE_SCHEMA='PUBLIC'");
        while (rs.next()) {
            tables.add(rs.getString(1));
        }
        rs.close();
        for (String table : tables) {
            s.executeUpdate("TRUNCATE TABLE " + table);
        }

        // Idem for sequences
        Set<String> sequences = new HashSet<String>();
        rs = s.executeQuery("SELECT SEQUENCE_NAME FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_SCHEMA='PUBLIC'");
        while (rs.next()) {
            sequences.add(rs.getString(1));
        }
        rs.close();
        for (String seq : sequences) {
            s.executeUpdate("ALTER SEQUENCE " + seq + " RESTART WITH 1");
        }

        // Enable FK
        s.execute("SET REFERENTIAL_INTEGRITY TRUE");
        s.close();
        c.close();
    }
}
