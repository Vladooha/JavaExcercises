package com.vladooha.service;

import com.vladooha.data.dto.HomeDTO;
import com.vladooha.data.entity.Home;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class HomeServiceTest {
    @Autowired
    private HomeService homeService;

//    @Before
//    public void initTest() {
//        try {
//            Server webServer = Server.createWebServer(
//                    "-web",
//                    "-webAllowOthers",
//                    "-webPort",
//                    "8082");
//            webServer.start();
//        } catch (SQLException e)  {
//            e.printStackTrace();
//        }
//    }

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
        fakeHomeDTO.setAdress("Adress3");
        fakeHomeDTO.setStageCount(2);

        final long INSERT_DENIED_CODE = -1;

        // Act
        long returnCode = homeService.insertDTO(fakeHomeDTO);
        Home homeFromDB = homeService.findById(fakeHomeDTO.getId());

        // Assert
        assertNotEquals(INSERT_DENIED_CODE, returnCode);
        assertNotEquals(null, homeFromDB); // 1 because of inserted home should be unique
        assertEquals(fakeHomeDTO, new HomeDTO(homeFromDB));
    }
}
