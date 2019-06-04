package com.vladooha.service;

import com.vladooha.data.dto.HomeDTO;
import com.vladooha.data.entity.Home;
import org.assertj.core.api.Fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class HomeServiceTest {
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
        HomeDTO fakeHomeDTO = new HomeDTO();
        fakeHomeDTO.setId(1);
        fakeHomeDTO.setAdress("Adress1");
        fakeHomeDTO.setStageCount(2);

        int expectedCount = homeService.findAllDTO().size() + 1; // + 1 because of inserted entity

        final long INSERT_DENIED_CODE = -1;

        // Act
        long returnCode = homeService.insertDTO(fakeHomeDTO);
        List<HomeDTO> allDTOfromDB = homeService.findAllDTO();

        // Assert
        assertNotEquals(INSERT_DENIED_CODE, returnCode);
        assertTrue(allDTOfromDB.contains(fakeHomeDTO));
        assertEquals(expectedCount, allDTOfromDB.size());
    }

    @Test
    public void insertDTOAndFindAll_1DTOinsert_ReturnIDAndOnlyOneEntityAndIncCountBy1() {
        // Arrange
        HomeDTO fakeHomeDTO = new HomeDTO();
        fakeHomeDTO.setId(2);
        fakeHomeDTO.setAdress("Adress2");
        fakeHomeDTO.setStageCount(2);

        int expectedCount = homeService.findAllDTO().size() + 1; // + 1 because of inserted entity

        final long INSERT_DENIED_CODE = -1;

        // Act
        long returnCode = homeService.insertDTO(fakeHomeDTO);
        List<Home> allFromDB = homeService.findAll();
        List<HomeDTO> sameHomes = allFromDB
                .stream()
                .map(h -> new HomeDTO(h))
                .filter(h -> fakeHomeDTO.equals(h))
                .collect(Collectors.toList());

        // Assert
        assertNotEquals(INSERT_DENIED_CODE, returnCode);
        assertEquals(1, sameHomes.size()); // 1 because of inserted home should be unique
        assertEquals(expectedCount, allFromDB.size());
    }

    @Test
    public void insertDTOAndFindById_1DTOinsert_ReturnIDAndReturnedNotNullAndSameEntity() {
        // Arrange
        HomeDTO fakeHomeDTO = new HomeDTO();
        fakeHomeDTO.setId(3);
        fakeHomeDTO.setAdress("Adress2");
        fakeHomeDTO.setStageCount(2);

        final long INSERT_DENIED_CODE = -1;
        List<HomeDTO> profilesBefore = homeService.findAllDTO();

        // Act and Assert
        long returnCode = homeService.insertDTO(fakeHomeDTO);
        List<HomeDTO> profilesAfter = homeService.findAllDTO();
        List<HomeDTO> addedProfile = profilesAfter
                .stream()
                .filter(p -> !profilesBefore.contains(p))
                .collect(Collectors.toList());

        assertEquals(1, addedProfile.size()); // 1 because of one added entity

        final long EXPECTED_ID = addedProfile.get(0).getId();
        Home homeFromDB = homeService.findById(EXPECTED_ID);

        // Assert
        assertNotEquals(INSERT_DENIED_CODE, returnCode);
        assertNotEquals(null, homeFromDB);
        assertEquals(fakeHomeDTO, new HomeDTO(homeFromDB));
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
