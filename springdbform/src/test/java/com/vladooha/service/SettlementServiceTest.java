package com.vladooha.service;

import com.vladooha.data.dto.HomeDTO;
import com.vladooha.data.dto.SettlementDTO;
import com.vladooha.data.dto.ProfileDTO;
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
